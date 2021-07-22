package dev.santimg.duels.plugin.spawn.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.utilities.TaskUtil;

public final class SpawnListener implements Listener {

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerJoin(PlayerJoinEvent event) {
		TaskUtil.runLater(() -> Duels.getInstance().getSpawnManager().teleportToSpawn(event.getPlayer()), 1L);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		Player player = event.getPlayer();

		// This is handled by match listeners
		if (Duels.getInstance().getMatchManager().getPlayerMatch(player) != null) {
			return;
		}

		TaskUtil.runLater(() -> Duels.getInstance().getSpawnManager().teleportToSpawn(player), 1L);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerMove(PlayerMoveEvent event) {
		if (event.getTo().getBlockY() <= 0) {
			Duels.getInstance().getSpawnManager().teleportToSpawn(event.getPlayer());
		}
	}
}
