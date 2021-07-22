package dev.santimg.duels.core.arena.repository;

import java.util.Set;

import dev.santimg.duels.core.arena.Arena;

public interface ArenaRepository {

	/**
	 * Loads all {@link Arena}s
	 */
	void load();

	/**
	 * Saves all {@link Arena}s
	 */
	void save();

	/**
	 * Gets the cache where all {@link Arena}s will be cached in
	 * 
	 * @return the cache as a {@link Set} instance
	 */
	Set<Arena> getCache();
}
