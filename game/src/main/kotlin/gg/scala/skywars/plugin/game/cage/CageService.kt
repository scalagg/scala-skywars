package gg.scala.skywars.plugin.game.cage

import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Service
import gg.scala.skywars.plugin.game.SkyWarsGamePlugin
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.*

/**
 * @author puugz, GrowlyX
 * @since 20/06/2021 11:02
 */
@Service
object CageService
{
    @Inject
    lateinit var plugin: SkyWarsGamePlugin

    private val usedLocations = mutableMapOf<UUID, Location>()
    val locations = mutableListOf<Location>()

    fun openCages()
    {
        locations.forEach(::deleteCage)

        Bukkit.getOnlinePlayers()
            .forEach {
                it.noDamageTicks = 60
                it.fallDistance = 0.0f
            }
    }

    fun teleportAllToCage()
    {
        Bukkit.getOnlinePlayers().forEach(::teleportToCage)
    }

    private val unusedLocation: Location?
        get() = locations
            .filterNot { usedLocations.containsValue(it) }
            .firstOrNull()

    private fun teleportToCage(player: Player): Boolean
    {
        val location = unusedLocation

        if (location != null)
        {
            spawnCageGlass(location)
            usedLocations[player.uniqueId] = location
            player.teleport(location)
        }

        return location != null
    }

    fun spawnCageGlass(location: Location)
    {
        val x = location.blockX - 2
        val y = location.blockY - 1
        val z = location.blockZ - 2
        val world = location.world

        for (x2 in x until x + 5)
        {
            for (y2 in y until y + 5)
            {
                for (z2 in z until z + 5)
                {
                    val block = world.getBlockAt(x2, y2, z2)
                    if (block.type == Material.AIR)
                    {
                        block.type = Material.GLASS
                    }
                }
            }
        }
        for (x2 in x + 1 until x + 4)
        {
            for (y2 in y + 1 until y + 4)
            {
                for (z2 in z + 1 until z + 4)
                {
                    val block = world.getBlockAt(x2, y2, z2)
                    if (block.type == Material.GLASS)
                    {
                        block.type = Material.AIR
                    }
                }
            }
        }
    }

    private fun deleteCage(location: Location?)
    {
        val x = location!!.blockX - 2
        val y = location.blockY - 1
        val z = location.blockZ - 2
        val world = location.world

        for (x2 in x until x + 5)
        {
            for (y2 in y until y + 5)
            {
                for (z2 in z until z + 5)
                {
                    val block = world.getBlockAt(x2, y2, z2)
                    if (block.type == Material.GLASS)
                    {
                        block.type = Material.AIR
                    }
                }
            }
        }
    }
}
