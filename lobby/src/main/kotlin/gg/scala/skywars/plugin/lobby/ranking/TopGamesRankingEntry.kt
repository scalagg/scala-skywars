package gg.scala.skywars.plugin.lobby.ranking

import gg.scala.cgs.common.player.CgsGamePlayer
import gg.scala.cgs.lobby.leaderboard.CgsLobbyRankingEntry
import gg.scala.skywars.plugin.lobby.SkyWarsLobbyEngine

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object TopGamesRankingEntry : CgsLobbyRankingEntry
{
    override fun getDisplay() = "Played"
    override fun getId() = "played"

    override fun getValue(cgsGamePlayer: CgsGamePlayer): Int
    {
        return SkyWarsLobbyEngine
            .getStatistics(cgsGamePlayer)
            .played.value
    }
}
