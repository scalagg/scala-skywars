package gg.scala.skywars.plugin.game.chest.items

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

/**
 * @author GrowlyX
 * @since 7/31/2022
 */
class RandomRangeLootItem(
    material: Material,
    enchantment: String?,
    enchantmentLevel: Int,
    private val range: IntRange
) : AbstractLootItem(
    material, enchantment, enchantmentLevel
)
{
    override fun toItemStack(): ItemStack
    {
        if (this.cached == null)
        {
            this.cached = ItemStack(material)

            if (enchantment != null)
            {
                this.cached?.addUnsafeEnchantment(
                    Enchantment.getByName(enchantment),
                    enchantmentLevel
                )
            }
        }

        val cached = this.cached!!
        cached.amount = this.range.random()

        return cached
    }
}
