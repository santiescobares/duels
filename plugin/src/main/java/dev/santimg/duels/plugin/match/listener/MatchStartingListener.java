package dev.santimg.duels.plugin.match.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import dev.santimg.duels.core.match.Match;
import dev.santimg.duels.core.match.Match.MatchState;
import dev.santimg.duels.plugin.Duels;

public final class MatchStartingListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamage(EntityDamageEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getEntity();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (match.isSpectating(player)) {
			return;
		}

		if (match.getState() != MatchState.STARTING) {
			return;
		}

		event.setCancelled(true);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!(event.getDamager() instanceof Player)) {
			return;
		}

		Player player = (Player) event.getDamager();
		Match match = Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (match.isSpectating(player)) {
			return;
		}

		if (match.getState() != MatchState.STARTING) {
			return;
		}

		event.setCancelled(true);
	}
}
