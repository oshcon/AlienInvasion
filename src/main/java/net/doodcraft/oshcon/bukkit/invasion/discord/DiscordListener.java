package net.doodcraft.oshcon.bukkit.invasion.discord;

import net.doodcraft.oshcon.bukkit.invasion.util.StaticMethods;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.guild.channel.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;

public class DiscordListener implements Listener {

    @EventSubscriber
    public void onMessage(MessageReceivedEvent event) {
        if (event.getAuthor().equals(DiscordManager.client.getOurUser())) {
            return;
        }

        if (event.getChannel().getLongID() == 311976269141245963L) {

            if (event.getMessage().getContent().startsWith(".who")) {
                if (Bukkit.getOnlinePlayers().size() <= 0) {
                    DiscordManager.client.getChannelByID(311976269141245963L).sendMessage("Looks like nobody is online! :o\nGet the party started by joining mc.doodcraft.net!");
                    return;
                }
                List<String> names = new ArrayList<>();
                for (Player p : Bukkit.getOnlinePlayers()) {
                    names.add(p.getName());
                }
                DiscordManager.client.getChannelByID(311976269141245963L).sendMessage("Online Now (" + names.size() + "): " + StringUtils.join(names, ", "));
                return;
            }

            Bukkit.broadcastMessage("§8[§dDISCORD§8]§r " + DiscordManager.getDiscordRankPrefix(event.getGuild(), event.getAuthor()) + event.getAuthor().getName() + "§8: §7" + event.getMessage());
        }
    }

    @EventSubscriber
    public void onReady(ReadyEvent event) {
        DiscordManager.client.changePlayingText("mc.doodcraft.net");
        DiscordManager.client.getChannelByID(311976269141245963L).changeTopic("ONLINE (mc.doodcraft.net): " + Bukkit.getOnlinePlayers().size() + "/32 players online");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        DiscordManager.client.getChannelByID(311976269141245963L).sendMessage(player.getName() + " joined.");
        DiscordManager.client.getChannelByID(311976269141245963L).changeTopic("ONLINE (mc.doodcraft.net): " + Bukkit.getOnlinePlayers().size() + "/32 players online");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        DiscordManager.client.getChannelByID(311976269141245963L).sendMessage(player.getName() + " quit.");
        DiscordManager.client.getChannelByID(311976269141245963L).changeTopic("ONLINE (mc.doodcraft.net): " + Bukkit.getOnlinePlayers().size() + "/32 players online");

    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        DiscordManager.client.getChannelByID(311976269141245963L).sendMessage("[CHAT] " + StaticMethods.removeColor(player.getDisplayName() + ": " + event.getMessage()));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        DiscordManager.client.getChannelByID(311976269141245963L).sendMessage("[DEATH] " + StaticMethods.removeColor(event.getDeathMessage() + "."));
    }
}