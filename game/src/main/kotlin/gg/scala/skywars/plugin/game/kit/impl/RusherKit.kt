package gg.scala.skywars.plugin.game.kit.impl

import gg.scala.flavor.service.Service
import gg.scala.flavor.service.ignore.IgnoreAutoScan
import gg.scala.skywars.plugin.game.kit.Kit
import gg.scala.skywars.plugin.game.kit.KitBind
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

/**
 * @author puugz
 * @since 20/06/2021 01:13
 */
@KitBind
@Service
@IgnoreAutoScan
class RusherKit : Kit
{
    override val priority = 6
    override val name: String
        get() = "Rusher"
    override val icon: Material
        get() = Material.WOOD
    override val armorContents: Array<ItemStack?>
        get() = arrayOfNulls(0)
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            val pickaxe = ItemBuilder(Material.DIAMOND_PICKAXE)
                .enchant(Enchantment.DIG_SPEED, 1)
                .build()
            val axe = ItemBuilder(Material.DIAMOND_AXE)
                .enchant(Enchantment.DIG_SPEED, 1)
                .build()
            val wood = ItemStack(Material.WOOD, 64)
            return arrayOf(pickaxe, axe, null, wood)
        }
}
