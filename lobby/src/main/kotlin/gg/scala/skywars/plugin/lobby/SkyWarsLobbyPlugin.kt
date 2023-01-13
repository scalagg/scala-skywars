package gg.scala.skywars.plugin.lobby

import gg.scala.cgs.lobby.gamemode.CgsGameLobby
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.commons.annotations.container.ContainerEnable
import gg.scala.commons.core.plugin.Plugin
import gg.scala.commons.core.plugin.PluginAuthor
import gg.scala.commons.core.plugin.PluginDependency
import gg.scala.commons.core.plugin.PluginDependencyComposite
import gg.scala.commons.core.plugin.PluginWebsite

/**
 * @author GrowlyX
 * @since 2/22/2022
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
    PluginDependency("ScGameLobby"),
    PluginDependency("cloudsync", soft = true),
)
class SkyWarsLobbyPlugin : ExtendedScalaPlugin()
{
    @ContainerEnable
    fun containerEnable()
    {
        CgsGameLobby.INSTANCE = SkyWarsLobbyEngine
    }
}
