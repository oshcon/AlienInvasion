package net.doodcraft.oshcon.bukkit.invasion.util;

import net.doodcraft.oshcon.bukkit.invasion.InvasionPlugin;
import net.doodcraft.oshcon.bukkit.invasion.config.Configuration;
import net.doodcraft.oshcon.bukkit.invasion.events.InvasionPlayerCreationEvent;
import net.doodcraft.oshcon.bukkit.invasion.player.InvasionClass;
import net.doodcraft.oshcon.bukkit.invasion.player.InvasionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;

public class PlayerMethods {

    public static void loadInvasionPlayer(InvasionPlayer pl) {
        Player player = Bukkit.getPlayer(pl.getUniqueId());
        InvasionPlayer iPlayer = InvasionPlayer.getInvasionPlayer(pl.getUniqueId());
        if (iPlayer == null) {
            return;
        }
        player.setDisplayName("§8[" + InvasionClass.getPrefix(iPlayer.getInvasionClass()) + InvasionClass.getName(iPlayer.getInvasionClass()) + "§8] §r" + player.getName() + "§r");
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
}
