package dev.santimg.duels.core.spawn;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface SpawnManager {

	/**
	 * Teleports a {@link Player} to the spawn if it's set
	 * 
	 * @param player the {@link Player} to teleport
	 * @return true if the {@link Player} could be teleported
	 */
	default boolean teleportToSpawn(Player player) {
		Location spawn = this.getSpawnLocation();

		if (spawn == null) {
			return false;
		}

		player.teleport(spawn);
		player.getInventory().setHeldItemSlot(0);

		return true;
	}

	/**
	 * Sets a new spawn {@link Location}
	 * 
	 * @param spawnLocation the new spawn {@link Location}
	 */
	void setSpawnLocation(Location spawnLocation);

	/**
	 * Gets the spawn {@link Location}
	 * 
	 * @return the spawn {@link Location} or null if it isn't set
	 */
	Location getSpawnLocation();
}
