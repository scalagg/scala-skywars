package gg.scala.skywars.plugin.game.kit.impl;

import gg.scala.flavor.service.Service;
import gg.scala.flavor.service.ignore.IgnoreAutoScan;
import gg.scala.skywars.plugin.game.kit.KitBind;
import net.evilblock.cubed.util.CC;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import gg.scala.skywars.plugin.game.kit.Kit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

/**
 * @author puugz
 * @since 19/06/2021 01:50
 */
@KitBind
@Service
@IgnoreAutoScan
public class ScoutKit implements Kit {

	@Override
	public String getName() {
		return "Scout";
	}

	@Override
	public Material getIcon() {
		return Material.FEATHER;
	}

	@Override
	public ItemStack[] getArmorContents() {
		return new ItemStack[0];
	}

	@Override
	public ItemStack[] getInventoryContents() {
		final ItemStack axe = new ItemBuilder(Material.DIAMOND_AXE)
				.enchant(Enchantment.DIG_SPEED, 2)
				.enchant(Enchantment.DAMAGE_ALL, 1)
				.build();

		final ItemStack pickaxe = new ItemBuilder(Material.DIAMOND_PICKAXE)
				.enchant(Enchantment.DIG_SPEED, 2)
				.build();

		final ItemStack potions = new Potion(PotionType.SPEED)
				.splash()
				.toItemStack(3);

		final PotionMeta meta = (PotionMeta) potions.getItemMeta();

		meta.setDisplayName(CC.WHITE + "Splash Potion of Swiftness (35s)");
		meta.addCustomEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 35, 0), true);

		potions.setItemMeta(meta);

		return new ItemStack[] { axe, pickaxe, potions };
	}
}
