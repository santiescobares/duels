package dev.santimg.duels.plugin.utilities;

import lombok.Getter;
import org.bukkit.configuration.file.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;

@Getter
public final class FileConfig {

	private File file;
	private FileConfiguration configuration;

	public FileConfig(JavaPlugin plugin, String fileName) {
		this.file = new File(plugin.getDataFolder(), fileName);

		if (!this.file.exists()) {
			this.file.getParentFile().mkdirs();

			if (plugin.getResource(fileName) == null) {
				try {
					this.file.createNewFile();
				} catch (IOException e) {
					plugin.getLogger().severe("Failed to create new file " + fileName);
				}
			} else {
				plugin.saveResource(fileName, false);
			}
		}

		this.configuration = YamlConfiguration.loadConfiguration(this.file);
	}

	/**
	 * Saves the {@link File}
	 */
	public void save() {
		try {
			this.configuration.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Saves the {@link File}
	 */
	public void reload() {
		this.configuration = YamlConfiguration.loadConfiguration(file);
	}
}