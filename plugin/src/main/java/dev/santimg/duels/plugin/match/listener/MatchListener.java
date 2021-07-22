package dev.santimg.duels.plugin.match.listener;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import dev.santimg.duels.core.match.MatchManager;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.match.PluginMatch;
import dev.santimg.duels.plugin.utilities.ChatUtil;
import dev.santimg.duels.plugin.utilities.PlayerUtil;

public final class MatchListener implements Listener {

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeath(PlayerDeathEvent event) {
		MatchManager matchManager = Duels.getInstance().getMatchManager();

		Player player = event.getEntity();
		PluginMatch match = (PluginMatch) matchManager.getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (match.isSpectating(player)) {
			return;
		}

		Player killer = player.getKiller();

		if (killer == null) {
			return;
		}

		if (matchManager.getPlayerMatch(killer) != match || match.getSpectators().contains(killer)) {
			return;
		}

		event.setDeathMessage(null);
		event.setDroppedExp(0);
		event.getDrops().clear();

		match.forEachViewer(viewer -> viewer.sendMessage(ChatUtil.translate("&c" + player.getName() + " &7was slain by &a" + killer.getName() + "&7.")));
		match.finish(killer);
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		PluginMatch match = (PluginMatch) Duels.getInstance().getMatchManager().getPlayerMatch(player);

		if (match == null) {
			return;
		}

		if (match.isSpectating(player)) {
			return;
		}

		PlayerUtil.reset(player, GameMode.SURVIVAL, true);

		Player winner = player == match.getParticipants()[0] ? match.getParticipants()[1] : match.getParticipants()[0];

		match.forEachViewer(viewer -> viewer.sendMessage(ChatUtil.translate("&c" + player.getName() + " &7logged out.")));
		match.finish(winner);
	}
}
