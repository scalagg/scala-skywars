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
 * @since 20/06/2021 00:45
 */
@KitBind
@Service
@IgnoreAutoScan
class ArcherKit : Kit
{
    override val priority = 4
    override val name: String
        get() = "Archer"
    override val icon: Material
        get() = Material.BOW
    override val armorContents: Array<ItemStack?>
        get()
        {
            val helmet = ItemBuilder(Material.LEATHER_HELMET)
                .enchant(Enchantment.PROTECTION_PROJECTILE, 5)
                .build()
            return arrayOf(null, null, null, helmet)
        }
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            val bow = ItemBuilder(Material.BOW)
                .enchant(Enchantment.ARROW_DAMAGE, 3)
                .build()
            val arrows = ItemStack(Material.ARROW, 12)
            return arrayOf(bow, arrows)
        }
}
