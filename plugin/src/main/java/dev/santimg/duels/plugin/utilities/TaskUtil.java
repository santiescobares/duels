package dev.santimg.duels.plugin.utilities;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import dev.santimg.duels.plugin.Duels;
import lombok.experimental.UtilityClass;

@UtilityClass
public final class TaskUtil {

	private JavaPlugin plugin;

	static {
		plugin = Duels.getInstance();
	}

	/**
	 * Runs a synchronized {@link Bukkit} task
	 * 
	 * @param task the {@link Runnable}
	 */
	public void run(Runnable task) {
		Bukkit.getScheduler().runTask(plugin, task);
	}

	/**
	 * Runs a non-synchronized {@link Bukkit} task
	 * 
	 * @param task the {@link Runnable}
	 */
	public void runAsync(Runnable task) {
		Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
	}

	/**
	 * Runs a synchronized delayed {@link Bukkit} task
	 * 
	 * @param task  the {@link Runnable}
	 * @param delay the delay before the task
	 */
	public void runLater(Runnable task, long delay) {
		Bukkit.getScheduler().runTaskLater(plugin, task, delay);
	}

	/**
	 * Runs a non-synchronized delayed {@link Bukkit} task
	 * 
	 * @param task  the {@link Runnable}
	 * @param delay the delay before the task
	 */
	public void runLaterAsync(Runnable task, long delay) {
		Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, task, delay);
	}

	/**
	 * Runs a synchronized repeating {@link Bukkit} task
	 * 
	 * @param task   the {@link Runnable}
	 * @param delay  the delay before the task
	 * @param period the period of the task
	 */
	public void runTimer(Runnable task, long delay, long period) {
		Bukkit.getScheduler().runTaskTimer(plugin, task, delay, period);
	}

	/**
	 * Runs a non-synchronized repeating {@link Bukkit} task
	 * 
	 * @param task   the {@link Runnable}
	 * @param delay  the delay before the task
	 * @param period the period of the task
	 */
	public void runTimerAsync(Runnable task, long delay, long period) {
		Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, task, delay, period);
	}
}
