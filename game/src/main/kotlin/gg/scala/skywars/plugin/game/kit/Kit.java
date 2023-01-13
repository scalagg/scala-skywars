package gg.scala.skywars.plugin.game.kit;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author puugz
 * @since 18/06/2021 22:48
 */
public interface Kit {

	String getName();

	Material getIcon();

	ItemStack[] getArmorContents();

	ItemStack[] getInventoryContents();

}
