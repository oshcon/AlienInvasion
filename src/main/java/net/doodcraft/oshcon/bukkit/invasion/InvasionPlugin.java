package net.doodcraft.oshcon.bukkit.invasion;

import net.doodcraft.oshcon.bukkit.invasion.abilities.aliens.AbilityEntityHandler;
import net.doodcraft.oshcon.bukkit.invasion.abilities.aliens.AbilityManager;
import net.doodcraft.oshcon.bukkit.invasion.abilities.aliens.AcidAbility;
import net.doodcraft.oshcon.bukkit.invasion.aliens.AlienBoiler;
import net.doodcraft.oshcon.bukkit.invasion.aliens.Aliens;
import net.doodcraft.oshcon.bukkit.invasion.commands.ManageCommand;
import net.doodcraft.oshcon.bukkit.invasion.config.Settings;
import net.doodcraft.oshcon.bukkit.invasion.discord.DiscordListener;
import net.doodcraft.oshcon.bukkit.invasion.discord.DiscordManager;
import net.doodcraft.oshcon.bukkit.invasion.listeners.AlienListener;
import net.doodcraft.oshcon.bukkit.invasion.listeners.PlayerListener;
import net.doodcraft.oshcon.bukkit.invasion.player.PassivesManager;
import net.doodcraft.oshcon.bukkit.invasion.util.PlayerMethods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class InvasionPlugin extends JavaPlugin {

    public static InvasionPlugin plugin;
    public static List<Entity> aliens = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;

        registerListeners();
        setExecutors();
        cleanUpTask();
        Aliens.registerEntities();
        abilityTask();

        Settings.addConfigDefaults();

        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerMethods.createInvasionPlayer(p);
        }

        try {
            DiscordManager.setupDiscord(Settings.discordToken);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Bukkit.getScheduler().runTaskLater(InvasionPlugin.plugin, new Runnable() {
            @Override
            public void run() {
                if (DiscordManager.client != null) {
                    if (DiscordManager.client.isLoggedIn()) {
                        DiscordManager.client.getChannelByID(311976269141245963L).sendMessage("The server is now online!\nType .who to see who is online, anytime.");
                    }
                }
            }
        },20L);
    }

    @Override
    public void onDisable() {

        if (DiscordManager.client != null) {
            if (DiscordManager.client.isLoggedIn()) {
                DiscordManager.client.getChannelByID(311976269141245963L).changeTopic("OFFLINE (mc.doodcraft.net)");
                DiscordManager.client.getChannelByID(311976269141245963L).sendMessage("The server is restarting.\nWe'll be right back. ;)");
                DiscordManager.client.logout();
            }
        }

        Aliens.unregisterEntities();

        AcidAbility.remove();
        AbilityEntityHandler.remove();

        InvasionPlugin.aliens.forEach(Entity::remove);
        Bukkit.getScheduler().cancelTasks(plugin);
    }

    public void setExecutors() {
        getCommand("manage").setExecutor(new ManageCommand());
    }

    public void registerListeners() {
        registerEvents(plugin, new AlienListener());
        registerEvents(plugin, new PlayerListener());
        registerEvents(plugin, new AbilityEntityHandler());
        registerEvents(plugin, new DiscordListener());
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
                // exploderParticleTasks
                AlienBoiler.exploderParticleTasks.forEach((k, v) -> {
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

                if (DiscordManager.client != null) {
                    if (DiscordManager.client.isReady()) {
                        if (DiscordManager.client.isLoggedIn()) {
                            DiscordManager.client.getChannelByID(311976269141245963L).changeTopic("ONLINE (mc.doodcraft.net): " + Bukkit.getOnlinePlayers().size() + "/32 players online");
                        } else {
                            DiscordManager.client.login();
                        }
                    }
                }
            }
        },1L, 1000L);
    }

    public static void abilityTask() {
        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(plugin, new AbilityManager(),0L, 1L);
    }
}