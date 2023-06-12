package gg.scala.skywars.plugin.game.chest

import gg.scala.flavor.service.Service
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.craftbukkit.v1_8_R3.block.CraftChest
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.Potion
import org.bukkit.potion.PotionType
import java.util.*
import java.util.concurrent.ThreadLocalRandom

/**
 * @author puugz, GrowlyX
 * @since 19/06/2021 15:25
 */
@Service
object ChestRefillService
{
    val islandChests = mutableListOf<MutableList<CraftChest>>()
    val middleChests = mutableListOf<CraftChest>()

    private fun random(): Random = ThreadLocalRandom.current()

    fun refillAllChests()
    {
        islandChests.forEach {
            refillAllChests(it, 1)
            println("thing")
        }

        refillAllChests(middleChests, 2)
        println("thing2")
    }

    private fun refillAllChests(
        _locations: List<CraftChest>, tier: Int
    )
    {
        val locations = _locations
            .filter {
                runCatching { it.inventory }.getOrNull() != null
            }

        println(locations.size)

        for (chest in locations)
        {
            val howManyItems = if (tier == 1) random().nextInt(3) + 5 else random().nextInt(3) + 4

            val usedSlots = mutableListOf<Int>()
            val usedItems = mutableListOf<Material>()

            for (i in 0 until howManyItems)
            {
                var slot: Int

                do
                {
                    slot = random().nextInt(27)
                } while (usedSlots.contains(slot))

                usedSlots.add(slot)

                var item = getTierItem(tier, usedItems)

                while (chest.inventory.contains(item.type))
                {
                    item = getTierItem(tier, usedItems)
                }

                chest.inventory.setItem(slot, item)
                usedItems.add(item.type)
            }

            chest.update()
        }
    }

    private fun getTierItem(tier: Int, usedItems: List<Material>): ItemStack
    {
        val rarity = random().nextInt(100)

        when (tier)
        {
            1 ->
            {
                if (rarity < 60)
                {
                    val i = random().nextInt(24)
                    when (i)
                    {
                        0, 1, 2, 3 -> return ItemStack(Material.WOOD, if (random().nextInt(5) < 3) 8 else 16, 0.toShort())
                        4 -> return ItemStack(Material.COBBLESTONE, if (random().nextInt(5) < 3) 8 else 16, 0.toShort())
                        5 -> return ItemStack(Material.SNOW_BALL, random().nextInt(9) + 8)
                        6 -> return ItemStack(Material.EGG, random().nextInt(9) + 8)
                        7 -> return ItemStack(Material.IRON_HELMET)
                        8 -> return ItemStack(Material.IRON_CHESTPLATE)
                        9 -> return ItemStack(Material.IRON_LEGGINGS)
                        10 -> return ItemStack(Material.IRON_BOOTS)
                        11 -> return ItemStack(Material.FISHING_ROD)
                        12 -> return ItemStack(Material.IRON_SWORD)
                        13 -> return ItemStack(Material.IRON_AXE)
                        14 -> return ItemStack(Material.IRON_SPADE)
                        15 -> return ItemStack(Material.BOW)
                        16 -> return ItemStack(Material.ARROW, random().nextInt(5) + 4)
                        17 -> return ItemStack(Material.STONE, random().nextInt(9) + 24)
                        18 -> return ItemStack(Material.COOKED_FISH, random().nextInt(2) + 2)
                        19, 20, 21 -> return Potion(PotionType.FIRE_RESISTANCE).toItemStack(1)
                        22 -> return ItemStack(Material.APPLE, random().nextInt(2) + 2)
                        23 -> return ItemStack(Material.BREAD, random().nextInt(2) + 2)
                    }
                } else if (rarity < 95)
                {
                    val i = random().nextInt(18)
                    when (i)
                    {
                        0 -> return ItemStack(Material.WATER_BUCKET)
                        1 -> return ItemStack(Material.ARROW, random().nextInt(9) + 4)
                        2 -> return ItemStack(Material.WEB)
                        3 -> return ItemStack(Material.DIAMOND_SWORD)
                        4 -> return ItemStack(Material.DIAMOND_AXE)
                        5 -> return ItemStack(Material.DIAMOND_PICKAXE)
                        6 -> return ItemStack(Material.DIAMOND_HELMET)
                        7 -> return ItemStack(Material.DIAMOND_CHESTPLATE)
                        8 -> return ItemStack(Material.DIAMOND_LEGGINGS)
                        9 -> return ItemStack(Material.EXP_BOTTLE, 2)
                        10 -> return ItemStack(Material.BAKED_POTATO, random().nextInt(2) + 2)
                        11 -> return ItemStack(Material.COOKED_CHICKEN, random().nextInt(2) + 2)
                        12 -> return ItemStack(Material.COOKED_BEEF, random().nextInt(2) + 2)
                        13 -> return ItemStack(Material.GOLDEN_CARROT, random().nextInt(2) + 2)
                        14 -> return ItemStack(Material.GRILLED_PORK, random().nextInt(2) + 2)
                        15 -> return ItemStack(Material.PUMPKIN_PIE, random().nextInt(2) + 2)
                        16 -> return ItemStack(Material.BOW)
                        17 -> return ItemStack(Material.FISHING_ROD)
                    }
                } else
                {
                    val i = random().nextInt(3)
                    when (i)
                    {
                        0 -> return ItemStack(Material.POTION, 1, 8261.toShort())
                        1 -> return ItemStack(Material.EXP_BOTTLE, 4)
                        2 -> return ItemStack(Material.FLINT_AND_STEEL)
                    }
                }
                if (rarity < 30)
                {
                    val i = random().nextInt(15)
                    when (i)
                    {
                        0 -> return ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                            .build()

                        1 -> return ItemBuilder(Material.DIAMOND_HELMET).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            1
                        ).build()

                        2 -> return ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            1
                        ).build()

                        3 -> return ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            1
                        ).build()

                        4 -> return ItemStack(Material.ARROW, random().nextInt(9) + 16)
                        5 -> return ItemStack(Material.ENDER_PEARL, random().nextInt(2))
                        6 -> return ItemStack(Material.EXP_BOTTLE, random().nextInt(4) + 8)
                        7 -> return ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 1).build()
                        8 -> return ItemStack(Material.FISHING_ROD)
                        9 -> return ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).build()
                        10 -> return ItemStack(Material.SNOW_BALL, random().nextInt(5) + 12)
                        11 -> return ItemStack(Material.EGG, random().nextInt(5) + 12)
                        12 -> return ItemStack(Material.GOLDEN_APPLE, 3)
                        13 -> return ItemStack(Material.WATER_BUCKET)
                        14 -> return ItemStack(Material.POTION, 1, 16385.toShort())
                    }
                } else if (rarity < 80)
                {
                    val i = random().nextInt(17)
                    when (i)
                    {
                        0 -> return ItemBuilder(Material.DIAMOND_BOOTS).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            random().nextInt(2) + 1
                        ).build()

                        1 -> return ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            random().nextInt(2) + 1
                        ).build()

                        2 -> return ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            random().nextInt(2) + 1
                        ).build()

                        3 -> return ItemBuilder(Material.DIAMOND_HELMET).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            random().nextInt(2) + 1
                        ).build()

                        4 -> return ItemStack(Material.GOLDEN_APPLE, 3)
                        5 -> return ItemStack(Material.EXP_BOTTLE, random().nextInt(2) + 16)
                        6 -> return ItemStack(Material.ENDER_PEARL, 2)
                        7 -> return ItemStack(Material.ENDER_PEARL, 1)
                        8 -> return ItemStack(Material.ARROW, random().nextInt(9) + 32)
                        9 -> return ItemStack(Material.POTION, 1, 16453.toShort())
                        10 -> return ItemStack(Material.POTION, 1, 16386.toShort())
                        11, 12 -> return ItemStack(Material.POTION, 1, 16385.toShort())
                        13 -> return ItemStack(Material.POTION, 1, 16451.toShort())
                        14 -> return ItemStack(Material.POTION, 1, 16388.toShort())
                        15 -> return ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).build()
                        16 -> return ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).build()
                    }
                } else
                {
                    val i = random().nextInt(10)
                    when (i)
                    {
                        0 -> return ItemStack(Material.POTION, 1, 16421.toShort())
                        1 -> return ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 3).build()
                        2 -> return ItemStack(Material.TNT, 2)
                        3 -> return ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).build()
                        4 -> return ItemStack(Material.ENDER_PEARL, random().nextInt(4))
                        5 -> return ItemStack(Material.LAVA_BUCKET)
                        6 -> return ItemBuilder(Material.DIAMOND_BOOTS).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            random().nextInt(3) + 1
                        ).build()

                        7 -> return ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            random().nextInt(3) + 1
                        ).build()

                        8 -> return ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            random().nextInt(3) + 1
                        ).build()

                        9 -> return ItemBuilder(Material.DIAMOND_HELMET).enchant(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            random().nextInt(3) + 1
                        ).build()
                    }
                }
            }

            2 -> if (rarity < 30)
            {
                val i = random().nextInt(15)
                when (i)
                {
                    0 -> return ItemBuilder(Material.DIAMOND_BOOTS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                        .build()

                    1 -> return ItemBuilder(Material.DIAMOND_HELMET).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                        .build()

                    2 -> return ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1)
                        .build()

                    3 -> return ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        1
                    ).build()

                    4 -> return ItemStack(Material.ARROW, random().nextInt(9) + 16)
                    5 -> return ItemStack(Material.ENDER_PEARL, random().nextInt(2))
                    6 -> return ItemStack(Material.EXP_BOTTLE, random().nextInt(4) + 8)
                    7 -> return ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 1).build()
                    8 -> return ItemStack(Material.FISHING_ROD)
                    9 -> return ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 1).build()
                    10 -> return ItemStack(Material.SNOW_BALL, random().nextInt(5) + 12)
                    11 -> return ItemStack(Material.EGG, random().nextInt(5) + 12)
                    12 -> return ItemStack(Material.GOLDEN_APPLE, 3)
                    13 -> return ItemStack(Material.WATER_BUCKET)
                    14 -> return ItemStack(Material.POTION, 1, 16385.toShort())
                }
            } else if (rarity < 80)
            {
                val i = random().nextInt(17)
                when (i)
                {
                    0 -> return ItemBuilder(Material.DIAMOND_BOOTS).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        random().nextInt(2) + 1
                    ).build()

                    1 -> return ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        random().nextInt(2) + 1
                    ).build()

                    2 -> return ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        random().nextInt(2) + 1
                    ).build()

                    3 -> return ItemBuilder(Material.DIAMOND_HELMET).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        random().nextInt(2) + 1
                    ).build()

                    4 -> return ItemStack(Material.GOLDEN_APPLE, 3)
                    5 -> return ItemStack(Material.EXP_BOTTLE, random().nextInt(2) + 16)
                    6 -> return ItemStack(Material.ENDER_PEARL, 2)
                    7 -> return ItemStack(Material.ENDER_PEARL, 1)
                    8 -> return ItemStack(Material.ARROW, random().nextInt(9) + 32)
                    9 -> return ItemStack(Material.POTION, 1, 16453.toShort())
                    10 -> return ItemStack(Material.POTION, 1, 16386.toShort())
                    11, 12 -> return ItemStack(Material.POTION, 1, 16385.toShort())
                    13 -> return ItemStack(Material.POTION, 1, 16451.toShort())
                    14 -> return ItemStack(Material.POTION, 1, 16388.toShort())
                    15 -> return ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 2).build()
                    16 -> return ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 2).build()
                }
            } else
            {
                val i = random().nextInt(10)
                when (i)
                {
                    0 -> return ItemStack(Material.POTION, 1, 16421.toShort())
                    1 -> return ItemBuilder(Material.BOW).enchant(Enchantment.ARROW_DAMAGE, 3).build()
                    2 -> return ItemStack(Material.TNT, 2)
                    3 -> return ItemBuilder(Material.DIAMOND_SWORD).enchant(Enchantment.DAMAGE_ALL, 3).build()
                    4 -> return ItemStack(Material.ENDER_PEARL, random().nextInt(4))
                    5 -> return ItemStack(Material.LAVA_BUCKET)
                    6 -> return ItemBuilder(Material.DIAMOND_BOOTS).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        random().nextInt(3) + 1
                    ).build()

                    7 -> return ItemBuilder(Material.DIAMOND_LEGGINGS).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        random().nextInt(3) + 1
                    ).build()

                    8 -> return ItemBuilder(Material.DIAMOND_CHESTPLATE).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        random().nextInt(3) + 1
                    ).build()

                    9 -> return ItemBuilder(Material.DIAMOND_HELMET).enchant(
                        Enchantment.PROTECTION_ENVIRONMENTAL,
                        random().nextInt(3) + 1
                    ).build()
                }
            }
        }

        return ItemStack(Material.BAKED_POTATO, 2)
    }
}
