package gg.scala.skywars.plugin.game.tasks

import gg.scala.skywars.plugin.game.SkyWarsGamePlugin.Companion.plugin
import gg.scala.skywars.plugin.game.chest.ChestRefillService.refillAllChests
import gg.scala.skywars.plugin.game.utilities.GameUtilities.formatSeconds
import net.evilblock.cubed.util.CC
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

/**
 * @author puugz
 * @since 19/06/2021 01:08
 */
class ChestRefillTask(private val again: Boolean) : BukkitRunnable()
{
    init
    {
        runTaskTimer(
            plugin,
            0L, 20L
        )
    }

    override fun run()
    {
        val game = plugin
        val chestRefillTime = game.chestRefillTime
        if (mutableListOf(180, 120, 60, 30, 15, 10, 5, 4, 3, 2, 1).contains(chestRefillTime))
        {
            Bukkit.broadcastMessage(
                CC.SEC + "The chests refill" + (if (again) " again" else "") + " in " + CC.PRI + formatSeconds(
                    chestRefillTime
                ) + CC.SEC + "."
            )
        }
        if (chestRefillTime == 0)
        {
            Bukkit.broadcastMessage(CC.GREEN + "The chests have refilled" + (if (again) " again" else "") + "!")
            refillAllChests()
            Bukkit.getOnlinePlayers()
                .forEach { player: Player? -> player!!.playSound(player.location, Sound.CHEST_OPEN, 5f, 1f) }
            if (!again)
            {
                game.chestRefillTime = 180
                ChestRefillTask(true)
            }
            cancel()
            return
        }
        game.chestRefillTime = chestRefillTime - 1
    }
}
