package gg.scala.skywars.plugin.game.listener

import com.google.common.collect.BiMap
import com.google.common.collect.HashBiMap
import gg.scala.cgs.common.CgsGameEngine
import gg.scala.cgs.common.states.CgsGameState
import gg.scala.cgs.common.teams.CgsGameTeamService
import gg.scala.flavor.inject.Inject
import gg.scala.grape.GrapeSpigotPlugin
import gg.scala.lemon.util.QuickAccess
import gg.scala.survivalgames.game.SurvivalGamesConfig
import gg.scala.skywars.plugin.game.SurvivalGamesGame
import gg.scala.skywars.plugin.game.chest.SurvivalGamesChestService
import gg.scala.skywars.plugin.game.sit
import gg.scala.skywars.plugin.game.sponsors.SurvivalGamesSponsorService
import gg.scala.survivalgames.game.sponsors.SurvivalGamesSponsorConfig
import me.lucko.helper.Events
import me.lucko.helper.utils.Players
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.Tasks
import net.kyori.adventure.platform.bukkit.BukkitAudiences
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemHeldEvent
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import java.util.*

/**
 * @author GrowlyX, puugz
 * @since 12/17/2021
 */
object SurvivalGamesListener : Listener
{
    @Inject
    lateinit var plugin: SurvivalGamesGame

    val killsTracker = mutableMapOf<UUID, Int>()

    private val compassTracker: BiMap<UUID, UUID> = HashBiMap.create()
    private val currentlyTracking = hashSetOf<UUID>()
    private val audiences by lazy { BukkitAudiences.create(plugin) }

    val spawnPoints = mutableListOf<Location>()
    val deathMatchSpawnPoints = mutableListOf<Location>()

    lateinit var centerLocation: Location

    init {
        // Fixes some issue with the Interaction Event (Someone needs to look into this)
        Events.subscribe(PlayerInteractEvent::class.java).handler { event ->
            onInteract(event)
        }

        // Update compass direction and action bar
        Tasks.asyncTimer(0L, 10L) {
            for (entry in compassTracker) {
                if (!currentlyTracking.contains(entry.key)) {
                    continue
                }

                val tracker = Bukkit.getPlayer(entry.key)
                val tracked = Bukkit.getPlayer(entry.value)

                // Set the compass to target towards the player's direction
                tracker.compassTarget = tracked.location.clone()

                audiences.player(tracker)
                    .sendActionBar(LegacyComponentSerializer.legacySection()
                        .deserialize(
                            "${CC.B_WHITE}TARGET: ${CC.RED}${tracked.name} ${CC.B_WHITE}DIST: ${CC.GREEN}${tracker.location.distance(tracked.location).toString().format("%.2f")}m"
                        ))
            }
        }
    }

    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent)
    {
        val config = plugin
            .config<SurvivalGamesConfig>()

        if (!config.breakableBlocks.contains(event.block.type.name))
        {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onBlockPlace(event: BlockPlaceEvent)
    {
        val config = plugin
            .config<SurvivalGamesConfig>()

        if (!config.placeableBlocks.contains(event.blockPlaced.type.name))
        {
            event.isCancelled = true
        }
    }

   /* @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )*/
    fun onInteract(event: PlayerInteractEvent)
    {
        val player = event.player

        if (
            event.hasBlock() &&
            event.clickedBlock.type == Material.CHEST &&
            event.action.name.contains("RIGHT") &&
            !player.hasMetadata("spectator") &&
            CgsGameEngine.INSTANCE.gameState == CgsGameState.STARTED
        )
        {
            val chest = event.clickedBlock.state as Chest
            SurvivalGamesChestService.openChest(player, chest)
        }

        if (
                event.hasItem() &&
                event.item.type == Material.COMPASS &&
                !player.hasMetadata("spectator") &&
                CgsGameEngine.INSTANCE.gameState == CgsGameState.STARTED
        )
        {
            if (compassTracker.contains(player.uniqueId))
            {
                player.sendMessage("${CC.RED}You've already activated this tracker, you cannot active it again!")
                return
            }

            Tasks.async {
                var closestPlayer: Player? = null
                var closestDistance = Double.MAX_VALUE

                // Find the closest player to the tracker
                for (team in CgsGameTeamService.teams.values)
                {
                    for (alive in team.alive)
                    {
                        if (alive == player.uniqueId)
                        {
                            continue
                        }

                        val alivePlayer = Bukkit.getPlayer(alive)
                        val distance = player.location.distance(alivePlayer.location)

                        if (distance < closestDistance)
                        {
                            closestPlayer = alivePlayer
                            closestDistance = distance
                        }
                    }
                }
                compassTracker[player.uniqueId] = closestPlayer?.uniqueId
                currentlyTracking.add(player.uniqueId)
                player.sendMessage("${CC.GREEN}You're now tracking ${CC.RED}${closestPlayer?.name}${CC.GREEN}.")
            }
        }
    }

    @EventHandler
    fun onItemDrop(event: PlayerDropItemEvent)
    {
        if (event.itemDrop.itemStack.type == Material.COMPASS)
        {
            event.isCancelled = true
            event.player.sendMessage("${CC.RED}You cannot drop your tracker!")
        }
    }

    @EventHandler
    fun onInventory(event: InventoryClickEvent)
    {
        if (event.currentItem.type == Material.COMPASS && compassTracker.containsKey(event.whoClicked.uniqueId))
        {
            event.isCancelled = true
            event.whoClicked.sendMessage("${CC.RED}You cannot gather or remove your tracker!")
        }
    }

    @EventHandler
    fun onItemTrackerHeld(event: PlayerItemHeldEvent)
    {
        val player = event.player
        val newItem = event.player.inventory.getItem(event.newSlot)

        if (newItem != null && newItem.type == Material.COMPASS && compassTracker.containsKey(event.player.uniqueId))
        {
            currentlyTracking.add(player.uniqueId)
        } else
        {
            // They're no longer using the compass, remove their action bar
            if (currentlyTracking.remove(player.uniqueId))
            {

                audiences.player(player)
                    .sendActionBar(LegacyComponentSerializer.legacySection()
                        .deserialize(""))
            }
        }
    }

    @EventHandler
    fun onGameStarting(
        event: CgsGameEngine.CgsGamePreStartEvent
    )
    {
        Players.all()
            .forEachIndexed { index, player ->
                player.teleport(spawnPoints[index])
                player.sit(true)
            }
    }

    @EventHandler
    fun onDeath(
        event: PlayerDeathEvent
    )
    {
        event.entity.world
            .strikeLightningEffect(
                event.entity.location
            )

        event.entity.killer ?: return
        event.drops.removeIf { stack -> stack.type == Material.COMPASS }

        killsTracker[event.entity.killer.uniqueId] =
            (killsTracker[event.entity.killer.uniqueId] ?: 0) + 1

        val tracker = Bukkit.getPlayer(compassTracker.inverse()[event.entity.uniqueId]);

        if (tracker != null)
        {
            compassTracker.remove(tracker.uniqueId)
            currentlyTracking.remove(tracker.uniqueId)
            tracker.inventory.remove(Material.COMPASS)
            tracker.sendMessage("${CC.GREEN}The player you were tracking died, so your tracker has been destroyed!")
        }
    }

    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent)
    {
        // After coming down and taking fall damage after being seated, it causes an issue with fall damage.
        if (event.entity.hasMetadata("seated") && event.cause == EntityDamageEvent.DamageCause.FALL)
        {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onGameStart(
        event: CgsGameEngine.CgsGameStartEvent
    )
    {
        Players.all()
            .forEach { player ->
                player.sit(false)
                killsTracker[player.uniqueId] = 0

                player.removeMetadata("spectator", plugin)
                player.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 10, 1))
                Players.all()
                    .forEach { other ->
                        player.showPlayer(other)
                    }
            }
    }

    @EventHandler(
        priority = EventPriority.HIGHEST,
        ignoreCancelled = true
    )
    fun onParticipantConnect(
        event: CgsGameEngine.CgsGameParticipantConnectEvent
    )
    {
        if (CgsGameEngine.INSTANCE.gameState == CgsGameState.STARTING)
        {
            event.participant.teleport(
                spawnPoints[Players.all().indexOf(event.participant)]
            )

            event.participant.sit(true)
        } else if (CgsGameEngine.INSTANCE.gameState == CgsGameState.STARTED)
        {
            event.participant.teleport(
                spawnPoints[Players.all().indexOf(event.participant)]
            )

            killsTracker[event.participant.uniqueId] = 0
        }
    }

    @EventHandler(
        priority = EventPriority.LOWEST,
        ignoreCancelled = true
    )
    fun onGameEnd(
        event: CgsGameEngine.CgsGameEndEvent
    )
    {
        val winner = CgsGameEngine.INSTANCE.winningTeam.alive[0]
        val sponsors = SurvivalGamesSponsorService.sponsors.remove(winner)

        if (sponsors != null)
        {
            for (sponsor in sponsors)
            {
                val player = Bukkit.getPlayer(sponsor.uniqueId)

                if (player != null)
                {
                    val grapePlayer = GrapeSpigotPlugin.getInstance().playerHandler.getByPlayer(player)

                    val reward =
                        sponsor.sponsorAmount * CgsGameEngine.INSTANCE.plugin.config<SurvivalGamesSponsorConfig>().sponsorCoinMulti
                    grapePlayer.coins += reward

                    grapePlayer.save().thenRun {
                        player.sendMessage(
                            "${CC.GREEN}You've been rewarded $reward coins for sponsoring ${
                                QuickAccess.coloredName(
                                    winner
                                )
                            }${CC.GREEN}."
                        )
                    }
                }
            }
        }
    }
}
