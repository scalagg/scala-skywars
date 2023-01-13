package gg.scala.skywars.plugin.game.kit

import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Service
import gg.scala.skywars.plugin.game.SkyWarsGamePlugin

/**
 * @author GrowlyX
 * @since 1/12/2023
 */
@Service
object KitService
{
    @Inject
    lateinit var plugin: SkyWarsGamePlugin

    val kits = mutableListOf<Kit>()

    fun findKitByName(name: String) = kits
        .firstOrNull {
            it.name.equals(name, true)
        }
}
