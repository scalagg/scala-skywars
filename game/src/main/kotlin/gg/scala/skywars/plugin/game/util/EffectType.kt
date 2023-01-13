package gg.scala.skywars.plugin.game.util

import org.bukkit.potion.PotionEffectType

/**
 * @author AgentRKID
 * @since 8/10/2022
 */
enum class EffectType(var type: PotionEffectType)
{

    SPEED(PotionEffectType.SPEED),
    REGEN(PotionEffectType.REGENERATION)
}
