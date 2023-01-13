package gg.scala.skywars.plugin.game.chest

import gg.scala.flavor.inject.Inject
import gg.scala.flavor.service.Configure
import gg.scala.flavor.service.Service
import gg.scala.survivalgames.game.SurvivalGamesConfig
import gg.scala.skywars.plugin.game.SurvivalGamesGame
import gg.scala.skywars.plugin.game.chest.items.AbstractLootItem
import gg.scala.skywars.plugin.game.chest.items.RandomRangeLootItem
import gg.scala.skywars.plugin.game.chest.items.SingularLootItem
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.math.Chance
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

/**
 * @author GrowlyX
 * @since 7/31/2022
 */
@Service
object SurvivalGamesChestService
{
    @Inject
    lateinit var plugin: SurvivalGamesGame

    val chests = mutableMapOf<Location, Inventory>()
    private val lootItems = mutableListOf<AbstractLootItem>()

    private val lockedCompass = ItemBuilder.of(Material.COMPASS)
        .name("${CC.GRAY}Locked Compass")
        .addToLore("", "${CC.YELLOW}Right click to track the closest player to you!")
        .build()

    @Configure
    fun configure()
    {
        val config = plugin
            .config<SurvivalGamesConfig>()

        for ((index, chestItem) in config.chestItems.withIndex())
        {
            kotlin
                .runCatching {
                    val parts = chestItem
                        .split(",".toRegex())
                        .dropLastWhile { it.isEmpty() }
                        .toTypedArray()

                    val material = Material.valueOf(parts[0])

                    val enchantment = if (parts[2] != "null") parts[2] else null
                    val enchantmentLevel = parts[3].toInt()

                    if (parts[1].contains("-"))
                    {
                        val minMax = parts[1]
                            .split("-".toRegex())
                            .dropLastWhile { it.isEmpty() }
                            .toTypedArray()

                        val min = minMax[0].toInt()
                        val max = minMax[1].toInt()

                        lootItems.add(
                            RandomRangeLootItem(
                                material, enchantment, enchantmentLevel, min..max
                            )
                        )
                    } else
                    {
                        val amount = parts[1].toInt()

                        lootItems.add(
                            SingularLootItem(
                                material, enchantment, enchantmentLevel, amount
                            )
                        )
                    }
                }
                .onFailure {
                    plugin.logger.warning("Failed to load loot item number #$index!")
                }
        }

        plugin.logger.info("Loaded ${lootItems.size} chest loot item(s).")
    }

    fun openChest(
        player: Player, chest: Chest
    )
    {
        val inventory = chest.inventory

        if (!chests.containsKey(chest.location))
        {
            inventory.clear()

            for (i in 0 until (3..5).random())
            {
                val range = (0 until inventory.size)
                var position = range.random()

                while (inventory.getItem(position) != null)
                {
                    position = range.random()
                }

                if (!inventory.contains(lockedCompass.type) && Chance.percent(plugin.config<SurvivalGamesConfig>().lockedCompassSpawnRate))
                {
                    inventory.setItem(position, lockedCompass)
                    continue
                }

                var lootItem = lootItems.random()

                while (inventory.contains(lootItem.material))
                {
                    lootItem = lootItems.random()
                }

                inventory.setItem(position, lootItem.toItemStack())
            }

            chests[chest.location] = inventory
        }

        player.openInventory(chests[chest.location])
    }
}
