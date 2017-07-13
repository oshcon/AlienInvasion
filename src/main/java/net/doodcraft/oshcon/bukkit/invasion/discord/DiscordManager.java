package net.doodcraft.oshcon.bukkit.invasion.discord;

import net.doodcraft.oshcon.bukkit.invasion.util.StaticMethods;
import sx.blah.discord.Discord4J;
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.handle.obj.IGuild;
import sx.blah.discord.handle.obj.IRole;
import sx.blah.discord.handle.obj.IUser;

import java.util.List;

public class DiscordManager {

    public static IDiscordClient client;

    public static void setupDiscord(String token) {
        if (Discord4J.LOGGER instanceof Discord4J.Discord4JLogger) {
            ((Discord4J.Discord4JLogger) Discord4J.LOGGER).setLevel(Discord4J.Discord4JLogger.Level.NONE);
        }

        ClientBuilder cb = new ClientBuilder();
        cb.withToken(token);
        try {
            client = cb.build();
            client.getDispatcher().registerListener(new DiscordListener());
            login();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void login() {
        if (client != null) {
            if (!client.isLoggedIn()) {
                client.login();
            } else {
                client.logout();
                client.login();
            }
        }
    }

    // TODO: fix this.
    public static String getDiscordRankPrefix(IGuild guild, IUser user) {
        List<IRole> roles = guild.getRolesForUser(user);
        StaticMethods.log(roles.toString());
        if (roles.toString().contains("Bots")) {
            return "§5";
        }
        // administrators
        if (roles.toString().contains("Administrators")) {
            return "§6";
        }
        // developers
        if (roles.toString().contains("Developers")) {
            return "§c";
        }
        // moderators
        if (roles.toString().contains("Moderators")) {
            return "§9";
        }
        // memebers
        if (roles.toString().contains("Memebers")) {
            return "§a";
        }

        return "§7";
    }
}