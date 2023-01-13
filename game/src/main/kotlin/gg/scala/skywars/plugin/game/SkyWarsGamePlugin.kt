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
    PluginDependency("ScGameFramework"),
    PluginDependency("Grape"),
    PluginDependency("cloudsync", soft = true)
)
class SkyWarsGamePlugin : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {
        val orchestration =
            loadConfig("orchestration.yml")

        val mode = orchestration.getString("mode")
            ?: "gg.scala.skywars.plugin.shared.gamemode.SkyWarsSoloGameMode"

        val modeClass = Class.forName(mode)

        val modeClassObject = modeClass
            .getField("INSTANCE")
            .get(null)

        val engine = SkyWarsEngine(
            this, SkyWarsGameInfo,
            modeClassObject as CgsGameMode
        )

        engine.initialLoad()

        SkyWarsEngine.INSTANCE = engine
    }
}
