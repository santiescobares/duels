package dev.santimg.duels.plugin.arena;

import org.bukkit.Location;

import com.google.common.base.Preconditions;

import dev.santimg.duels.core.arena.Arena;
import lombok.Setter;

@Setter
public final class PluginArena implements Arena {

	private final String name;
	private final Location[] locations = new Location[3];

	private boolean enabled = false;
	private boolean inUse = false;

	public PluginArena(String name) {
		this.name = Preconditions.checkNotNull(name, "Name can't be null.");
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Location[] getLocations() {
		return this.locations;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}

	@Override
	public boolean isInUse() {
		return this.inUse;
	}
}
