package gg.scala.skywars.plugin.game.kit.impl

import gg.scala.flavor.service.Service
import gg.scala.flavor.service.ignore.IgnoreAutoScan
import gg.scala.skywars.plugin.game.kit.Kit
import gg.scala.skywars.plugin.game.kit.KitBind
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author puugz
 * @since 19/06/2021 18:18
 */
@KitBind
@Service
@IgnoreAutoScan
class TeleporterKit : Kit
{
    override val priority = 3
    override val name: String
        get() = "Teleporter"
    override val icon: Material
        get() = Material.ENDER_PEARL
    override val armorContents: Array<ItemStack?>
        get() = arrayOfNulls(0)
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            val pearls = ItemBuilder(Material.ENDER_PEARL)
                .name("Teleporter Pearl")
                .amount(2)
                .build()
            return arrayOf(pearls)
        }
}
