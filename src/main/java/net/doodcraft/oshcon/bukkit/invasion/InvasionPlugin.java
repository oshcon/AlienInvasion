package net.doodcraft.oshcon.bukkit.invasion;

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
        cleanUpTask();
        for (Player p : Bukkit.getOnlinePlayers()) {
            StaticMethods.createInvasionPlayer(p);
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTasks(plugin);
        // remove all alien mobs. no need to store them for this gamemode.
        InvasionAlien.getAliens().forEach((k, v) -> v.getEntity().remove());
    }

    public void setExecutors() {
        getCommand("manage").setExecutor(new ManageCommand());
    }

    public void registerListeners() {
        registerEvents(plugin, new InvasionListener());
        registerEvents(plugin, new AcidManager());
    }

    public static void registerEvents(Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }

    public static void cleanUpTask() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(InvasionPlugin.plugin, new Runnable() {
            @Override
            public void run() {
                // iterate over all task collections for null entries to cancel
                // help prevent memory leaks from poor code? idk :)

                // exploderParticleTasks
                InvasionListener.exploderParticleTasks.forEach((k, v) -> {
                    if (k == null) {
                        Bukkit.getScheduler().cancelTask(v);
                    }
                });

                // acidTasks/projectiles
                AcidManager.acidParticleTasks.forEach((k, v) -> {
                   if (k == null) {
                       Bukkit.getScheduler().cancelTask(v);
                   }
                });

                // passivesTasks
                PassivesManager.passiveTasks.forEach((k, v) -> {
                    if (Bukkit.getPlayer(k) == null) {
                        Bukkit.getScheduler().cancelTask(v);
                    }
                });
            }
        },1L, 500L);
    }
}