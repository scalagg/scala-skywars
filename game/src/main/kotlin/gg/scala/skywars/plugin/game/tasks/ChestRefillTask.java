package gg.scala.skywars.plugin.game.tasks;

import gg.scala.skywars.plugin.game.SkyWarsGamePlugin;
import gg.scala.skywars.plugin.game.utilities.GameUtilities;
import net.evilblock.cubed.util.CC;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

/**
 * @author puugz
 * @since 19/06/2021 01:08
 */
public class ChestRefillTask extends BukkitRunnable {

	private final boolean again;

	public ChestRefillTask(boolean again) {
		this.again = again;

		this.runTaskTimer(
			SkyWarsGamePlugin.getPlugin(),
			0L, 20L
		);
	}

	@Override
	public void run() {
		final SkyWarsGamePlugin game = SkyWarsGamePlugin.getPlugin();
		final int chestRefillTime = game.getChestRefillTime();

		if (Arrays.asList(180, 120, 60, 30, 15, 10, 5, 4, 3, 2, 1).contains(chestRefillTime)) {
			Bukkit.broadcastMessage(CC.SEC + "The chests refill" + (this.again ? " again" : "") + " in " + CC.PRI + GameUtilities.formatSeconds(chestRefillTime) + CC.SEC + ".");
		}

		if (chestRefillTime == 0) {
			Bukkit.broadcastMessage(CC.GREEN + "The chests have refilled" + (this.again ? " again" : "") + "!");
			SkyWars.getInstance().getChestHandler().refillChests();

			Bukkit.getOnlinePlayers()
					.forEach(player -> player.playSound(player.getLocation(), Sound.CHEST_OPEN, 5f, 1f));

			if (!this.again) {
				game.setChestRefillTime(180);
				new ChestRefillTask(true);
			}

			this.cancel();
			return;
		}

		game.setChestRefillTime(chestRefillTime - 1);
	}
}
