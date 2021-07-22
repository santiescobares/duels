package dev.santimg.duels.plugin.arena.repository;

import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.google.common.collect.Sets;

import dev.santimg.duels.core.arena.Arena;
import dev.santimg.duels.core.arena.repository.ArenaRepository;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.arena.PluginArena;
import dev.santimg.duels.plugin.utilities.BukkitUtil;
import dev.santimg.duels.plugin.utilities.FileConfig;

public final class YAMLArenaRepository implements ArenaRepository {

	private final Set<Arena> cache = Sets.newHashSet();

	@Override
	public void load() {
		this.cache.clear();

		FileConfiguration arenasFile = Duels.getInstance().getArenasFile().getConfiguration();

		if (arenasFile.contains("ARENAS")) {
			arenasFile.getConfigurationSection("ARENAS").getKeys(false).forEach(key -> {
				ConfigurationSection section = arenasFile.getConfigurationSection("ARENAS." + key);
				PluginArena arena = new PluginArena(key);

				if (section.contains("LOCATIONS.1")) {
					arena.getLocations()[0] = BukkitUtil.deserializeLocation(section.getString("LOCATIONS.1"));
				}

				if (section.contains("LOCATIONS.2")) {
					arena.getLocations()[1] = BukkitUtil.deserializeLocation(section.getString("LOCATIONS.2"));
				}

				if (section.contains("LOCATIONS.3")) {
					arena.getLocations()[2] = BukkitUtil.deserializeLocation(section.getString("LOCATIONS.3"));
				}

				arena.setEnabled(section.getBoolean("ENABLED"));

				this.cache.add(arena);
			});
		}
	}

	@Override
	public void save() {
		FileConfig arenasFileConfig = Duels.getInstance().getArenasFile();
		FileConfiguration arenasFile = arenasFileConfig.getConfiguration();

		arenasFile.set("ARENAS", null);

		this.cache.forEach(arena -> {
			ConfigurationSection section = arenasFile.createSection("ARENAS." + arena.getName());

			if (arena.getLocations()[0] != null) {
				section.set("LOCATIONS.1", BukkitUtil.serializeLocation(arena.getLocations()[0]));
			}

			if (arena.getLocations()[1] != null) {
				section.set("LOCATIONS.2", BukkitUtil.serializeLocation(arena.getLocations()[1]));
			}

			if (arena.getLocations()[2] != null) {
				section.set("LOCATIONS.3", BukkitUtil.serializeLocation(arena.getLocations()[2]));
			}

			section.set("ENABLED", arena.isEnabled());
		});

		arenasFileConfig.save();
	}

	@Override
	public Set<Arena> getCache() {
		return this.cache;
	}
}
