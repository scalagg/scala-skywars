package gg.scala.skywars.plugin.game.kit.impl;

import gg.scala.skywars.plugin.game.kit.Kit;
import gg.scala.skywars.plugin.game.kit.KitAutoScan;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author puugz
 * @since 18/06/2021 22:51
 */
@KitAutoScan
public class DefaultKit implements Kit {

	@Override
	public String getName() {
		return "Default";
	}

	@Override
	public Material getIcon() {
		return Material.WEB;
	}

	@Override
	public ItemStack[] getArmorContents() {
		return new ItemStack[0];
	}

	@Override
	public ItemStack[] getInventoryContents() {
		return new ItemStack[] { new ItemStack(Material.IRON_AXE), new ItemStack(Material.IRON_PICKAXE) };
	}
}
