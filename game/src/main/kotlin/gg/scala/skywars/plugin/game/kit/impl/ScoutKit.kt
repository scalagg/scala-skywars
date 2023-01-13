package gg.scala.skywars.plugin.game.kit.impl

import gg.scala.flavor.service.Service
import gg.scala.flavor.service.ignore.IgnoreAutoScan
import gg.scala.skywars.plugin.game.kit.Kit
import gg.scala.skywars.plugin.game.kit.KitBind
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.potion.Potion
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.potion.PotionType

/**
 * @author puugz
 * @since 19/06/2021 01:50
 */
@KitBind
@Service
@IgnoreAutoScan
class ScoutKit : Kit
{
    override val name: String
        get() = "Scout"
    override val icon: Material
        get() = Material.FEATHER
    override val armorContents: Array<ItemStack?>
        get() = arrayOfNulls(0)
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            val axe = ItemBuilder(Material.DIAMOND_AXE)
                .enchant(Enchantment.DIG_SPEED, 2)
                .enchant(Enchantment.DAMAGE_ALL, 1)
                .build()
            val pickaxe = ItemBuilder(Material.DIAMOND_PICKAXE)
                .enchant(Enchantment.DIG_SPEED, 2)
                .build()
            val potions = Potion(PotionType.SPEED)
                .splash()
                .toItemStack(3)
            val meta = potions.itemMeta as PotionMeta
            meta.displayName = CC.WHITE + "Splash Potion of Swiftness (35s)"
            meta.addCustomEffect(PotionEffect(PotionEffectType.SPEED, 20 * 35, 0), true)
            potions.itemMeta = meta
            return arrayOf(axe, pickaxe, potions)
        }
}
