package gg.scala.skywars.plugin.game.service

import gg.scala.cgs.common.player.handler.CgsPlayerHandler
import gg.scala.flavor.service.Service
import gg.scala.skywars.plugin.shared.SkyWarsPlayerModel
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 1/12/2023
 */
@Service
object PlayerServices
{
    fun provide(player: Player) =
        CgsPlayerHandler.statistics[player.uniqueId] as SkyWarsPlayerModel?
}
