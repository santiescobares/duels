package dev.santimg.duels.plugin.utilities;

import java.util.*;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.google.common.collect.Lists;

import lombok.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class PlayerUtil {

	/**
	 * Gets the ping of a {@link Player}
	 * 
	 * @param player the {@link Player}
	 * @return the ping or -1 if the player is null
	 */
	public int getPing(Player player) {
		if (player == null) {
			return -1;
		}

		try {
			String version = Bukkit.getServer().getClass().getPackage().getName().substring(23);
			Class<?> craftPlayer = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftPlayer");
			Object handle = craftPlayer.getMethod("getHandle", new Class[0]).invoke(player);

			return (Integer) handle.getClass().getDeclaredField("ping").get(handle);
		} catch (Exception e) {
			return -1;
		}
	}

	/**
	 * Colors an amount of a ping
	 * 
	 * @param ping the amount
	 * @return the colored {@link String}
	 */
	public String colorPing(int ping) {
		return ping < 100 ? ChatColor.GREEN.toString() : ping >= 100 && ping < 200 ? ChatColor.YELLOW.toString() : ChatColor.RED.toString();
	}

	/**
	 * Colors the ping of a {@link Player}
	 * 
	 * @param player the {@link Player}
	 * @return the colored {@link String}
	 */
	public String colorPing(Player player) {
		return colorPing(getPing(player));
	}

	/**
	 * Resets a {@link Player}'s inventory and armor
	 * 
	 * @param player the {@link Player} to reset
	 */
	public void resetInventory(Player player) {
		if (player == null) {
			return;
		}

		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.updateInventory();
	}

	/**
	 * Resets a {@link Player}'s inventory
	 * 
	 * @param player the {@link Player} to reset
	 */
	public void resetInventoryOnly(Player player) {
		if (player == null) {
			return;
		}

		player.getInventory().clear();
		player.updateInventory();
	}

	/**
	 * Resets a {@link Player}'s armor
	 * 
	 * @param player the {@link Player} to reset
	 */
	public void resetArmorOnly(Player player) {
		if (player == null) {
			return;
		}

		player.getInventory().setArmorContents(null);
		player.updateInventory();
	}

	/**
	 * Fully resets a {@link Player} using default values
	 * 
	 * @param player the {@link Player} to reset
	 */
	public void reset(Player player) {
		if (player == null) {
			return;
		}

		resetInventory(player);

		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0.0F);
		player.setSaturation(10.0F);
		player.setFireTicks(0);
	}

	/**
	 * Fully resets a {@link Player} using some given values
	 * 
	 * @param player     the {@link Player} to reset
	 * @param gameMode   the new {@link GameMode} for the player
	 * @param disableFly should or not disable the player's fly mode
	 */
	public void reset(Player player, GameMode gameMode, boolean disableFly) {
		if (player == null) {
			return;
		}

		resetInventory(player);

		player.getActivePotionEffects().forEach(effect -> player.removePotionEffect(effect.getType()));
		player.setGameMode(gameMode);
		player.setHealth(player.getMaxHealth());
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0.0F);
		player.setSaturation(10.0F);
		player.setFireTicks(0);

		if (disableFly) {
			player.setAllowFlight(false);
			player.setFlying(false);
		}
	}

	/**
	 * Gets the amount of empty slots in a {@link Player}'s inventory
	 * 
	 * @param player the {@link Player}
	 * @return the amount of empty slots
	 */
	public int countEmptySlots(Player player) {
		if (player == null) {
			return -1;
		}

		int emptySlots = 0;

		for (ItemStack item : player.getInventory().getContents()) {
			if (item == null || item.getType() == Material.AIR) {
				emptySlots++;
			}
		}

		return emptySlots;
	}

	/**
	 * Checks if a {@link Player} has a specific amount of empty slots
	 * 
	 * @param player the {@link Player}
	 * @param slots  the required amount of empty slots
	 * @return true if it has the required amount
	 */
	public boolean hasEmptySlots(Player player, int slots) {
		return countEmptySlots(player) >= slots;
	}

	/**
	 * Gets the distance between two {@link Location}s
	 * 
	 * @param loc1 the first {@link Location}
	 * @param loc2 the second {@link Location}
	 * @return the blocks distance between the locations
	 */
	public int getDistance(Location loc1, Location loc2) {
		if (!loc1.getWorld().getName().equals(loc2.getWorld().getName())) {
			return -1;
		}

		int distance = 0;

		int playerX = loc1.getBlockX();
		int targetX = loc2.getBlockX();

		distance += Math.max(playerX, targetX) - Math.min(playerX, targetX);

		int playerZ = loc1.getBlockZ();
		int targetZ = loc2.getBlockZ();

		distance += Math.max(playerZ, targetZ) - Math.min(playerZ, targetZ);

		return distance;
	}

	/**
	 * Gets a {@link List} of all offline players
	 * 
	 * @return a {@link List} instance
	 */
	public List<OfflinePlayer> getOfflinePlayers() {
		return Arrays.asList(Bukkit.getOfflinePlayers());
	}

	/**
	 * Gets a {@link List} of all online players
	 * 
	 * @return a {@link List} instance
	 */
	public List<Player> getOnlinePlayers() {
		return Lists.newArrayList(Bukkit.getOnlinePlayers());
	}

	/**
	 * Gets an {@link OfflinePlayer} by its name (insensitive case)
	 * 
	 * @param playerName the player's name
	 * @return a {@link OfflinePlayer} instance
	 */
	@SuppressWarnings("deprecation")
	public OfflinePlayer getOfflinePlayer(String playerName) {
		for (OfflinePlayer player : getOfflinePlayers()) {
			if (player.getName().equalsIgnoreCase(playerName)) {
				return player;
			}
		}

		return Bukkit.getOfflinePlayer(playerName);
	}

	/**
	 * Gets an {@link OfflinePlayer} by its {@link UUID}
	 * 
	 * @param uuid the player's {@link UUID}
	 * @return a {@link OfflinePlayer} instance
	 */
	public OfflinePlayer getOfflinePlayer(UUID uuid) {
		if (uuid == null) {
			return null;
		}

		return Bukkit.getOfflinePlayer(uuid);
	}

	/**
	 * Gets a player's {@link UUID} by its name
	 * 
	 * @param playerName the player's name
	 * @return the player's {@link UUID}
	 */
	public UUID uuid(String playerName) {
		if (Bukkit.getPlayer(playerName) != null) {
			return Bukkit.getPlayer(playerName).getUniqueId();
		}

		return getOfflinePlayer(playerName).getUniqueId();
	}

	/**
	 * Gets a player's name by its {@link UUID}
	 * 
	 * @param uuid the player's {@link UUID}
	 * @return the player's name or "Unknown" if it never played before
	 */
	public String name(UUID uuid) {
		if (uuid == null) {
			return "Unknown";
		}

		if (Bukkit.getPlayer(uuid) != null) {
			return Bukkit.getPlayer(uuid).getName();
		}

		String name = getOfflinePlayer(uuid).getName();
		return name == null ? "Unknown" : name;
	}

	/**
	 * Teleports a {@link Player} to a {@link Location}
	 * 
	 * @param player   the {@link Player} to teleport
	 * @param location the {@link Location}
	 * @return true if it could be teleported
	 */
	public boolean teleport(Player player, Location location) {
		if (player == null || location == null) {
			return false;
		}

		return player.teleport(location);
	}

	/**
	 * Gets the cardinal direction of a yaw amount
	 * 
	 * @param yaw the yaw amount
	 * @return a {@link Direction} instance
	 */
	public Direction getDirection(float yaw) {
		double rotation = (yaw - 90.0F) % 360.0F;

		if (rotation < 0) {
			rotation += 360.0F;
		}

		if (0 <= rotation && rotation < 22.5) {
			return Direction.NORTH;
		}

		if (22.5 <= rotation && rotation < 67.5) {
			return Direction.NORTH_EAST;
		}

		if (67.5 <= rotation && rotation < 112.5) {
			return Direction.EAST;
		}

		if (112.5 <= rotation && rotation < 157.5) {
			return Direction.SOUTH_EAST;
		}

		if (157.5 <= rotation && rotation < 202.5) {
			return Direction.SOUTH;
		}

		if (202.5 <= rotation && rotation < 247.5) {
			return Direction.SOUTH_WEST;
		}

		if (247.5 <= rotation && rotation < 292.5) {
			return Direction.WEST;
		}

		if (292.5 <= rotation && rotation < 337.5) {
			return Direction.NORTH_WEST;
		}

		if (337.5 <= rotation && rotation <= 360) {
			return Direction.NORTH;
		}

		return Direction.SOUTH;
	}

	/**
	 * Gets the cardinal direction of a {@link Player}
	 * 
	 * @param player the {@link Player}
	 * @return a {@link Direction} instance
	 */
	public Direction getDirection(Player player) {
		if (player == null) {
			return Direction.SOUTH;
		}

		return getDirection(player.getLocation().getYaw());
	}

	@Getter
	@AllArgsConstructor
	public static enum Direction {
		SOUTH("South", "S"), NORTH("North", "N"), WEST("West", "W"), EAST("East", "E"), SOUTH_WEST("South West", "SW"), SOUTH_EAST("South East", "SE"),
		NORTH_WEST("North West", "NW"), NORTH_EAST("North East", "NE");

		private final String name;
		private final String shortName;

		/**
		 * Gets the opposite direction of this one
		 * 
		 * @return the opposite {@link Direction} instance
		 */
		public Direction opposite() {
			switch (this) {
			case SOUTH:
				return NORTH;
			case NORTH:
				return SOUTH;
			case WEST:
				return EAST;
			case EAST:
				return WEST;
			case SOUTH_WEST:
				return NORTH_WEST;
			case SOUTH_EAST:
				return NORTH_EAST;
			case NORTH_WEST:
				return SOUTH_WEST;
			case NORTH_EAST:
				return SOUTH_EAST;
			default:
				return this;
			}
		}
	}
}
