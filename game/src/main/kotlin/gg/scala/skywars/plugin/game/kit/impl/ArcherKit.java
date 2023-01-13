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
 * @since 20/06/2021 00:45
 */
@KitBind
@Service
@IgnoreAutoScan
public class ArcherKit implements Kit {

	@Override
	public String getName() {
		return "Archer";
	}

	@Override
	public Material getIcon() {
		return Material.BOW;
	}

	@Override
	public ItemStack[] getArmorContents() {
		final ItemStack helmet = new ItemBuilder(Material.LEATHER_HELMET)
				.enchant(Enchantment.PROTECTION_PROJECTILE, 5)
				.build();

		return new ItemStack[] { null, null, null, helmet};
	}

	@Override
	public ItemStack[] getInventoryContents() {
		final ItemStack bow = new ItemBuilder(Material.BOW)
				.enchant(Enchantment.ARROW_DAMAGE, 3)
				.build();

		final ItemStack arrows = new ItemStack(Material.ARROW, 12);

		return new ItemStack[] { bow, arrows };
	}
}
