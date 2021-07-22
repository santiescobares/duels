package dev.santimg.duels.plugin.match.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import dev.santimg.duels.core.match.Match;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.utilities.ChatUtil;

public final class MatchPreventionListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (match.isSpectating(player)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (match.isSpectating(player)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (match.isSpectating(player)) {
			return;
		}

		if (event.getItemDrop().getItemStack().getType().name().contains("SWORD")) {
			event.setCancelled(true);

			player.sendMessage(ChatUtil.translate("&cYou can't drop your sword!"));
		} else {
			event.getItemDrop().remove();
		}
	}
}
