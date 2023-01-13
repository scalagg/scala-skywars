package gg.scala.skywars.plugin.lobby.ranking

import gg.scala.cgs.common.player.CgsGamePlayer
import gg.scala.cgs.lobby.leaderboard.CgsLobbyRankingEntry
import gg.scala.skywars.plugin.lobby.SurvivalGamesLobbyEngine

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object TopKillsRankingEntry : CgsLobbyRankingEntry
{
    override fun getDisplay() = "Kills"
    override fun getId() = "kills"

    override fun getValue(cgsGamePlayer: CgsGamePlayer): Int
    {
        return SurvivalGamesLobbyEngine
            .getStatistics(cgsGamePlayer)
            .kills.value
    }
}
