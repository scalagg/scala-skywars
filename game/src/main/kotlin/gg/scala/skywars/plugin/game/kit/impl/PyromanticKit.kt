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
 * @since 20/06/2021 01:15
 */
@KitBind
@Service
@IgnoreAutoScan
class PyromanticKit : Kit
{
    override val priority = 5
    override val name: String
        get() = "Pyromantic"
    override val icon: Material
        get() = Material.FLINT_AND_STEEL
    override val armorContents: Array<ItemStack?>
        get()
        {
            val boots = ItemBuilder(Material.LEATHER_BOOTS)
                .enchant(Enchantment.PROTECTION_FIRE, 4)
                .build()
            val leggings = ItemBuilder(Material.LEATHER_LEGGINGS)
                .enchant(Enchantment.PROTECTION_FIRE, 4)
                .build()
            val chestplate = ItemBuilder(Material.LEATHER_CHESTPLATE)
                .enchant(Enchantment.PROTECTION_FIRE, 4)
                .build()
            val helmet = ItemBuilder(Material.LEATHER_HELMET)
                .enchant(Enchantment.PROTECTION_FIRE, 4)
                .build()
            return arrayOf(boots, leggings, chestplate, helmet)
        }
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            val sword = ItemBuilder(Material.WOOD_SWORD)
                .enchant(Enchantment.FIRE_ASPECT, 2)
                .build()
            val fas = ItemStack(Material.FLINT_AND_STEEL)
            val lava = ItemStack(Material.LAVA_BUCKET, 3)
            return arrayOf(sword, fas, lava)
        }
}
