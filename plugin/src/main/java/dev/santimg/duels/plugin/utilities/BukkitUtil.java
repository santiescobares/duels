package dev.santimg.duels.plugin.utilities;

import java.io.*;

import org.bukkit.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.*;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class BukkitUtil {

	/**
	 * Transforms a {@link Location} to a {@link String}
	 * 
	 * @param location the {@link Location} to serialize
	 * @return the location {@link String}
	 */
	public String serializeLocation(Location location) {
		StringBuilder serializedData = new StringBuilder();

		serializedData.append(location.getWorld().getName()).append(", ");
		serializedData.append(location.getX()).append(", ");
		serializedData.append(location.getY()).append(", ");
		serializedData.append(location.getZ()).append(", ");
		serializedData.append(location.getYaw()).append(", ");
		serializedData.append(location.getPitch());

		return serializedData.toString();
	}

	/**
	 * Transforms a {@link String} to a {@link Location}
	 * 
	 * @param data the {@link String} to deserialize
	 * @return a {@link Location} instance
	 */
	public Location deserializeLocation(String data) {
		String[] splittedData = data.split(", ");

		if (splittedData.length < 6) {
			return null;
		}

		try {
			World world = Bukkit.getWorld(splittedData[0]);
			double x = Double.parseDouble(splittedData[1]);
			double y = Double.parseDouble(splittedData[2]);
			double z = Double.parseDouble(splittedData[3]);
			float yaw = Float.parseFloat(splittedData[4]);
			float pitch = Float.parseFloat(splittedData[5]);

			return new Location(world, x, y, z, yaw, pitch);
		} catch (Exception e) {
		}

		return null;
	}

	/**
	 * Transforms an {@link ItemStack} to a {@link String} in Base64
	 * 
	 * @param item the {@link ItemStack} to serialize
	 * @return the item {@link String}
	 */
	public String serializeItemStack(ItemStack item) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			dataOutput.writeObject(item);
			dataOutput.close();

			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * Transforms a {@link String} in Base64 to an {@link ItemStack}
	 * 
	 * @param data the {@link String} to deserialize
	 * @return a {@link ItemStack} instance
	 */
	public ItemStack deserializeItemStack(String data) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack item = (ItemStack) dataInput.readObject();

			dataInput.close();

			return item;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Transforms an {@link ItemStack} array to a {@link String} in Base64
	 * 
	 * @param item the {@link ItemStack} array to serialize
	 * @return the item {@link String}
	 */
	public String serializeItemStackArray(ItemStack[] items) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

			dataOutput.writeInt(items.length);

			for (int i = 0; i < items.length; i++) {
				dataOutput.writeObject(items[i]);
			}

			dataOutput.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

	/**
	 * Transforms a {@link String} in Base64 to an {@link ItemStack} array
	 * 
	 * @param data the {@link String} to deserialize
	 * @return a {@link ItemStack} array instance
	 */
	public ItemStack[] deserializeItemStackArray(String data) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
			ItemStack[] items = new ItemStack[dataInput.readInt()];

			for (int i = 0; i < items.length; i++) {
				items[i] = (ItemStack) dataInput.readObject();
			}

			dataInput.close();
			return items;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ItemStack[0];
	}

	/**
	 * Gets the {@link Server} NMS version
	 * 
	 * @return the version
	 */
	public String getServerVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().substring(23);
	}
}
