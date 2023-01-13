package gg.scala.skywars.plugin.game.listener

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.flavor.inject.Inject
import gg.scala.skywars.plugin.game.SkyWarsGamePlugin
import gg.scala.skywars.plugin.game.cage.CageService
import gg.scala.skywars.plugin.game.kit.KitService
import gg.scala.skywars.plugin.game.kit.selection.KitSelectionMenu
import gg.scala.skywars.plugin.game.tasks.ChestRefillTask
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.Tasks
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

/**
 * @author GrowlyX, puugz
 * @since 12/17/2021
 */
object SkyWarsListener : Listener
{
    @Inject
    lateinit var plugin: SkyWarsGamePlugin

    val killsTracker = mutableMapOf<UUID, Int>()

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
