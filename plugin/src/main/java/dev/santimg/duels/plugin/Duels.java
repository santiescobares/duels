package dev.santimg.duels.plugin;

import org.bukkit.plugin.java.JavaPlugin;

import dev.santimg.duels.core.arena.ArenaManager;
import dev.santimg.duels.core.kit.KitManager;
import dev.santimg.duels.core.match.MatchManager;
import dev.santimg.duels.core.spawn.SpawnManager;
import dev.santimg.duels.plugin.arena.PluginArenaManager;
import dev.santimg.duels.plugin.kit.PluginKitManager;
import dev.santimg.duels.plugin.match.PluginMatchManager;
import dev.santimg.duels.plugin.spawn.PluginSpawnManager;
import dev.santimg.duels.plugin.utilities.FileConfig;
import lombok.Getter;

@Getter
public final class Duels extends JavaPlugin {

	@Getter
	private static Duels instance;

	private FileConfig arenasFile;
	private FileConfig kitFile;

	private ArenaManager arenaManager;
	private SpawnManager spawnManager;
	private KitManager kitManager;
	private MatchManager matchManager;

	@Override
	public void onEnable() {
		instance = this;

		this.saveDefaultConfig();
		this.arenasFile = new FileConfig(this, "arenas.yml");
		this.kitFile = new FileConfig(this, "kit.yml");

		this.registerManagers();
	}

	@Override
	public void onDisable() {
		this.arenaManager.getRepository().save();
	}

	private void registerManagers() {
		this.arenaManager = new PluginArenaManager();
		this.spawnManager = new PluginSpawnManager();
		this.kitManager = new PluginKitManager();
		this.matchManager = new PluginMatchManager();
	}
}
