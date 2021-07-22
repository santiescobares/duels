package dev.santimg.duels.plugin.match;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import dev.santimg.duels.core.arena.Arena;
import dev.santimg.duels.core.match.Match;
import dev.santimg.duels.core.match.MatchManager;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.match.listener.*;
import dev.santimg.duels.plugin.utilities.ChatUtil;

public final class PluginMatchManager implements MatchManager {

	private final Set<Match> activeMatches;
	private final Map<UUID, Match> playersMatchesMap;

	public PluginMatchManager() {
		this.activeMatches = Sets.newHashSet();
		this.playersMatchesMap = Maps.newHashMap();

		Bukkit.getPluginManager().registerEvents(new MatchListener(), Duels.getInstance());
		Bukkit.getPluginManager().registerEvents(new MatchStartingListener(), Duels.getInstance());
		Bukkit.getPluginManager().registerEvents(new MatchPreventionListener(), Duels.getInstance());
		Bukkit.getPluginManager().registerEvents(new MatchSpectatingListener(), Duels.getInstance());
	}

	@Override
	public void startMatch(Player player1, Player player2) {
		Optional<Arena> arena = Duels.getInstance().getArenaManager().findAvailableArena();

		if (!arena.isPresent()) {
			player1.sendMessage(ChatUtil.translate("&cNo arena was found in order to start the match!"));
			player2.sendMessage(ChatUtil.translate("&cNo arena was found in order to start the match!"));

			return;
		}

		Match match = new PluginMatch(arena.get(), player1, player2);

		this.activeMatches.add(match);
		this.playersMatchesMap.put(player1.getUniqueId(), match);
		this.playersMatchesMap.put(player2.getUniqueId(), match);

		match.startCountdown();
	}

	@Override
	public Set<Match> getActiveMatches() {
		return this.activeMatches;
	}

	@Override
	public Map<UUID, Match> getPlayerMatchesMap() {
		return this.playersMatchesMap;
	}
}
