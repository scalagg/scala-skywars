package gg.scala.skywars.plugin.game.kit.impl;

import gg.scala.skywars.plugin.game.kit.KitAutoScan;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import gg.scala.skywars.plugin.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * @author puugz
 * @since 19/06/2021 18:14
 */
@KitAutoScan
public class GladiatorKit implements Kit {

	@Override
	public String getName() {
		return "Gladiator";
	}

	@Override
	public Material getIcon() {
		return Material.DIAMOND_SWORD;
	}

	@Override
	public ItemStack[] getArmorContents() {
		return new ItemStack[0];
	}

	@Override
	public ItemStack[] getInventoryContents() {
		final ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
		final ItemStack exp = new ItemStack(Material.EXP_BOTTLE, 32);
		final ItemStack enchantmentTable = new ItemStack(Material.ENCHANTMENT_TABLE);
		final ItemStack anvil = new ItemStack(Material.ANVIL);
		final ItemStack books = new ItemBuilder(Material.ENCHANTED_BOOK)
				.enchant(Enchantment.DAMAGE_ALL, 1)
				.amount(3)
				.build();
		// temp enchants

		return new ItemStack[] { sword, exp, enchantmentTable, anvil, books };
	}
}
