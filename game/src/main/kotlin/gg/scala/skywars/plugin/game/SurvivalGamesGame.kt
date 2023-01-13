package gg.scala.skywars.plugin.game

import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.core.plugin.Plugin
import gg.scala.commons.core.plugin.PluginAuthor
import gg.scala.commons.core.plugin.PluginDependency
import gg.scala.commons.core.plugin.PluginDependencyComposite
import gg.scala.commons.core.plugin.PluginWebsite
import gg.scala.skywars.plugin.shared.SkyWarsGameInfo
import gg.scala.skywars.plugin.game.listener.SurvivalGamesListener

/**
 * @author GrowlyX
 * @since 12/17/2021
 */
@Plugin(
    name = "SkyWars",
    version = "%remote%/%branch%/%id%"
)
@PluginAuthor("GrowlyX")
@PluginWebsite("https://scala.gg")
@PluginDependencyComposite(
    PluginDependency("scala-commons"),
    PluginDependency("Lemon"),
    PluginDependency("CGS-Engine"),
    PluginDependency("Grape"),
    PluginDependency("cloudsync", soft = true)
)
class SurvivalGamesGame : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {
        val orchestration =
            loadConfig("orchestration.yml")

        val mode = orchestration.getString("mode")
            ?: "gg.scala.survivalgames.shared.gamemode.SurvivalGamesSoloGameMode"

        val modeClass = Class.forName(mode)

        val modeClassObject = modeClass
            .getField("INSTANCE")
            .get(null)

        val engine = SurvivalGamesEngine(
            this, SkyWarsGameInfo,
            modeClassObject as CgsGameMode
        )

        engine.initialLoad()

        flavor {
            inject(SurvivalGamesListener)
        }

        server.pluginManager.registerEvents(
            SurvivalGamesListener, this
        )

        SurvivalGamesEngine.INSTANCE = engine
    }
}
