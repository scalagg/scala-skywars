package gg.scala.skywars.plugin.shared

import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.skywars.plugin.shared.gamemode.SkyWarsSoloOpGamemode

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
object SkyWarsGameInfo : CgsGameGeneralInfo(
    "SkyWars", 0.01F, 2,
    10, SkyWarsGameRewards, false, true,
    true, true, true,
    true, listOf(SkyWarsSoloOpGamemode)
)
