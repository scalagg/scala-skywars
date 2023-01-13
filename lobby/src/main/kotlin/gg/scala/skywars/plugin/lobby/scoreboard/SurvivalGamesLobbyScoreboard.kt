package gg.scala.skywars.plugin.lobby.scoreboard

import gg.scala.cgs.common.player.handler.CgsPlayerHandler
import gg.scala.cgs.lobby.updater.CgsGameInfoUpdater
import gg.scala.lemon.LemonConstants
import gg.scala.skywars.plugin.lobby.SurvivalGamesLobbyEngine
import net.evilblock.cubed.scoreboard.ScoreboardAdapter
import net.evilblock.cubed.util.CC
import org.bukkit.entity.Player
import java.util.*

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object SurvivalGamesLobbyScoreboard : ScoreboardAdapter()
{
    override fun getLines(
        board: LinkedList<String>,
        player: Player
    )
    {
        board.add("${CC.GRAY}${CC.STRIKE_THROUGH}-----------------")
        board.add("In Game: ${CC.PRI}${CgsGameInfoUpdater.playingTotalCount}")
        board.add("In Lobby: ${CC.PRI}${CgsGameInfoUpdater.lobbyTotalCount}")

        board.add("")

        val cgsPlayer = CgsPlayerHandler.find(player)

        if (cgsPlayer != null)
        {
            val statistics = SurvivalGamesLobbyEngine
                .getStatistics(cgsPlayer)

            board.add("${CC.D_AQUA}Statistics:")
            board.add(" Wins: ${CC.PRI}${statistics.wins.value}")
            board.add(" Losses: ${CC.PRI}${statistics.losses.value}")
            board.add("")
        }

        board.add("${CC.D_AQUA}Global:")
        board.add(" Your coins: ${CC.GOLD}0")
        board.add("")

        board.add("${CC.PRI}play.${LemonConstants.WEB_LINK}")
        board.add("${CC.GRAY}${CC.STRIKE_THROUGH}-----------------")
    }

    override fun getTitle(player: Player) =
        CC.B_PRI + "Survival Games"
}
