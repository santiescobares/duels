package dev.santimg.duels.core.arena;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import dev.santimg.duels.core.arena.repository.ArenaRepository;

public interface ArenaManager {

	/**
	 * Gets the implemented {@link ArenaRepository}
	 * 
	 * @return the implemented {@link ArenaRepository}
	 */
	ArenaRepository getRepository();

	/**
	 * Gets an {@link Arena}
	 * 
	 * @param name the {@link Arena}'s name
	 * @return an {@link Optional} with the {@link Arena} if it exists
	 */
	default Optional<Arena> getByName(String name) {
		return this.getRepository().getCache().stream().filter(arena -> arena.getName().equals(name)).findFirst();
	}

	/**
	 * Gets all enabled {@link Arena}s
	 * 
	 * @return a {@link Set} with all enabled {@link Arena}s instances
	 */
	default Set<Arena> getAllEnabled() {
		return this.getRepository().getCache().stream().filter(Arena::isEnabled).collect(Collectors.toSet());
	}

	/**
	 * Finds an available {@link Arena} to use
	 * 
	 * @return an {@link Optional} with the {@link Arena} if is any was found
	 */
	default Optional<Arena> findAvailableArena() {
		return this.getAllEnabled().stream().findAny();
	}
}
