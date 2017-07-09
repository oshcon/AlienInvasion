package net.doodcraft.oshcon.bukkit.invasion;

import net.doodcraft.oshcon.bukkit.invasion.listeners.SurvivalistListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class InvasionPlugin extends JavaPlugin {

    public static InvasionPlugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        registerListeners();
        setExecutors();
        for (Player p : Bukkit.getOnlinePlayers()) {
            StaticMethods.createInvasionPlayer(p);
        }
    }

    public void setExecutors() {
        getCommand("manage").setExecutor(new ManageCommand());
    }

    public void registerListeners() {
        registerEvents(plugin, new InvasionListener());
        registerEvents(plugin, new SurvivalistListener());
    }

    public static void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
}