package net.doodcraft.oshcon.bukkit.invasion;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManageCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("manage")) {
            if (sender instanceof Player) {

                Player player = (Player) sender;

                if (!StaticMethods.hasPermission(player, "alieninvasion.command.manage", true)) {
                    return false;
                }

                if (args.length == 0) {
                    sender.sendMessage("Invalid command.");
                    return true;
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    if (!StaticMethods.hasPermission(player, "alieninvasion.command.reload", true)) {
                        return false;
                    }

                    reload();
                    return true;
                }

                if (args[0].equalsIgnoreCase("setclass")) {
                    if (!StaticMethods.hasPermission(player, "alieninvasion.command.setclass", true)) {
                        return false;
                    }

                    if (args.length == 3) {
                        setInvasionClass(sender, args[1], args[2]);
                        return true;
                    } else {
                        sender.sendMessage("Invalid args length.");
                        return false;
                    }
                }

                sender.sendMessage("Invalid command.");
                return false;
            } else {
                if (args.length == 0) {
                    sender.sendMessage("Invalid command.");
                    return true;
                }

                if (args[0].equalsIgnoreCase("setclass")) {
                    if (args.length == 3) {
                        setInvasionClass(sender, args[1], args[2]);
                        return true;
                    } else {
                        sender.sendMessage("Invalid args length.");
                        return false;
                    }
                }

                if (args[0].equalsIgnoreCase("reload")) {
                    reload();
                    return true;
                }
            }
        }
        return false;
    }

    public void reload() {
        // clear player cache
        // create new InvasionPlayer for online
    }

    public void setInvasionClass(CommandSender sender, String p, String invasionClass) {
        if (Bukkit.getPlayer(p) == null) {
            sender.sendMessage("Invalid player. They are offline.");
            return;
        }

        if (!InvasionClass.isInvasionClass(invasionClass)) {
            sender.sendMessage("Invalid InvasionClass name.");
            return;
        }

        Player player = Bukkit.getPlayer(p);
        InvasionClass iClass = InvasionClass.getInvasionClass(invasionClass);

        if (InvasionPlayer.getInvasionPlayer(player.getUniqueId()) == null) {
            new InvasionPlayer(player, iClass);
            sender.sendMessage("Player did not have a class. Set their class to " + invasionClass);
        } else {
            InvasionPlayer.getInvasionPlayer(player.getUniqueId()).setInvasionClass(iClass);
            sender.sendMessage("Set " + player.getName() + "'s class to " + InvasionClass.getPrefix(iClass) + InvasionClass.getName(iClass));
        }
    }
}