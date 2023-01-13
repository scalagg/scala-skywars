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
 * @since 20/06/2021 01:15
 */
@KitBind
@Service
@IgnoreAutoScan
public class PyromanticKit implements Kit {

	@Override
	public String getName() {
		return "Pyromantic";
	}

	@Override
	public Material getIcon() {
		return Material.FLINT_AND_STEEL;
	}

	@Override
	public ItemStack[] getArmorContents() {
		final ItemStack boots = new ItemBuilder(Material.LEATHER_BOOTS)
				.enchant(Enchantment.PROTECTION_FIRE, 4)
				.build();

		final ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS)
				.enchant(Enchantment.PROTECTION_FIRE, 4)
				.build();

		final ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE)
				.enchant(Enchantment.PROTECTION_FIRE, 4)
				.build();

		final ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
				.enchant(Enchantment.PROTECTION_FIRE, 4)
				.build();

		return new ItemStack[] { boots, leggings, chestplate, helmet };
	}

	@Override
	public ItemStack[] getInventoryContents() {
		final ItemStack sword = new ItemBuilder(Material.WOOD_SWORD)
				.enchant(Enchantment.FIRE_ASPECT, 2)
				.build();

		final ItemStack fas = new ItemStack(Material.FLINT_AND_STEEL);

		final ItemStack lava = new ItemStack(Material.LAVA_BUCKET, 3);

		return new ItemStack[] { sword, fas, lava };
	}
}
