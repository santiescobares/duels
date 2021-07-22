package dev.santimg.duels.plugin.arena.command;

import java.util.Optional;
import java.util.Set;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import dev.santimg.duels.core.arena.Arena;
import dev.santimg.duels.core.arena.ArenaManager;
import dev.santimg.duels.plugin.Duels;
import dev.santimg.duels.plugin.arena.PluginArena;
import dev.santimg.duels.plugin.utilities.ChatUtil;

public final class ArenaCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatUtil.translate("&cThis action can only be performed by players!"));
			return true;
		}

		ArenaManager arenaManager = Duels.getInstance().getArenaManager();
		Player player = (Player) sender;

		if (!player.hasPermission("duels.command.arena")) {
			player.sendMessage(ChatUtil.translate("&cYou don't have permission to perform this action!"));
			return true;
		}

		if (args.length >= 1) {
			switch (args[0].toLowerCase()) {
			case "create": {
				if (args.length >= 2) {
					String name = args[1];

					if (arenaManager.getByName(name) != null) {
						player.sendMessage(ChatUtil.translate("&cThat arena already exists!"));
						return true;
					}

					arenaManager.getRepository().getCache().add(new PluginArena(name));

					player.sendMessage(ChatUtil.translate("Arena &b" + name + " &acreated&f!"));
				} else {
					player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <arenaName>"));
				}

				break;
			}
			case "delete": {
				if (args.length >= 2) {
					Optional<Arena> arenaOp = arenaManager.getByName(args[1]);

					if (!arenaOp.isPresent()) {
						player.sendMessage(ChatUtil.translate("&cArena \"" + args[1] + "\" not found!"));
						return true;
					}

					Arena arena = arenaOp.get();

					if (arena.isEnabled()) {
						player.sendMessage(ChatUtil.translate("&cYou can't delete an arena while is enabled!"));
						return true;
					}

					arenaManager.getRepository().getCache().remove(arena);

					player.sendMessage(ChatUtil.translate("Arena &b" + arena.getName() + " &cdeleted&f!"));
				} else {
					player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <arenaName>"));
				}

				break;
			}
			case "setlocation": {
				if (args.length >= 3) {
					Optional<Arena> arenaOp = arenaManager.getByName(args[1]);
					String type = args[2];

					if (!arenaOp.isPresent()) {
						player.sendMessage(ChatUtil.translate("&cArena \"" + args[1] + "\" not found!"));
						return true;
					}

					Arena arena = arenaOp.get();

					if (arena.isEnabled()) {
						player.sendMessage(ChatUtil.translate("&cYou can't modify an arena while is enabled!"));
						return true;
					}

					switch (type.toLowerCase()) {
					case "1": {
						arena.getLocations()[0] = player.getLocation().clone();

						player.sendMessage(ChatUtil.translate("First location of &aset &fon &b" + arena.getName() + "&f!"));
						break;
					}
					case "2": {
						arena.getLocations()[1] = player.getLocation().clone();

						player.sendMessage(ChatUtil.translate("Second location of &aset &fon &b" + arena.getName() + "&f!"));
						break;
					}
					case "spectators": {
						arena.getLocations()[2] = player.getLocation().clone();

						player.sendMessage(ChatUtil.translate("Spectators spawn location of &aset &fon &b" + arena.getName() + "&f!"));
						break;
					}
					default:
						player.sendMessage(ChatUtil.translate("&cType \"" + type + "\" not found!"));
						return true;
					}
				} else {
					player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <arenaName> <1|2|spectators>"));
				}

				break;
			}
			case "toggle": {
				if (args.length >= 2) {
					Optional<Arena> arenaOp = arenaManager.getByName(args[1]);

					if (!arenaOp.isPresent()) {
						player.sendMessage(ChatUtil.translate("&cArena \"" + args[1] + "\" not found!"));
						return true;
					}

					PluginArena arena = (PluginArena) arenaOp.get();

					if (!arena.isEnabled()) {
						arena.setEnabled(true);

						player.sendMessage(ChatUtil.translate("Arena &b" + arena.getName() + " &aenabled&f!"));
					} else {
						arena.setEnabled(false);

						player.sendMessage(ChatUtil.translate("Arena &b" + arena.getName() + " &cdisabled&f!"));
					}
				} else {
					player.sendMessage(ChatUtil.translate("&cUsage: /" + label + " <arenaName>"));
				}

				break;
			}
			case "list": {
				Set<Arena> arenas = arenaManager.getRepository().getCache();

				if (arenas.isEmpty()) {
					player.sendMessage(ChatUtil.translate("&cThere are no arenas created yet!"));
					return true;
				}

				player.sendMessage("");
				player.sendMessage(ChatUtil.translate("&b&lArenas List &7(" + arenas.size() + ")"));

				for (Arena arena : arenas) {
					player.sendMessage(ChatUtil.translate(" " + arena.getName() + " &7- " + (arena.isEnabled() ? "&aEnabled" : "&cDisabled")));
				}

				player.sendMessage("");

				break;
			}
			}
		} else {
			player.sendMessage("");
			player.sendMessage(ChatUtil.translate("&b&lArena Commands"));
			player.sendMessage(ChatUtil.translate(" /arena create <arenaName>"));
			player.sendMessage(ChatUtil.translate(" /arena delete <arenaName>"));
			player.sendMessage(ChatUtil.translate(" /arena setLocation <arenaName> <1|2|spectators>"));
			player.sendMessage(ChatUtil.translate(" /arena toggle <arenaName>"));
			player.sendMessage(ChatUtil.translate(" /arena list"));
			player.sendMessage("");
		}

		return true;
	}
}
