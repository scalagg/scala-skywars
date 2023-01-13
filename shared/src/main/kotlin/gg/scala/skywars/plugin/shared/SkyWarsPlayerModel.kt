package gg.scala.skywars.plugin.shared

import gg.scala.cgs.common.player.statistic.GameSpecificStatistics
import gg.scala.store.controller.DataStoreObjectControllerCache
import gg.scala.store.storage.type.DataStoreStorageType
import java.util.UUID

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
class SkyWarsPlayerModel(
    uniqueId: UUID
) : GameSpecificStatistics(uniqueId)
{
    init
    {
        this.gameKills.reset()
    }

    override fun getAbstractType() =
        SkyWarsPlayerModel::class.java

    override fun save() = DataStoreObjectControllerCache
        .findNotNull<SkyWarsPlayerModel>()
        .save(this, DataStoreStorageType.MONGO)
}
