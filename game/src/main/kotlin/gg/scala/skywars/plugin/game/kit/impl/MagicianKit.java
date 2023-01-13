package gg.scala.skywars.plugin.game.kit.impl;

import gg.scala.flavor.service.Service;
import gg.scala.flavor.service.ignore.IgnoreAutoScan;
import gg.scala.skywars.plugin.game.kit.KitBind;
import net.evilblock.cubed.util.bukkit.ItemBuilder;
import gg.scala.skywars.plugin.game.kit.Kit;
import net.evilblock.cubed.util.CC;
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
 * @since 19/06/2021 18:14
 */
@KitBind
@Service
@IgnoreAutoScan
public class MagicianKit implements Kit {

	@Override
	public String getName() {
		return "Magician";
	}

	@Override
	public Material getIcon() {
		return Material.BREWING_STAND_ITEM;
	}

	@Override
	public ItemStack[] getArmorContents() {
		final ItemStack leggings = new ItemBuilder(Material.LEATHER_LEGGINGS).enchant(Enchantment.DURABILITY, 2).build();
		final ItemStack chestplate = new ItemBuilder(Material.LEATHER_CHESTPLATE).enchant(Enchantment.DURABILITY, 2).build();

		return new ItemStack[] { null, leggings, chestplate, null };
	}

	@Override
	public ItemStack[] getInventoryContents() {
		PotionMeta meta;

		final ItemStack wand = new ItemBuilder(Material.STICK)
				.name("Magic Wand")
				.enchant(Enchantment.DAMAGE_ALL, 4)
				.build();

		final ItemStack poison = new Potion(PotionType.POISON).splash().toItemStack(2);
		meta = (PotionMeta) poison.getItemMeta();

		meta.setDisplayName(CC.WHITE + "Splash Potion of Poison (20s)");
		meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 20 * 20, 0), true);
		poison.setItemMeta(meta);

		final ItemStack slowness = new Potion(PotionType.SLOWNESS).splash().toItemStack(2);
		meta = (PotionMeta) slowness.getItemMeta();

		meta.setDisplayName(CC.WHITE + "Splash Potion of Slowness (20s)");
		meta.addCustomEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 20, 0), true);
		slowness.setItemMeta(meta);

		final ItemStack harming = new Potion(PotionType.INSTANT_DAMAGE).splash().toItemStack(2);
		meta = (PotionMeta) harming.getItemMeta();

		meta.setDisplayName(CC.WHITE + "Splash Potion of Poison (20s)");
		meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 20 * 20, 0), true);
		harming.setItemMeta(meta);

		final ItemStack milk = new ItemStack(Material.MILK_BUCKET, 2);

		return new ItemStack[] { wand, poison, slowness, harming, milk };
	}
}
