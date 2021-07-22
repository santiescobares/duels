package dev.santimg.duels.plugin.spawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.utilities.ChatUtil;

public final class SpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatUtil.translate("&cThis action can only be performed by players!"));
			return true;
		}

		Player player = (Player) sender;

		if (!player.hasPermission("duels.command.spawn")) {
			player.sendMessage(ChatUtil.translate("&cYou don't have permission to perform this action!"));
			return true;
		}

		if (Duels.getInstance().getMatchManager().getPlayerMatch(player) != null) {
			player.sendMessage(ChatUtil.translate("&cYou can't execute this command while in a match!"));
			return true;
		}

		if (Duels.getInstance().getSpawnManager().teleportToSpawn(player)) {
			player.sendMessage(ChatUtil.translate("You have been &ateleported &fto the spawn!"));
		} else {
			player.sendMessage(ChatUtil.translate("&cSpawn is not definied yet!"));
		}

		return true;
	}
}
