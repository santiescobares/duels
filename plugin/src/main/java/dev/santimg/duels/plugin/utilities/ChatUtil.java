package dev.santimg.duels.plugin.utilities;

import java.util.*;

import org.bukkit.*;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class ChatUtil {

	public String LINE;
	public String SHORT_LINE;
	public String MENU_LINE;
	public String SB_LINE;

	public String[] ABC = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	static {
		LINE = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "-----------------------------------------------------";
		SHORT_LINE = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "---------------------------------------";
		MENU_LINE = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "------------------------------";
		SB_LINE = ChatColor.GRAY.toString() + ChatColor.STRIKETHROUGH.toString() + "---------------------";
	}

	/**
	 * Colors a {@link String} using chat color codes
	 * 
	 * @param text the {@link String} to translate
	 * @return the colored {@link String}
	 */
	public String translate(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	/**
	 * Colors a {@link String} list using chat color codes
	 * 
	 * @param text the {@link String} list to translate
	 * @return the colored {@link String} list
	 */
	public List<String> translate(List<String> text) {
		List<String> toReturn = Lists.newArrayList();

		for (String line : text) {
			toReturn.add(translate(line));
		}

		return toReturn;
	}

	/**
	 * Gets a random character from A-Z
	 * 
	 * @return the random character as {@link String}
	 */
	public String randomABC() {
		return ABC[new Random().nextInt(ABC.length - 1)];
	}

	/**
	 * Broadcasts a message in the server
	 * 
	 * @param message the message {@link String}
	 */
	public void broadcast(String message) {
		Bukkit.broadcastMessage(translate(message));
	}

	/**
	 * Broadcasts multiple messages in the server
	 * 
	 * @param message the messages {@link String} list
	 */
	public void broadcast(List<String> message) {
		message.forEach(ChatUtil::broadcast);
	}

	/**
	 * Broadcasts multiple messages in the server
	 * 
	 * @param message the messages {@link String} array
	 */
	public void broadcast(String... message) {
		broadcast(Arrays.asList(message));
	}

	/**
	 * Sends a message to console
	 * 
	 * @param message the message {@link String}
	 */
	public void log(String message) {
		Bukkit.getConsoleSender().sendMessage(translate(message));
	}

	/**
	 * Builds a {@link String} from multiple arguments
	 * 
	 * @param arguments the {@link String} list of arguments
	 * @param separator the separator {@link String} between arguments
	 * @return the built {@link String}
	 */
	public String buildText(List<String> arguments, String separator) {
		StringBuilder text = new StringBuilder();

		Joiner.on(separator).appendTo(text, arguments);

		return text.toString();
	}

	/**
	 * Builds a {@link String} from multiple arguments
	 * 
	 * @param arguments the {@link String} list of arguments
	 * @return the built {@link String} using a space as default separator
	 */
	public String buildText(List<String> arguments) {
		return buildText(arguments, " ");
	}

	/**
	 * Builds a {@link String} from multiple arguments
	 * 
	 * @param arguments the {@link String} array of arguments
	 * @param separator the separator {@link String} between arguments
	 * @return the built {@link String}
	 */
	public String buildText(String[] arguments, String separator) {
		return buildText(Arrays.asList(arguments), separator);
	}

	/**
	 * Builds a {@link String} from multiple arguments
	 * 
	 * @param arguments the {@link String} array of arguments
	 * @return the built {@link String} using a space as default separator
	 */
	public String buildText(String[] arguments) {
		return buildText(arguments, " ");
	}
}
