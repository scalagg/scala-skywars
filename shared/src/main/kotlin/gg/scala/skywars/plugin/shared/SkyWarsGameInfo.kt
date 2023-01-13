package gg.scala.skywars.plugin.shared

import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.skywars.plugin.shared.gamemode.SkyWarsSoloGamemode

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object SkyWarsGameInfo : CgsGameGeneralInfo(
    "Survival Games", 0.01F, 2,
    10, SkyWarsGameRewards, true, true,
    true, false, true,
    true, listOf(SkyWarsSoloGamemode)
)
