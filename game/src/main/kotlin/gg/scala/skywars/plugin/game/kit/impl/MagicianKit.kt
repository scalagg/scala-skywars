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
 * @since 19/06/2021 18:14
 */
@KitBind
@Service
@IgnoreAutoScan
object MagicianKit : Kit
{
    override val priority = 8
    override val name: String
        get() = "Magician"
    override val icon: Material
        get() = Material.BREWING_STAND_ITEM
    override val armorContents: Array<ItemStack?>
        get()
        {
            val leggings = ItemBuilder(Material.LEATHER_LEGGINGS).enchant(Enchantment.DURABILITY, 2).build()
            val chestplate = ItemBuilder(Material.LEATHER_CHESTPLATE).enchant(Enchantment.DURABILITY, 2).build()
            return arrayOf(null, leggings, chestplate, null)
        }
    override val inventoryContents: Array<ItemStack?>
        get()
        {
            var meta: PotionMeta
            val wand = ItemBuilder(Material.STICK)
                .name("Magic Wand")
                .enchant(Enchantment.DAMAGE_ALL, 4)
                .build()
            val poison = Potion(PotionType.POISON).splash().toItemStack(2)
            meta = poison.itemMeta as PotionMeta
            meta.displayName = CC.WHITE + "Splash Potion of Poison (20s)"
            meta.addCustomEffect(PotionEffect(PotionEffectType.POISON, 20 * 20, 0), true)
            poison.itemMeta = meta
            val slowness = Potion(PotionType.SLOWNESS).splash().toItemStack(2)
            meta = slowness.itemMeta as PotionMeta
            meta.displayName = CC.WHITE + "Splash Potion of Slowness (20s)"
            meta.addCustomEffect(PotionEffect(PotionEffectType.SLOW, 20 * 20, 0), true)
            slowness.itemMeta = meta
            val harming = Potion(PotionType.INSTANT_DAMAGE).splash().toItemStack(2)
            meta = harming.itemMeta as PotionMeta
            meta.displayName = CC.WHITE + "Splash Potion of Poison (20s)"
            meta.addCustomEffect(PotionEffect(PotionEffectType.POISON, 20 * 20, 0), true)
            harming.itemMeta = meta
            val milk = ItemStack(Material.MILK_BUCKET, 2)
            return arrayOf(wand, poison, slowness, harming, milk)
        }
}
