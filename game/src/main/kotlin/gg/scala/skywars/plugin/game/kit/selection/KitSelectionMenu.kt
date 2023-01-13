package gg.scala.skywars.plugin.game.kit.selection

import gg.scala.skywars.plugin.game.kit.KitService
import gg.scala.skywars.plugin.game.service.PlayerServices
import gg.scala.skywars.plugin.game.utilities.GameUtilities
import gg.scala.skywars.plugin.game.utilities.ItemNameUtilities
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.Menu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.ItemBuilder
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 * @author GrowlyX
 * @since 1/12/2023
 */
class KitSelectionMenu : Menu()
{
    override fun getTitle(player: Player) = "Select a Kit"

    override fun getButtons(player: Player): Map<Int, Button>
    {
        val buttons = mutableMapOf<Int, Button>()
        val model = PlayerServices.provide(player)
            ?: return buttons

        for (kit in KitService.kits)
        {
            val selected = model.selectedKit == kit.name
            val description = mutableListOf<String>()
            val items = kit.inventoryContents.toMutableList()

            for (stack in kit.armorContents)
            {
                if (stack != null && stack.type != Material.AIR)
                {
                    items.add(stack)
                }
            }

            if (items.isEmpty())
            {
                description.add("You get nothing.")
            } else
            {
                for (item in items)
                {
                    if (item == null)
                    {
                        continue
                    }

                    description += CC.GRAY + " ● " + ChatColor.WHITE + ItemNameUtilities.lookupWithAmount(item)

                    for (enchant in item.enchantments.entries)
                    {
                        description += "    ${CC.WHITE}(${
                            GameUtilities.getEnchantmentName(enchant.key)
                        } ${
                            GameUtilities.numberToNumeral(enchant.value)
                        })"
                    }
                }
            }

            description.add("")
            description.add(
                if (selected) "${CC.RED}This kit is selected!" else "${CC.GREEN}Click to select!"
            )

            buttons[buttons.size] = ItemBuilder
                .of(kit.icon)
                .name("${if (selected) CC.RED else CC.GREEN}${kit.name}")
                .toButton { _, _ ->
                    if (selected)
                    {
                        player.sendMessage("${CC.RED}You already have this kit selected!")
                        return@toButton
                    }

                    model.selectedKit = kit.name

                    player.sendMessage("${CC.GREEN}You selected the ${CC.GOLD}${kit.name}${CC.GREEN} kit!")
                    player.closeInventory()
                }
        }

        return buttons
    }
}
