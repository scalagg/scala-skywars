package gg.scala.skywars.plugin.game.kit.impl;

import gg.scala.flavor.service.Service;
import gg.scala.flavor.service.ignore.IgnoreAutoScan;
import gg.scala.skywars.plugin.game.kit.KitBind;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import gg.scala.skywars.plugin.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * @author puugz
 * @since 18/06/2021 22:52
 */
@KitBind
@Service
@IgnoreAutoScan
public class ArmorerKit implements Kit {

	@Override
	public String getName() {
		return "Armorer";
	}

	@Override
	public Material getIcon() {
		return Material.DIAMOND_CHESTPLATE;
	}

	@Override
	public ItemStack[] getArmorContents() {
		final ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
		final ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);

		return new ItemStack[] { null, leggings, chestplate, null };
	}

	@Override
	public ItemStack[] getInventoryContents() {
		final ItemStack exp = new ItemStack(Material.EXP_BOTTLE);
		final ItemStack enchantmentTable = new ItemStack(Material.ENCHANTMENT_TABLE);
		final ItemStack anvil = new ItemStack(Material.ANVIL);
		final ItemStack books = new ItemBuilder(Material.ENCHANTED_BOOK)
				.enchant(Enchantment.FIRE_ASPECT, 1)
				.enchant(Enchantment.DAMAGE_ALL, 1)
				.amount(4)
				.build();

		// TODO: 20/06/2021 add enchants onto the books

		return new ItemStack[] { exp, enchantmentTable, anvil, books };
	}
}
