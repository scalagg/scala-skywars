package gg.scala.skywars.plugin.game.frontend

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.player.scoreboard.CgsGameScoreboardRenderer
import gg.scala.cgs.common.runnable.state.StartingStateRunnable
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.lemon.LemonConstants
import gg.scala.skywars.plugin.game.SkyWarsGamePlugin
import gg.scala.skywars.plugin.game.listener.SkyWarsListener
import gg.scala.skywars.plugin.game.utilities.GameUtilities
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Tasks
import net.evilblock.cubed.util.time.TimeUtil
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.*

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object SkyWarsScoreboardAdapter : CgsGameScoreboardRenderer
{
    private var remaining = 0

    init
    {
        Tasks.asyncTimer({
            remaining = Bukkit.getOnlinePlayers()
                .filter { !it.hasMetadata("spectator") }
                .size
        }, 0L, 10L)
    }

    override fun getTitle() = "${CC.B_PRI}SkyWars ${CC.GRAY}(Beta)"

    override fun render(lines: LinkedList<String>, player: Player, state: CgsGameState)
    {
        lines.add("${CC.GRAY}${CC.STRIKE_THROUGH}-----------------")

        if (state == CgsGameState.WAITING || state == CgsGameState.STARTING)
        {
            if (state == CgsGameState.WAITING)
            {
                lines.add("Waiting for players...")
            } else
            {
                lines.add("Starting in ${CC.PRI}${TimeUtil.formatIntoAbbreviatedString(StartingStateRunnable.PRE_START_TIME)}")
            }

            lines.add("")
            lines.add("Players: ${CC.PRI}${
                Bukkit.getOnlinePlayers().size
            }/${
                Bukkit.getMaxPlayers()
            }")
        } else if (state.isAfter(CgsGameState.STARTED))
        {
            lines.add("Next Event:")
            if (state != CgsGameState.ENDED)
            {
                lines.add(CC.GREEN + "Refill " + GameUtilities.secondsToTime(SkyWarsGamePlugin.plugin.chestRefillTime))
            } else
            {
                lines += "${CC.RED}Game ended!"
            }
            lines.add("")
            lines.add("Players left: " + CC.PRI + remaining + "/" + CgsGameEngine.INSTANCE.originalRemaining.size)
            lines.add("Kills: " + CC.PRI + SkyWarsListener.killsTracker[player.uniqueId])
        }

        lines.add("")
        lines.add("Mode: ${CC.PRI}${
            CgsGameEngine.INSTANCE.gameMode.getName()
        }")
        lines.add("Version: ${CC.PRI}${
            CgsGameEngine.INSTANCE.gameInfo.gameVersion
        }")
        lines.add("")
        lines.add("${CC.PRI}${LemonConstants.WEB_LINK}")
        lines.add("${CC.GRAY}${CC.STRIKE_THROUGH}-----------------")
    }
}
