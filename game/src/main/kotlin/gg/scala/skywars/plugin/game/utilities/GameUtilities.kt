package gg.scala.skywars.plugin.game.utilities

import org.apache.commons.lang.time.DurationFormatUtils
import org.bukkit.GameMode
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.potion.PotionEffect
import java.util.function.Consumer

object GameUtilities
{
    fun numberToNumeral(number: Int): String
    {
        return when (number)
        {
            1 -> "I"
            2 -> "II"
            3 -> "III"
            4 -> "IV"
            5 -> "V"
            6 -> "VI"
            7 -> "VII"
            8 -> "VIII"
            9 -> "IX"
            10 -> "X"
            else -> number.toString()
        }
    }

    fun getEnchantmentName(enchantment: Enchantment): String
    {
        var output = ""
        when (enchantment.name)
        {
            "PROTECTION_ENVIRONMENTAL" -> output = "Protection"
            "PROTECTION_FIRE" -> output = "Fire Protection"
            "PROTECTION_FALL" -> output = "Feather Falling"
            "PROTECTION_EXPLOSIONS" -> output = "Blast Protection"
            "PROTECTION_PROJECTILE" -> output = "Projectile Protection"
            "OXYGEN" -> output = "Respiration"
            "WATER_WORKER" -> output = "Aqua Affinity"
            "THORNS" -> output = "Thorns"
            "DEPTH_STRIDER" -> output = "Depth Strider"
            "DAMAGE_ALL" -> output = "Sharpness"
            "DAMAGE_UNDEAD" -> output = "Smite"
            "DAMAGE_ARTHROPODS" -> output = "Bane of Arthropods"
            "KNOCKBACK" -> output = "Knockback"
            "FIRE_ASPECT" -> output = "Fire Aspect"
            "LOOT_BONUS_MOBS" -> output = "Looting"
            "DIG_SPEED" -> output = "Haste"
            "SILK_TOUCH" -> output = "Silk Touch"
            "DURABILITY" -> output = "Unbreaking"
            "LOOT_BONUS_BLOCKS" -> output = "Fortune"
            "ARROW_DAMAGE" -> output = "Power"
            "ARROW_KNOCKBACK" -> output = "Punch"
            "ARROW_FIRE" -> output = "Flame"
            "ARROW_INFINITE" -> output = "Infinity"
            "LUCK" -> output = "Luck of the Sea"
            "LURE" -> output = "Lure"
        }
        return output
    }

    fun resetPlayer(player: Player)
    {
        resetPlayer(player, false)
    }

    fun resetPlayer(player: Player, resetHeldItemSlot: Boolean)
    {
        player.inventory.armorContents = arrayOfNulls(4)
        player.inventory.contents = arrayOfNulls(36)
        player.health = 20.0
        player.foodLevel = 20
        player.level = 0
        player.exp = 0f
        player.fireTicks = 0
        player.maximumNoDamageTicks = 20
        player.saturation = 20f
        player.allowFlight = false
        player.isFlying = false
        player.gameMode = GameMode.SURVIVAL
        player.activePotionEffects.forEach(Consumer { effect: PotionEffect -> player.removePotionEffect(effect.type) })
        if (resetHeldItemSlot) player.inventory.heldItemSlot = 0
        player.updateInventory()
    }

    fun formatMillis(millis: Long): String
    {
        return DurationFormatUtils.formatDurationWords(millis, true, true)
    }

    @JvmStatic
	fun formatSeconds(seconds: Int): String
    {
        return formatMillis(seconds * 1000L)
    }

    fun secondsToTime(secs: Int): String
    {
        if (secs <= 0) return ""
        val hours = secs / 3600
        val minutes = secs % 3600 / 60
        val seconds = secs % 60
        return (if (hours > 0) hours.toString() + "h " else "") + (if (minutes > 0) minutes.toString() + "m " else "") + if (seconds > 0) seconds.toString() + "s" else ""
    }
}
