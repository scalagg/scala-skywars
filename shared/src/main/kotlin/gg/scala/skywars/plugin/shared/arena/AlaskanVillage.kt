package gg.scala.skywars.plugin.shared.arena

import gg.scala.cgs.common.information.arena.CgsGameArena
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import java.io.File
import java.nio.file.Path

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
object AlaskanVillage : CgsGameArena
{
    override fun getId() = "alaskan_village"
    override fun getName() = "Alaskan Village"

    override fun getMaterial() = Pair(Material.SNOW_BALL, 0)
    override fun getDescription() = "Alaskan village."

    override fun getDirectory(): Path? =
        File("plugins${File.separator}maps${File.separator}alaskan_village")
            .toPath()

    override fun getBukkitWorldName() = "alaskan_village"

    override fun getPreLobbyLocation() = Location(
        Bukkit.getWorld("alaskan_village"),
        257.500, 78.000, 256.500
    )

    override fun getSpectatorLocation() = Location(
        Bukkit.getWorld("alaskan_village"),
        257.500, 100.000, 256.500
    )
}
