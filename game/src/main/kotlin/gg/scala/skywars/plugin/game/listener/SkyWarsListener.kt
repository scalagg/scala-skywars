package gg.scala.skywars.plugin.game.listener

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.flavor.inject.Inject
import gg.scala.skywars.plugin.game.SkyWarsGamePlugin
import gg.scala.skywars.plugin.game.cage.CageService
import gg.scala.skywars.plugin.game.kit.KitService
import gg.scala.skywars.plugin.game.kit.selection.KitSelectionMenu
import gg.scala.skywars.plugin.game.sit
import gg.scala.skywars.plugin.game.tasks.ChestRefillTask
import me.lucko.helper.utils.Players
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.Tasks
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*
import java.util.function.Consumer

/**
 * @author GrowlyX, puugz
 * @since 12/17/2021
 */
object SkyWarsListener : Listener
{
    @Inject
    lateinit var plugin: SkyWarsGamePlugin

    val killsTracker = mutableMapOf<UUID, Int>()

    private val compassTracker: BiMap<UUID, UUID> = HashBiMap.create()
    private val currentlyTracking = hashSetOf<UUID>()
    private val audiences by lazy { BukkitAudiences.create(plugin) }

    val spawnPoints = mutableListOf<Location>()
    val deathMatchSpawnPoints = mutableListOf<Location>()

    lateinit var centerLocation: Location

    @EventHandler
    fun onItemDrop(event: PlayerDropItemEvent)
    {
        if (event.itemDrop.itemStack.type == Material.COMPASS)
        {
            event.isCancelled = true
            event.player.sendMessage("${CC.RED}You cannot drop your tracker!")
        }
    }

    @EventHandler
    fun onInventory(event: InventoryClickEvent)
    {
        if (event.currentItem.type == Material.COMPASS && compassTracker.containsKey(event.whoClicked.uniqueId))
        {
            event.isCancelled = true
            event.whoClicked.sendMessage("${CC.RED}You cannot gather or remove your tracker!")
        }
    }

    @EventHandler
    fun onItemTrackerHeld(event: PlayerItemHeldEvent)
    {
        val player = event.player
        val newItem = event.player.inventory.getItem(event.newSlot)

        if (newItem != null && newItem.type == Material.COMPASS && compassTracker.containsKey(event.player.uniqueId))
        {
            currentlyTracking.add(player.uniqueId)
        } else
        {
            // They're no longer using the compass, remove their action bar
            if (currentlyTracking.remove(player.uniqueId))
            {

                audiences.player(player)
                    .sendActionBar(
                        LegacyComponentSerializer.legacySection()
                            .deserialize("")
                    )
            }
        }
    }

    @EventHandler
    fun onGameStarting(
        event: CgsGameEngine.CgsGamePreStartEvent
    )
    {
        Players.all()
            .forEachIndexed { index, player ->
                player.teleport(spawnPoints[index])
                player.sit(true)
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
        event.drops.removeIf { stack -> stack.type == Material.COMPASS }

        killsTracker[event.entity.killer.uniqueId] =
            (killsTracker[event.entity.killer.uniqueId] ?: 0) + 1

        val tracker = Bukkit.getPlayer(compassTracker.inverse()[event.entity.uniqueId])

        if (tracker != null)
        {
            compassTracker.remove(tracker.uniqueId)
            currentlyTracking.remove(tracker.uniqueId)
            tracker.inventory.remove(Material.COMPASS)
            tracker.sendMessage("${CC.GREEN}The player you were tracking died, so your tracker has been destroyed!")
        }
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent)
    {
        // After coming down and taking fall damage after being seated, it causes an issue with fall damage.
        if (event.entity.hasMetadata("seated") && event.cause == EntityDamageEvent.DamageCause.FALL)
        {
            event.isCancelled = true
        }
    }

    private val effect = PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 2)

    @EventHandler
    fun onGameStart(
        event: CgsGameEngine.CgsGameStartEvent
    )
    {
        Bukkit.getOnlinePlayers()
            .forEach { player ->
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

    @EventHandler
    fun onInteract(event: PlayerInteractEvent)
    {
        if (
            CgsGameEngine.INSTANCE.gameState != CgsGameState.WAITING ||
            CgsGameEngine.INSTANCE.gameState != CgsGameState.STARTING
            )
        {
            return
        }

        if (event.clickedBlock != null)
        {
            return
        }

        if (event.hasItem() && event.item.type == Material.BOOK)
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
            event.participant.inventory.setItem(
                0,
                ItemBuilder.of(Material.BOOK)
                    .name("${CC.GOLD}Select a Kit ${CC.GRAY}(Right Click)")
                    .build()
            )
        }

        when (CgsGameEngine.INSTANCE.gameState)
        {
            CgsGameState.WAITING -> {
                Tasks.delayed(10L, lambda)
            }
            CgsGameState.STARTING -> lambda()
            else -> {}
        }
    }
}
