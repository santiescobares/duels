package dev.santimg.duels.plugin.spawn;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import dev.santimg.duels.core.spawn.SpawnManager;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.spawn.command.*;
import dev.santimg.duels.plugin.spawn.listener.SpawnListener;
import dev.santimg.duels.plugin.utilities.BukkitUtil;
import dev.santimg.duels.plugin.utilities.TaskUtil;

public final class PluginSpawnManager implements SpawnManager {

	private Location spawnLocation;

	public PluginSpawnManager() {
		FileConfiguration configFile = Duels.getInstance().getConfig();

		if (configFile.contains("SPAWN")) {
			this.spawnLocation = BukkitUtil.deserializeLocation(configFile.getString("SPAWN"));
		}

		Bukkit.getPluginManager().registerEvents(new SpawnListener(), Duels.getInstance());

		Duels.getInstance().getCommand("setSpawn").setExecutor(new SetSpawnCommand());
		Duels.getInstance().getCommand("spawn").setExecutor(new SpawnCommand());
	}

	@Override
	public void setSpawnLocation(Location spawnLocation) {
		this.spawnLocation = spawnLocation;

		TaskUtil.runAsync(() -> {
			Duels.getInstance().getConfig().set("SPAWN", BukkitUtil.serializeLocation(spawnLocation));
			Duels.getInstance().saveConfig();
		});
	}

	@Override
	public Location getSpawnLocation() {
		return this.spawnLocation;
	}
}
