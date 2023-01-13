package gg.scala.skywars.plugin.game.kit

import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Service
import gg.scala.skywars.plugin.game.SkyWarsGamePlugin
import gg.scala.skywars.plugin.game.service.PlayerServices
import gg.scala.skywars.plugin.game.utilities.GameUtilities
import org.bukkit.entity.Player

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

    fun applyKit(player: Player)
    {
        val model = PlayerServices.provide(player)

        var selectedKit = model?.selectedKit?.let { findKitByName(it) }
            ?: findKitByName("Default") ?: kits.first()

        GameUtilities.resetPlayer(player)

        player.inventory.contents = selectedKit.inventoryContents
        player.inventory.armorContents = selectedKit.armorContents
        player.updateInventory()
    }
}
