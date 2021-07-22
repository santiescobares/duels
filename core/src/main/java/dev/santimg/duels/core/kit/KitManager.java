package dev.santimg.duels.core.kit;

import org.bukkit.inventory.ItemStack;

public interface KitManager {

	/**
	 * Sets the default contents of the default kit
	 * 
	 * @param defaultContents      the default contents {@link ItemStack} array
	 * @param defaultArmorContents the default armor contents {@link ItemStack}
	 *                             array
	 */
	void setDefaultKitContents(ItemStack[] defaultContents, ItemStack[] defaultArmorContents);

	/**
	 * Gets the default contents of the default kit
	 * 
	 * @return the default contents as an {@link ItemStack} array
	 */
	ItemStack[] getDefaultContents();

	/**
	 * Gets the default armor contents of the default kit
	 * 
	 * @return the default armor contents as an {@link ItemStack} array
	 */
	ItemStack[] getDefaultArmorContents();
}
