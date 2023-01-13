package gg.scala.skywars.plugin.game.sponsors

import gg.scala.skywars.plugin.game.util.EffectType
import org.bukkit.Material
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

/**
 * @author AgentRKID
 * @since 8/10/2022
 */
class SponsorEffect(var material: Material, var effect: EffectType, var amp: Int = 1, var duration: Int = 3, var cost: Int = 100)
{
    fun toPotionEffect(duration: Int): PotionEffect
    {
        return PotionEffect(effect.type, duration, amp)
    }
}
