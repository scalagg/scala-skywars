package gg.scala.skywars.plugin.game.kit.impl;

import gg.scala.skywars.plugin.game.kit.KitAutoScan;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import gg.scala.skywars.plugin.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * @author puugz
 * @since 20/06/2021 01:13
 */
@KitAutoScan
public class RusherKit implements Kit {

	@Override
	public String getName() {
		return "Rusher";
	}

	@Override
	public Material getIcon() {
		return Material.WOOD;
	}

	@Override
	public ItemStack[] getArmorContents() {
		return new ItemStack[0];
	}

	@Override
	public ItemStack[] getInventoryContents() {
		final ItemStack pickaxe = new ItemBuilder(Material.DIAMOND_PICKAXE)
				.enchant(Enchantment.DIG_SPEED, 1)
				.build();

		final ItemStack axe = new ItemBuilder(Material.DIAMOND_AXE)
				.enchant(Enchantment.DIG_SPEED, 1)
				.build();

		final ItemStack wood = new ItemStack(Material.WOOD, 64);

		return new ItemStack[] { pickaxe, axe, null, wood };
	}
}
