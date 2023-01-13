package gg.scala.skywars.plugin.shared.arena

import gg.scala.cgs.common.information.arena.CgsGameArena
import net.evilblock.cubed.util.bukkit.cuboid.Cuboid

/**
 * @author GrowlyX
 * @since 1/12/2023
 */
interface SkyWarsMap : CgsGameArena
{
    val cuboid: Cuboid
}
