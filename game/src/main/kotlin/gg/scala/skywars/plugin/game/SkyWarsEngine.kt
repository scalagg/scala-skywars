package gg.scala.skywars.plugin.game

import com.cryptomorin.xseries.XMaterial
import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.deathmatch.DeathmatchConfiguration
import gg.scala.cgs.common.information.CgsGameGeneralInfo
import gg.scala.cgs.common.information.arena.CgsGameArenaHandler
import gg.scala.cgs.common.information.mode.CgsGameMode
import gg.scala.cgs.common.snapshot.CgsGameSnapshot
import gg.scala.cgs.common.voting.VotingMapConfiguration
import gg.scala.cgs.common.voting.VotingMapEntry
import gg.scala.cgs.common.voting.selection.VoteSelectionType
import gg.scala.commons.ExtendedScalaPlugin
import gg.scala.lemon.util.CubedCacheUtil
import gg.scala.skywars.plugin.game.listener.SkyWarsListener
import gg.scala.skywars.plugin.game.frontend.SkyWarsNametagAdapter
import gg.scala.skywars.plugin.game.frontend.SkyWarsScoreboardAdapter
import gg.scala.skywars.plugin.game.frontend.SkyWarsVisibilityAdapter
import gg.scala.skywars.plugin.shared.SkyWarsPlayerModel
import me.lucko.helper.utils.Players
import net.evilblock.cubed.util.CC
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.time.Duration
import java.util.concurrent.CompletableFuture
import kotlin.properties.Delegates

/**
 * @author GrowlyX
 * @since 12/3/2021
 */
class SkyWarsEngine(
    plugin: ExtendedScalaPlugin,
    gameInfo: CgsGameGeneralInfo,
    gameMode: CgsGameMode
) : CgsGameEngine<SkyWarsPlayerModel>(
    plugin, gameInfo, gameMode,
    SkyWarsPlayerModel::class
)
{
    companion object
    {
        @JvmStatic
        var INSTANCE by Delegates.notNull<SkyWarsEngine>()
    }

    override fun getScoreboardRenderer() = SkyWarsScoreboardAdapter
    override fun getVisibilityAdapter() = SkyWarsVisibilityAdapter
    override fun getNametagAdapter() = SkyWarsNametagAdapter

    private val votingMapConfiguration = object : VotingMapConfiguration
    {
        override val minimumPlayersForVotingStart = 4
        override val selectionType = VoteSelectionType.GUI
        override val votingAutoCloseDuration = Duration.ofSeconds(20L)

        override fun entries() = listOf(
            VotingMapEntry("sg_4", XMaterial.DEAD_BUSH, "Abandoned city...", "SG 4"),
            VotingMapEntry("alaskan_village", XMaterial.OAK_SAPLING, "Snowy!", "Alaskan Village")
        ).toMutableList().take(5)

        override fun preStartLobby() = Bukkit.getWorld("world").spawnLocation
    }

    override fun getVotingConfig() = votingMapConfiguration

    override fun onAsyncPreStartResourceInitialization(): CompletableFuture<Boolean>
    {
        return CompletableFuture.supplyAsync {
            val arenaData = File(
                CgsGameArenaHandler.arena.getDirectory()!!.toFile(),
                "data.yml"
            )

            val yaml = YamlConfiguration
                .loadConfiguration(arenaData)

            fun mapToLocation(line: String): Location
            {
                val split = line.split(", ")

                return Location(
                    Bukkit.getWorld(split[0]),
                    split[1].toDouble(),
                    split[2].toDouble(),
                    split[3].toDouble(),
                    split[4].toFloat(),
                    split[5].toFloat()
                )
            }

            SkyWarsListener.spawnPoints
                .addAll(
                    yaml.getStringList("spawnpoints")
                        .map(::mapToLocation)
                )

            SkyWarsListener.deathMatchSpawnPoints
                .addAll(
                    yaml.getStringList("deathmatchPoints")
                        .map(::mapToLocation)
                )

            SkyWarsListener.centerLocation =
                mapToLocation(
                    yaml.getString("center-location")
                )

            return@supplyAsync true
        }
    }

    override fun getDeathmatchConfig() = object : DeathmatchConfiguration
    {
        override fun getMinimumToStartDeathMatch() = 4

        override fun getStartTime() = Duration.ofSeconds(10L)
        override fun getTeleportationTime() = Duration.ofSeconds(10L)

        override fun onStart()
        {
            Players.all()
                .filter {
                    !it.hasMetadata("spectator")
                }
                .forEach {
                    it sit false
                }
        }

        override fun onTeleporation()
        {

            for (
            (indexed, player) in Players.all()
                .filter {
                    !it.hasMetadata("spectator")
                }
                .withIndex()
            )
            {
                player.teleport(
                    SkyWarsListener.deathMatchSpawnPoints[indexed]
                )
                player sit true
            }
        }
    }

    override fun getGameSnapshot(): CgsGameSnapshot
    {
        return object : CgsGameSnapshot
        {
            override fun getExtraInformation(): List<String>
            {
                val topKills = mutableListOf<String>()

                val sortedKills = SkyWarsListener.killsTracker
                    .entries.sortedByDescending { it.value }

                for (i in 0 until 3.coerceAtMost(sortedKills.size))
                {
                    val (key, value) = sortedKills[i]
                    topKills.add("  ${CC.YELLOW}#${i + 1} ${CC.GRAY}- ${CC.WHITE}${CubedCacheUtil.fetchName(key)}${CC.GRAY} - ${CC.PRI}$value")
                }

                return topKills
            }
        }
    }
}
