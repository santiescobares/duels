package dev.santimg.duels.core.kit;

import org.bukkit.entity.Player;
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
	 * Applies the default kit to a {@link Player}
	 * 
	 * @param player {@link Player} to apply the kit for
	 */
	default void apply(Player player) {
		player.getInventory().setContents(this.getDefaultContents());
		player.getInventory().setArmorContents(this.getDefaultArmorContents());
		player.updateInventory();
	}

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
