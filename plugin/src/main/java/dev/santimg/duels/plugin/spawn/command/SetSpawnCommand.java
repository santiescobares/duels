package dev.santimg.duels.plugin.spawn.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.utilities.ChatUtil;

public final class SetSpawnCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatUtil.translate("&cThis action can only be performed by players!"));
			return true;
		}

		Player player = (Player) sender;

		if (!player.hasPermission("duels.command.setspawn")) {
			player.sendMessage(ChatUtil.translate("&cYou don't have permission to perform this action!"));
			return true;
		}

		Duels.getInstance().getSpawnManager().setSpawnLocation(player.getLocation().clone());

		player.sendMessage(ChatUtil.translate("Spawn &aset &fon your location!"));
		return true;
	}
}
