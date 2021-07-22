package dev.santimg.duels.plugin.arena;

import dev.santimg.duels.core.arena.ArenaManager;
import dev.santimg.duels.core.arena.repository.ArenaRepository;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.arena.command.ArenaCommand;
import dev.santimg.duels.plugin.arena.repository.YAMLArenaRepository;

public final class PluginArenaManager implements ArenaManager {

	private final ArenaRepository repository;

	public PluginArenaManager() {
		this.repository = new YAMLArenaRepository();
		this.repository.load();

		Duels.getInstance().getCommand("arena").setExecutor(new ArenaCommand());
	}

	@Override
	public ArenaRepository getRepository() {
		return this.repository;
	}
}
