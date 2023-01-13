package gg.scala.skywars.plugin.game.service

import gg.scala.flavor.service.Service
import gg.scala.skywars.plugin.shared.SkyWarsPlayerModel
import gg.scala.store.controller.DataStoreObjectControllerCache
import org.bukkit.entity.Player
import java.util.UUID

/**
 * @author GrowlyX
 * @since 1/12/2023
 */
@Service
object PlayerServices
{
    fun provide(player: Player) = DataStoreObjectControllerCache
        .findNotNull<SkyWarsPlayerModel>()
        .localCache()[player.uniqueId]

    fun provide(uniqueId: UUID) = DataStoreObjectControllerCache
        .findNotNull<SkyWarsPlayerModel>()
        .localCache()[uniqueId]
}
