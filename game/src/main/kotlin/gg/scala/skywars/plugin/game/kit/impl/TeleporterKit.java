package gg.scala.skywars.plugin.game.kit.impl;

import gg.scala.flavor.service.Service;
import gg.scala.flavor.service.ignore.IgnoreAutoScan;
import gg.scala.skywars.plugin.game.kit.Kit;
import gg.scala.skywars.plugin.game.kit.KitBind;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * @author puugz
 * @since 19/06/2021 18:18
 */
@KitBind
@Service
@IgnoreAutoScan
public class TeleporterKit implements Kit {

	@Override
	public String getName() {
		return "Teleporter";
	}

	@Override
	public Material getIcon() {
		return Material.ENDER_PEARL;
	}

	@Override
	public ItemStack[] getArmorContents() {
		return new ItemStack[0];
	}

	@Override
	public ItemStack[] getInventoryContents() {
		final ItemStack pearls = new ItemBuilder(Material.ENDER_PEARL)
				.name("Teleporter Pearl")
				.amount(2)
				.build();

		return new ItemStack[] { pearls };
	}
}
