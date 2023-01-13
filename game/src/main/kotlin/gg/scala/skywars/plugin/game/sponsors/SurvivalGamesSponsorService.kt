package gg.scala.skywars.plugin.game.sponsors

import gg.scala.flavor.service.Service
import java.util.UUID

/**
 * @author AgentRKID
 * @since 8/10/2022
 */
@Service
object SurvivalGamesSponsorService
{
    val sponsors = mutableMapOf<UUID, MutableList<Sponsor>>()
    val sponsorsLock = mutableMapOf<UUID, UUID>()
}
