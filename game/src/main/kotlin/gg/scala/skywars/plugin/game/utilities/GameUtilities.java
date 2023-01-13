package gg.scala.skywars.plugin.game.utilities;

import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.GameMode;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameUtilities {

	public static String numberToNumeral(int number) {
		switch (number) {
			case 1:
				return "I";
			case 2:
				return "II";
			case 3:
				return "III";
			case 4:
				return "IV";
			case 5:
				return "V";
			case 6:
				return "VI";
			case 7:
				return "VII";
			case 8:
				return "VIII";
			case 9:
				return "IX";
			case 10:
				return "X";
			default:
				return String.valueOf(number);
		}
	}

	public static String getEnchantmentName(Enchantment enchantment) {
		String output = "";

		switch (enchantment.getName()) {
			case "PROTECTION_ENVIRONMENTAL":
				output = "Protection";
				break;
			case "PROTECTION_FIRE":
				output = "Fire Protection";
				break;
			case "PROTECTION_FALL":
				output = "Feather Falling";
				break;
			case "PROTECTION_EXPLOSIONS":
				output = "Blast Protection";
				break;
			case "PROTECTION_PROJECTILE":
				output = "Projectile Protection";
				break;
			case "OXYGEN":
				output = "Respiration";
				break;
			case "WATER_WORKER":
				output = "Aqua Affinity";
				break;
			case "THORNS":
				output = "Thorns";
				break;
			case "DEPTH_STRIDER":
				output = "Depth Strider";
				break;
			case "DAMAGE_ALL":
				output = "Sharpness";
				break;
			case "DAMAGE_UNDEAD":
				output = "Smite";
				break;
			case "DAMAGE_ARTHROPODS":
				output = "Bane of Arthropods";
				break;
			case "KNOCKBACK":
				output = "Knockback";
				break;
			case "FIRE_ASPECT":
				output = "Fire Aspect";
				break;
			case "LOOT_BONUS_MOBS":
				output = "Looting";
				break;
			case "DIG_SPEED":
				output = "Haste";
				break;
			case "SILK_TOUCH":
				output = "Silk Touch";
				break;
			case "DURABILITY":
				output = "Unbreaking";
				break;
			case "LOOT_BONUS_BLOCKS":
				output = "Fortune";
				break;
			case "ARROW_DAMAGE":
				output = "Power";
				break;
			case "ARROW_KNOCKBACK":
				output = "Punch";
				break;
			case "ARROW_FIRE":
				output = "Flame";
				break;
			case "ARROW_INFINITE":
				output = "Infinity";
				break;
			case "LUCK":
				output = "Luck of the Sea";
				break;
			case "LURE":
				output = "Lure";
				break;
		}

		return output;
	}

	public static void resetPlayer(Player player) {
		GameUtilities.resetPlayer(player, false);
	}

	public static void resetPlayer(Player player, boolean resetHeldItemSlot) {
		player.getInventory().setArmorContents(new ItemStack[4]);
		player.getInventory().setContents(new ItemStack[36]);
		player.setHealth(20.0D);
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0f);
		player.setFireTicks(0);
		player.setMaximumNoDamageTicks(20);
		player.setSaturation(20);
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setGameMode(GameMode.SURVIVAL);
		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		if (resetHeldItemSlot) player.getInventory().setHeldItemSlot(0);
		player.updateInventory();
	}


	public static String formatMillis(long millis) {
		return DurationFormatUtils.formatDurationWords(millis, true, true);
	}

	public static String formatSeconds(int seconds) {
		return GameUtilities.formatMillis(seconds * 1000L);
	}

	public static String secondsToTime(int secs) {
		if (secs <= 0) return "";

		int hours = secs / 3600;
		int minutes = secs % 3600 / 60;
		int seconds = secs % 60;

		return (hours > 0 ? hours + "h " : "") + (minutes > 0 ? minutes + "m " : "") + (seconds > 0 ? seconds + "s" : "");
	}
}
