package dev.santimg.duels.plugin.kit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import dev.santimg.duels.core.kit.KitManager;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.utilities.BukkitUtil;
import dev.santimg.duels.plugin.utilities.FileConfig;
import dev.santimg.duels.plugin.utilities.TaskUtil;

public final class PluginKitManager implements KitManager {

	private ItemStack[] defaultContents = new ItemStack[0];
	private ItemStack[] defaultArmorContents = new ItemStack[0];

	public PluginKitManager() {
		FileConfiguration kitFile = Duels.getInstance().getKitFile().getConfiguration();

		if (kitFile.contains("KIT.CONTENTS")) {
			this.defaultContents = BukkitUtil.deserializeItemStackArray(kitFile.getString("KIT.CONTENTS"));
		}

		if (kitFile.contains("KIT.ARMOR_CONTENTS")) {
			this.defaultArmorContents = BukkitUtil.deserializeItemStackArray(kitFile.getString("KIT.ARMOR_CONTENTS"));
		}
	}

	@Override
	public void setDefaultKitContents(ItemStack[] defaultContents, ItemStack[] defaultArmorContents) {
		this.defaultContents = defaultArmorContents;
		this.defaultArmorContents = defaultArmorContents;

		TaskUtil.runAsync(() -> {
			FileConfig kitFileConfig = Duels.getInstance().getKitFile();

			kitFileConfig.getConfiguration().set("KIT.CONTENTS", BukkitUtil.serializeItemStackArray(defaultContents));
			kitFileConfig.getConfiguration().set("KIT.ARMOR_CONTENTS", BukkitUtil.serializeItemStackArray(defaultArmorContents));
			kitFileConfig.save();
		});
	}

	@Override
	public ItemStack[] getDefaultContents() {
		return this.defaultContents;
	}

	@Override
	public ItemStack[] getDefaultArmorContents() {
		return this.defaultArmorContents;
	}
}
