package dev.santimg.duels.core.match;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.entity.Player;

public interface MatchManager {

	/**
	 * Starts a {@link Match} between two {@link Player}s
	 * 
	 * @param player1 the first {@link Player}
	 * @param player2 the second {@link Player}
	 */
	void startMatch(Player player1, Player player2);

	/**
	 * Gets the cache where all active {@link Match}es will be cached in
	 * 
	 * @return the cache as a {@link Set} instance
	 */
	Set<Match> getActiveMatches();

	/**
	 * Gets the cache where all player {@link Match}es will be cached in
	 * 
	 * @return the cache as a {@link Map} instance
	 */
	Map<UUID, Match> getPlayerMatchesMap();

	/**
	 * Gets the playing {@link Match} of a player
	 * 
	 * @param uuid the player's {@link UUID}
	 * @return an {@link Optional} with the {@link Match} if the player is playing
	 *         one
	 */
	default Optional<Match> getPlayerMatch(UUID uuid) {
		return Optional.ofNullable(this.getPlayerMatchesMap().get(uuid));
	}

	default Optional<Match> getPlayerMatch(Player player) {
		return this.getPlayerMatch(player.getUniqueId());
	}
}
