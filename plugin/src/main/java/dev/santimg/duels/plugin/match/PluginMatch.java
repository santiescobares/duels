package dev.santimg.duels.plugin.match;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

import dev.santimg.duels.core.arena.Arena;
import dev.santimg.duels.core.match.Match;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.arena.PluginArena;
import dev.santimg.duels.plugin.utilities.ChatUtil;
import dev.santimg.duels.plugin.utilities.PlayerUtil;
import lombok.Setter;

@Setter
public final class PluginMatch implements Match {

	private final Arena arena;
	private final Player[] participants = new Player[2];

	private MatchState state = MatchState.NONE;

	private final List<Player> spectators = Lists.newArrayList();
	private final AtomicInteger playTimeSeconds = new AtomicInteger(0);

	public PluginMatch(Arena arena, Player firstPlayer, Player secondPlayer) {
		this.arena = Preconditions.checkNotNull(arena, "Arena can't be null.");
		this.participants[0] = Preconditions.checkNotNull(firstPlayer, "First player can't be null.");
		this.participants[1] = Preconditions.checkNotNull(secondPlayer, "Second player can't be null.");

		((PluginArena) arena).setInUse(true);
	}

	@Override
	public void startCountdown() {
		if (this.state == MatchState.STARTING) {
			return;
		}

		this.state = MatchState.STARTING;

		try {
			this.participants[0].teleport(this.arena.getLocations()[0]);
			this.participants[1].teleport(this.arena.getLocations()[1]);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Either team 1 spawn or team 2 spawn is not set on arena " + this.arena.getName() + ".");
		}

		for (Player participant : this.participants) {
			PlayerUtil.reset(participant, GameMode.SURVIVAL, true);

			Duels.getInstance().getKitManager().apply(participant);
		}

		new BukkitRunnable() {
			int i = 6;

			@Override
			public void run() {
				if (i-- > 0) {
					forEachViewer(player -> {
						player.sendMessage(ChatUtil.translate("&b&l" + i + "..."));
						player.playSound(player.getLocation(), Sound.NOTE_PLING, 6.0F, 1.0F);
					});
				} else if (i == 0) {
					this.cancel();

					start();
				}
			}

		}.runTaskTimerAsynchronously(Duels.getInstance(), 20L, 20L);
	}

	@Override
	public void start() {
		if (this.state == MatchState.PLAYING) {
			return;
		}

		this.state = MatchState.PLAYING;

		this.forEachViewer(player -> {
			player.sendMessage(ChatUtil.translate("&aDuel has started! Good luck."));
			player.playSound(player.getLocation(), Sound.NOTE_PLING, 6.0F, 2.4F);
		});

		new MatchPlayTimeRunnable().runTaskTimerAsynchronously(Duels.getInstance(), 20L, 20L);
	}

	@Override
	public void finish(Player winner) {
		if (this.state == MatchState.TERMINATED) {
			return;
		}

		this.state = MatchState.TERMINATED;

		Player loser = winner == this.participants[0] ? this.participants[1] : this.participants[0];

		this.forEachViewer(player -> {
			player.sendMessage("");
			player.sendMessage(ChatUtil.translate("&b&lMatch Ended"));
			player.sendMessage(ChatUtil.translate(" Winner: &a" + winner.getName()));
			player.sendMessage(ChatUtil.translate(" Loser: &c" + loser.getName()));
			player.sendMessage("");

			PlayerUtil.reset(player, GameMode.SURVIVAL, true);

			Duels.getInstance().getSpawnManager().teleportToSpawn(player);
		});
	}

	@Override
	public void cancel() {
		this.forEachViewer(player -> {
			player.sendMessage("");
			player.sendMessage(ChatUtil.translate("&c&lThe match was cancelled by an administrator."));
			player.sendMessage("");

			PlayerUtil.reset(player, GameMode.SURVIVAL, true);

			Duels.getInstance().getSpawnManager().teleportToSpawn(player);
		});
	}

	@Override
	public void addSpectator(Player player) {
		if (this.isSpectating(player)) {
			return;
		}

		this.spectators.add(player);

		PlayerUtil.reset(player, GameMode.CREATIVE, false);

		try {
			player.teleport(this.arena.getLocations()[2]);
		} catch (NullPointerException e) {
			throw new IllegalStateException("Spectators spawn is not set on arena " + this.arena.getName() + ".");
		}
	}

	@Override
	public void removeSpectator(Player player) {
		if (!this.isSpectating(player)) {
			return;
		}

		this.spectators.remove(player);

		PlayerUtil.reset(player, GameMode.SURVIVAL, true);

		Duels.getInstance().getSpawnManager().teleportToSpawn(player);
	}

	/**
	 * Performs a {@link Consumer} action on all {@link Player}s in the
	 * {@link Match}
	 * 
	 * @param action the {@link Consumer} action to perform
	 */
	public void forEachViewer(Consumer<Player> action) {
		this.getAllViewing().forEach(viewer -> action.accept(viewer));
	}

	/**
	 * Gets all {@link Player}s viewing the match (participants & spectators)
	 * 
	 * @return a {@link List} with the {@link Player}s
	 */
	public List<Player> getAllViewing() {
		List<Player> viewers = Lists.newArrayList(this.spectators);

		viewers.add(this.participants[0]);
		viewers.add(this.participants[1]);

		return viewers;
	}

	@Override
	public Arena getArena() {
		return this.arena;
	}

	@Override
	public Player[] getParticipants() {
		return this.participants;
	}

	@Override
	public MatchState getState() {
		return this.state;
	}

	@Override
	public List<Player> getSpectators() {
		return this.spectators;
	}

	@Override
	public boolean isSpectating(Player player) {
		return this.spectators.contains(player);
	}

	@Override
	public int getPlayTimeSeconds() {
		return this.playTimeSeconds.get();
	}

	final class MatchPlayTimeRunnable extends BukkitRunnable {

		@Override
		public void run() {
			if (state != MatchState.PLAYING) {
				this.cancel();
				return;
			}

			playTimeSeconds.incrementAndGet();
		}
	}
}
