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
 * @since 19/06/2021 18:14
 */
@KitBind
@Service
@IgnoreAutoScan
class GladiatorKit : Kit
{
    override val priority = 2
    override val name: String
        get() = "Gladiator"
    override val icon: Material
        get() = Material.DIAMOND_SWORD
    override val armorContents: Array<ItemStack?>
        get() = arrayOfNulls(0)
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            val sword = ItemStack(Material.DIAMOND_SWORD)
            val exp = ItemStack(Material.EXP_BOTTLE, 32)
            val enchantmentTable = ItemStack(Material.ENCHANTMENT_TABLE)
            val anvil = ItemStack(Material.ANVIL)
            val books = ItemBuilder(Material.ENCHANTED_BOOK)
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .amount(3)
                .build()
            // temp enchants
            return arrayOf(sword, exp, enchantmentTable, anvil, books)
        }
}
