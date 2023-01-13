package gg.scala.skywars.plugin.game.chest.items

import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack

/**
 * @author GrowlyX
 * @since 7/31/2022
 */
class SingularLootItem(
    material: Material,
    enchantment: String?,
    enchantmentLevel: Int,
    private val amount: Int
) : AbstractLootItem(
    material, enchantment, enchantmentLevel
)
{
    override fun toItemStack(): ItemStack
    {
        if (this.cached == null)
        {
            this.cached = ItemStack(material, amount)

            if (enchantment != null)
            {
                this.cached?.addUnsafeEnchantment(
                    Enchantment.getByName(enchantment),
                    enchantmentLevel
                )
            }
        }

        return this.cached!!
    }
}
