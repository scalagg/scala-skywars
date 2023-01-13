package gg.scala.skywars.plugin.lobby.ranking

import gg.scala.cgs.common.player.CgsGamePlayer
import gg.scala.cgs.lobby.leaderboard.CgsLobbyRankingEntry
import gg.scala.skywars.plugin.lobby.SkyWarsLobbyEngine

/**
 * @author GrowlyX
 * @since 2/22/2022
 */
object TopWinsRankingEntry : CgsLobbyRankingEntry
{
    override fun getDisplay() = "Wins"
    override fun getId() = "wins"

    override fun getValue(cgsGamePlayer: CgsGamePlayer): Int
    {
        return SkyWarsLobbyEngine
            .getStatistics(cgsGamePlayer)
            .wins.value
    }
}
