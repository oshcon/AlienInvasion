package net.doodcraft.oshcon.bukkit.invasion;

import net.doodcraft.oshcon.bukkit.invasion.events.InvasionPlayerCreationEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticMethods {

    public static void loadInvasionPlayer(InvasionPlayer pl) {
        Player player = Bukkit.getPlayer(pl.getUniqueId());
        InvasionPlayer iPlayer = InvasionPlayer.getInvasionPlayer(pl.getUniqueId());
        if (iPlayer == null) {
            return;
        }
        player.setDisplayName(InvasionClass.getPrefix(iPlayer.getInvasionClass()) + player.getName() + "§r");
    }

    public static void createInvasionPlayer(Player player) {
        Configuration data = new Configuration(InvasionPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + player.getUniqueId().toString() + ".yml");
        if (data.getString("UUID") == null) {
            // new player
            InvasionPlayer iPlayer = new InvasionPlayer(player, InvasionClass.CIVILIAN);
            data.set("UUID", iPlayer.getUniqueId().toString());
            data.set("Class", iPlayer.getInvasionClass().toString());
            data.save();
            Bukkit.getPluginManager().callEvent(new InvasionPlayerCreationEvent(iPlayer));
        } else {
            // returning player
            InvasionClass classType = InvasionClass.getInvasionClass((data.getString("Class")));
            Bukkit.getPluginManager().callEvent(new InvasionPlayerCreationEvent(new InvasionPlayer(player, classType)));
        }
    }

    public static Boolean hasPermission(Player player, String node, Boolean sendError) {
        if (player.isOp()) {
            return true;
        }

        if (player.hasPermission(InvasionPlugin.plugin.getName().toLowerCase() + ".*")) {
            return true;
        }

        if (player.hasPermission(node)) {
            return true;
        }

        if (sendError) {
            player.sendMessage("Unknown command. Type \"help\" for help.");
        }

        return false;
    }

    public static void log(String message) {
        try {
            message = "[AlienInvasion] &r" + message;
            sendConsole(message);
        } catch (Exception ex) {
            Logger logger = Bukkit.getLogger();
            logger.log(Level.INFO, removeColor("[AlienInvasion] " + message));
        }
    }

    private static void sendConsole(String message) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(addColor(message));
    }

    public static String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String removeColor(String message) {
        message = addColor(message);
        return ChatColor.stripColor(message);
    }
}