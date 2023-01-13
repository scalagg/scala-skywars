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
 * @since 20/06/2021 01:10
 */
@KitBind
@Service
@IgnoreAutoScan
class ChickenKit : Kit
{
    override val priority = 7
    override val name: String
        get() = "Chicken"
    override val icon: Material
        get() = Material.EGG
    override val armorContents: Array<ItemStack?>
        get()
        {
            val boots = ItemStack(Material.LEATHER_BOOTS)
            val leggings = ItemStack(Material.IRON_LEGGINGS)
            val chestplate = ItemStack(Material.LEATHER_CHESTPLATE)
            val helmet = ItemStack(Material.LEATHER_HELMET)
            return arrayOf(boots, leggings, chestplate, helmet)
        }
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            val sword = ItemBuilder(Material.STONE_SWORD)
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .build()
            val eggs = ItemStack(Material.EGG, 192)
            return arrayOf(sword, eggs)
        }
}
