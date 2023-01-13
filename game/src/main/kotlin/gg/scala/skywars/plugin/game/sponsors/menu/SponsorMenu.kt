package gg.scala.skywars.plugin.game.sponsors.menu

import gg.scala.cgs.common.CgsGameEngine
import gg.scala.grape.GrapeSpigotPlugin
import gg.scala.skywars.plugin.game.sponsors.Sponsor
import gg.scala.skywars.plugin.game.sponsors.SponsorEffect
import gg.scala.skywars.plugin.game.sponsors.SponsorItem
import gg.scala.skywars.plugin.game.sponsors.SurvivalGamesSponsorService
import gg.scala.survivalgames.game.sponsors.*
import net.evilblock.cubed.menu.Button
import net.evilblock.cubed.menu.pagination.PaginatedMenu
import net.evilblock.cubed.util.CC
import net.evilblock.cubed.util.bukkit.InventoryUtils
import net.evilblock.cubed.util.bukkit.ItemBuilder
import net.evilblock.cubed.util.bukkit.ItemUtils
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

/**
 * @author AgentRKID
 * @since 8/10/2022
 */
class SponsorMenu(var sponsoring: Player) : PaginatedMenu()
{
    override fun getPrePaginatedTitle(player: Player): String
    {
        return "Sponsor - ${player.name}"
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button>
    {
        return mutableMapOf<Int, Button>().also { buttons ->
            val config = CgsGameEngine.INSTANCE.plugin.config<SurvivalGamesSponsorConfig>()

            for (item in config.sponsorItems)
            {
                buttons[buttons.size] = SponsorItemButton(item)
            }

            for (effect in config.sponsorEffects)
            {
                buttons[buttons.size] = SponsorEffectButton(effect)
            }
        }
    }

    private inner class SponsorItemButton(var sponsorItem: SponsorItem) : Button()
    {
        override fun getButtonItem(player: Player): ItemStack {
            return ItemBuilder.copyOf(sponsorItem.stack)
                .addToLore("", "${CC.YELLOW}Click to sponsor this item to ${CC.GREEN}${player.name}${CC.YELLOW}.")
                .build()
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView)
        {
            if (InventoryUtils.getMaxItemsSize(sponsoring.inventory, sponsorItem.stack) == 0)
            {
                player.sendMessage("${CC.RED}You cannot sponsor them that item since their inventory cannot fit it.")
                return
            }

            val grapePlayer = GrapeSpigotPlugin.getInstance().playerHandler.getByPlayer(player)
            grapePlayer.coins -= sponsorItem.cost

            SurvivalGamesSponsorService.sponsorsLock[player.uniqueId] = sponsoring.uniqueId
            SurvivalGamesSponsorService.sponsors.computeIfAbsent(sponsoring.uniqueId) { ArrayList() }.add(
                Sponsor(player.uniqueId, sponsorItem.cost)
            )

            sponsoring.inventory.addItem(sponsorItem.stack.clone())
            sponsoring.sendMessage("${CC.B_GREEN}${player.name} ${CC.GREEN}has sponsored you ${ItemUtils.getName(sponsorItem.stack)}${if (sponsorItem.stack.amount == 1) "" else "s"}!")
        }
    }

    private inner class SponsorEffectButton(var sponsorEffect: SponsorEffect) : Button()
    {
        override fun getButtonItem(player: Player): ItemStack {
            return ItemBuilder.of(sponsorEffect.material)
                .name("${CC.GREEN}${sponsorEffect.effect.name}")
                .addToLore("", "${CC.YELLOW}Click to sponsor this item to ${CC.GREEN}${player.name}${CC.YELLOW}.")
                .build()
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView)
        {
            if (sponsoring.hasPotionEffect(sponsorEffect.effect.type))
            {
                player.sendMessage("${CC.RED}You cannot give them this effect as they're have it active.")
                return
            }

            val grapePlayer = GrapeSpigotPlugin.getInstance().playerHandler.getByPlayer(player)
            grapePlayer.coins -= sponsorEffect.cost

            SurvivalGamesSponsorService.sponsorsLock[player.uniqueId] = sponsoring.uniqueId
            SurvivalGamesSponsorService.sponsors.computeIfAbsent(sponsoring.uniqueId) { ArrayList() }.add(
                Sponsor(player.uniqueId, sponsorEffect.cost)
            )

            sponsoring.sendMessage("${CC.B_GREEN}${player.name} ${CC.GREEN}has sponsored you ${sponsorEffect.effect.name} ${sponsorEffect.amp + 1} for ${sponsorEffect.duration} seconds!")
        }
    }

}
