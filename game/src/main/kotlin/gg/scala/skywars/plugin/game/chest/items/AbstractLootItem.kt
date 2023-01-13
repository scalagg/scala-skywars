package gg.scala.skywars.plugin.game.chest.items

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author GrowlyX
 * @since 7/31/2022
 */
abstract class AbstractLootItem(
    val material: Material,
    val enchantment: String?,
    val enchantmentLevel: Int
)
{
    protected var cached: ItemStack? = null

    abstract fun toItemStack(): ItemStack
}
