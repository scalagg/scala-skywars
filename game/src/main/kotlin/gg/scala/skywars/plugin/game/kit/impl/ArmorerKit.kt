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
 * @since 18/06/2021 22:52
 */
@KitBind
@Service
@IgnoreAutoScan
object  ArmorerKit : Kit
{
    override val priority = 1
    override val name: String
        get() = "Armorer"
    override val icon: Material
        get() = Material.DIAMOND_CHESTPLATE
    override val armorContents: Array<ItemStack?>
        get()
        {
            val leggings = ItemStack(Material.IRON_LEGGINGS)
            val chestplate = ItemStack(Material.IRON_CHESTPLATE)
            return arrayOf(null, leggings, chestplate, null)
        }
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            val exp = ItemStack(Material.EXP_BOTTLE)
            val enchantmentTable = ItemStack(Material.ENCHANTMENT_TABLE)
            val anvil = ItemStack(Material.ANVIL)
            val books = ItemBuilder(Material.ENCHANTED_BOOK)
                .enchant(Enchantment.FIRE_ASPECT, 1)
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .amount(4)
                .build()

            // TODO: 20/06/2021 add enchants onto the books
            return arrayOf(exp, enchantmentTable, anvil, books)
        }
}
