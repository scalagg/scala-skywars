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
 * @since 20/06/2021 01:10
 */
@KitBind
@Service
@IgnoreAutoScan
public class ChickenKit implements Kit {

	@Override
	public String getName() {
		return "Chicken";
	}

	@Override
	public Material getIcon() {
		return Material.EGG;
	}

	@Override
	public ItemStack[] getArmorContents() {
		final ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
		final ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
		final ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
		final ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);

		return new ItemStack[] { boots, leggings, chestplate, helmet };
	}

	@Override
	public ItemStack[] getInventoryContents() {
		final ItemStack sword = new ItemBuilder(Material.STONE_SWORD)
				.enchant(Enchantment.DAMAGE_ALL, 1)
				.build();

		final ItemStack eggs = new ItemStack(Material.EGG, 192);

		return new ItemStack[] { sword, eggs };
	}
}
