package dev.santimg.duels.plugin.match.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import dev.santimg.duels.core.match.Match;
import dev.santimg.duels.plugin.Duels;

public final class MatchSpectatingListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (!match.getSpectators().contains(player)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onBlockPlace(BlockPlaceEvent event) {
		Player player = event.getPlayer();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (!match.getSpectators().contains(player)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = event.getPlayer();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (!match.getSpectators().contains(player)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (!match.getSpectators().contains(player)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onInventoryClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (!match.getSpectators().contains(player)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntitDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (!match.getSpectators().contains(player)) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntitDamageByEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getDamager();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (!match.getSpectators().contains(player)) {
			return;
		}

		event.setCancelled(true);
	}
}
