package gg.scala.skywars.plugin.game.kit.impl

import gg.scala.flavor.service.Service
import gg.scala.flavor.service.ignore.IgnoreAutoScan
import gg.scala.skywars.plugin.game.kit.Kit
import gg.scala.skywars.plugin.game.kit.KitBind
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author puugz
 * @since 18/06/2021 22:51
 */
@KitBind
@Service
@IgnoreAutoScan
class DefaultKit : Kit
{
    override val priority = 0

    override val name: String
        get() = "Default"
    override val icon: Material
        get() = Material.WEB
    override val armorContents: Array<ItemStack?>
        get() = arrayOfNulls(0)
    override val inventoryContents: Array<ItemStack?>
        get() = arrayOf(ItemStack(Material.IRON_AXE), ItemStack(Material.IRON_PICKAXE))
}
