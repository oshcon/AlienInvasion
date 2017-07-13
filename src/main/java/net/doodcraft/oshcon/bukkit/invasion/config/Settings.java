package net.doodcraft.oshcon.bukkit.invasion.config;

import net.doodcraft.oshcon.bukkit.invasion.InvasionPlugin;

import java.io.File;

public class Settings {

    public static String discordToken;

    public static void addConfigDefaults() {
        discordToken = "bot-token";

        Configuration config = new Configuration(InvasionPlugin.plugin.getDataFolder() + File.separator + "config.yml");
        config.add("Discord.Token", discordToken);
        config.save();

        setNewConfigValues(config);
    }

    public static void setNewConfigValues(Configuration config) {
        discordToken = config.getString("Discord.Token");
    }

    public static void reload() {
        Configuration config = new Configuration(InvasionPlugin.plugin.getDataFolder() + File.separator + "config.yml");
        setNewConfigValues(config);
    }
}