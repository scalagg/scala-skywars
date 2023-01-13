package gg.scala.skywars.plugin.game.kit

import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @author puugz
 * @since 18/06/2021 22:48
 */
interface Kit
{
    val name: String
    val icon: Material
    val armorContents: Array<ItemStack?>
    val inventoryContents: Array<ItemStack?>
}
