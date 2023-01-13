package gg.scala.skywars.plugin.shared.gamemode

import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.skywars.plugin.shared.arena.AlaskanVillage
import net.evilblock.cubed.util.CC
import org.bukkit.Material

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object SkyWarsSoloGamemode : CgsGameMode
{
    override fun getId() = "solo"
    override fun getName() = "Solo"

    override fun getMaterial() = Pair(Material.EYE_OF_ENDER, 0)

    override fun getDescription() = "${CC.GRAY}A solo game of Skywars!"

    override fun getArenas() = listOf(
        AlaskanVillage
    )

    override fun getTeamSize() = 1 // team size
    override fun getMaxTeams() = 12 // max teams
}
