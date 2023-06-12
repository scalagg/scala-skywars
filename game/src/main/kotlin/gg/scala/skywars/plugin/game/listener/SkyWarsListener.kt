package gg.scala.skywars.plugin.game.listener

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.skywars.plugin.game.SkyWarsGamePlugin
import gg.scala.skywars.plugin.game.cage.CageService
import gg.scala.skywars.plugin.game.kit.KitService
import gg.scala.skywars.plugin.game.kit.selection.KitSelectionMenu
import gg.scala.skywars.plugin.game.tasks.ChestRefillTask
import gg.scala.skywars.plugin.game.utilities.GameUtilities
import me.lucko.helper.utils.Players
import net.evilblock.cubed.entity.EntityHandler
import net.evilblock.cubed.entity.hologram.updating.UpdatingHologramEntity
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.Tasks
import net.minecraft.server.v1_8_R3.BlockPosition
import net.minecraft.server.v1_8_R3.Blocks
import net.minecraft.server.v1_8_R3.PacketPlayOutBlockAction
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.metadata.FixedMetadataValue
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*


/**
 * @author GrowlyX, puugz
 * @since 12/17/2021
 */
@Service
object SkyWarsListener : Listener
{
    @Inject
    lateinit var plugin: SkyWarsGamePlugin

    class RefillHologram(
        location: Location
    ) : UpdatingHologramEntity(
        "", location
    )
    {
        fun destroy()
        {
            closeForAll(location)

            location.block
                .removeMetadata(
                    "hologram", plugin
                )

            destroyForCurrentWatchers()
            EntityHandler.forgetEntity(this)
        }

        fun create()
        {
            openForAll(location)

            location.block
                .setMetadata(
                    "hologram",
                    FixedMetadataValue(plugin, this)
                )

            initializeData()
            EntityHandler.trackEntity(this)
        }

        override fun getNewLines() = listOf(
            "${CC.GREEN}${
                GameUtilities.secondsToTime(SkyWarsGamePlugin.plugin.chestRefillTime)
            }"
        )

        override fun getTickInterval() = 500L
    }

    val killsTracker = mutableMapOf<UUID, Int>()
    val holograms = mutableListOf<RefillHologram>()

    @Configure
    fun configure()
    {
        plugin.server.pluginManager.registerEvents(this, plugin)
    }

    @EventHandler
    fun BlockBreakEvent.on()
    {
        if (block.type != Material.CHEST)
        {
            return
        }

        if (block.hasMetadata("hologram"))
        {
            with(
                block.getMetadata("hologram")
                    .first().value() as RefillHologram
            )
            {
                destroy()
                holograms -= this
            }
        }
    }

    @EventHandler
    fun PlayerInteractEvent.on()
    {
        if (
            action != Action.RIGHT_CLICK_BLOCK ||
            clickedBlock.type != Material.CHEST
        )
        {
            return
        }

        if (clickedBlock.hasMetadata("hologram"))
        {
            return
        }

        with(
            RefillHologram(
                clickedBlock.location
                    .add(0.500, 0.0, 0.500)
            )
        ) {
            create()
            holograms += this
        }
    }

    fun closeForAll(chestLocation: Location) =
        sendChestAction(chestLocation, 0)

    fun openForAll(chestLocation: Location) =
        sendChestAction(chestLocation, 1)

    private fun sendChestAction(chestLocation: Location, id: Int)
    {
        val pos = BlockPosition(chestLocation.blockX, chestLocation.blockY, chestLocation.blockZ)
        val packet = PacketPlayOutBlockAction(pos, Blocks.CHEST, 1, id)

        Players.all()
            .forEach {
                (it as CraftPlayer).handle
                    .playerConnection
                    .sendPacket(packet)
            }
    }

    @EventHandler
    fun onDeath(
        event: PlayerDeathEvent
    )
    {
        event.entity.world
            .strikeLightningEffect(
                event.entity.location
            )

        event.entity.killer ?: return

        killsTracker[event.entity.killer.uniqueId] =
            (killsTracker[event.entity.killer.uniqueId] ?: 0) + 1
    }

    private val effect = PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 2)

    @EventHandler
    fun onGameStart(
        event: CgsGameEngine.CgsGameStartEvent
    )
    {
        Bukkit.getOnlinePlayers()
            .forEach { player ->
                player.closeInventory()

                player.addPotionEffect(effect)
                KitService.applyKit(player)

                killsTracker[player.uniqueId] = 0
            }

        CageService.openCages()
        ChestRefillTask(false)
    }

    @EventHandler
    fun onLeave(event: PlayerQuitEvent)
    {
        CageService.usedLocations.remove(event.player.uniqueId)
    }

    private val thing = ItemBuilder.of(Material.BOOK)
        .name("${CC.GOLD}Select a Kit ${CC.GRAY}(Right Click)")
        .build()

    @EventHandler
    fun onInteract(event: PlayerInteractEvent)
    {
        if (
            event.hasItem() &&
            event.action.name.contains("RIGHT") &&
            event.clickedBlock == null &&
            event.item.isSimilar(thing)
        )
        {
            KitSelectionMenu().openMenu(event.player)
        }
    }

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    fun onParticipantConnect(
        event: CgsGameEngine.CgsGameParticipantConnectEvent
    )
    {
        val lambda = ctx@{
            if (!CageService.teleportToCage(event.participant))
            {
                event.participant.kickPlayer("Couldn't find a space for you to play!")
                return@ctx
            }

            event.participant.inventory.clear()
            event.participant.inventory.setItem(0, thing)
            event.participant.updateInventory()
        }

        when (CgsGameEngine.INSTANCE.gameState)
        {
            CgsGameState.WAITING ->
            {
                Tasks.delayed(10L, lambda)
            }

            CgsGameState.STARTING -> lambda()
            else ->
            {
            }
        }
    }
}
