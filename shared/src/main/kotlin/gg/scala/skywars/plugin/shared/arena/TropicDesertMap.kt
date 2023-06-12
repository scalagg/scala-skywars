package gg.scala.skywars.plugin.shared.arena

import net.evilblock.cubed.util.bukkit.cuboid.Cuboid
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object TropicDesertMap : SkyWarsMap
{
    override fun getId() = "desert"
    override fun getName() = "Desert"

    override fun getMaterial() = Pair(Material.SAND, 0)
    override fun getDescription() = "Desert"
    override fun getBukkitWorldName() = "desert"

    override fun getDirectory() = null

    override val cuboid = Cuboid(
        Location(
            Bukkit.getWorlds().first(),
            98.221, 0.0, 106.7
        ),
        Location(
            Bukkit.getWorlds().first(),
            -247.0, 0.0, -329.0
        )
    )

    override fun getPreLobbyLocation() = Location(
        Bukkit.getWorlds().first(),
        -15.5, 79.0, -33.5
    )

    override fun getSpectatorLocation() = Location(
        Bukkit.getWorlds().first(),
        -15.5, 79.0, -33.5
    )
}
