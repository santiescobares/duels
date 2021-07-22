package dev.santimg.duels.core.arena;

import org.bukkit.Location;

public interface Arena {

	/**
	 * Gets the name of the {@link Arena}
	 * 
	 * @return the {@link Arena}'s name
	 */
	String getName();

	/**
	 * Gets the {@link Location}s of the {@link Arena}
	 * 
	 * @return a {@link Location}s array (0 = first player spawn, 1 = second player
	 *         spawn, 2 = spectators spawn)
	 */
	Location[] getLocations();

	/**
	 * Checks if the {@link Arena} is enabled
	 * 
	 * @return true if it's enabled
	 */
	boolean isEnabled();

	/**
	 * Checks if the {@link Arena} is in use
	 * 
	 * @return true if it's in use
	 */
	boolean isInUse();
}
