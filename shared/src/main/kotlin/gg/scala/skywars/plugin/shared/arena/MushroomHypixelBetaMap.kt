package gg.scala.skywars.plugin.shared.arena

import net.evilblock.cubed.util.bukkit.cuboid.Cuboid
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object MushroomHypixelBetaMap : SkyWarsMap
{
    override fun getId() = "mushroom"
    override fun getName() = "Mushroom"

    override fun getMaterial() = Pair(Material.MUSHROOM_SOUP, 0)
    override fun getDescription() = "Mushroom 4life."
    override fun getBukkitWorldName() = "mushroom"

    override fun getDirectory() = null

    override val cuboid = Cuboid(
        Location(
            Bukkit.getWorlds().first(),
            10844.500, 53.6, 19937.3
        ),
        Location(
            Bukkit.getWorlds().first(),
            10744.5, 154.5, 20061.5
        )
    )

    override fun getPreLobbyLocation() = Location(
        Bukkit.getWorlds().first(),
        10814.500, 128.00, 20003.500
    )

    override fun getSpectatorLocation() = Location(
        Bukkit.getWorlds().first(),
        10814.500, 121.00, 20003.500
    )
}
