package gg.scala.skywars.plugin.game

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.cgs.common.snapshot.CgsGameSnapshot
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.lemon.util.CubedCacheUtil
import gg.scala.skywars.plugin.game.frontend.SkyWarsNametagAdapter
import gg.scala.skywars.plugin.game.frontend.SkyWarsScoreboardAdapter
import gg.scala.skywars.plugin.game.frontend.SkyWarsVisibilityAdapter
import gg.scala.skywars.plugin.game.listener.SkyWarsListener
import gg.scala.skywars.plugin.shared.SkyWarsPlayerModel
import net.evilblock.cubed.util.CC
import kotlin.properties.Delegates

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
class SkyWarsEngine(
    plugin: ExtendedScalaPlugin,
    gameInfo: CgsGameGeneralInfo,
    gameMode: CgsGameMode
) : CgsGameEngine<SkyWarsPlayerModel>(
    plugin, gameInfo, gameMode,
    SkyWarsPlayerModel::class
)
{
    companion object
    {
        @JvmStatic
        var INSTANCE by Delegates.notNull<SkyWarsEngine>()
    }

    override fun getScoreboardRenderer() = SkyWarsScoreboardAdapter
    override fun getVisibilityAdapter() = SkyWarsVisibilityAdapter
    override fun getNametagAdapter() = SkyWarsNametagAdapter

    override fun getGameSnapshot(): CgsGameSnapshot
    {
        return object : CgsGameSnapshot
        {
            override fun getExtraInformation(): List<String>
            {
                val topKills = mutableListOf<String>()

                val sortedKills = SkyWarsListener.killsTracker
                    .entries.sortedByDescending { it.value }

                for (i in 0 until 3.coerceAtMost(sortedKills.size))
                {
                    val (key, value) = sortedKills[i]
                    topKills.add("  ${CC.YELLOW}#${i + 1} ${CC.GRAY}- ${CC.WHITE}${CubedCacheUtil.fetchName(key)}${CC.GRAY} - ${CC.PRI}$value")
                }

                return topKills
            }
        }
    }
}
