package net.doodcraft.oshcon.bukkit.invasion;

import net.doodcraft.oshcon.bukkit.invasion.events.InvasionPlayerCreationEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class InvasionListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        StaticMethods.createInvasionPlayer(event.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent event) {
        Player player = event.getPlayer();
        if (InvasionPlayer.getInvasionPlayer(player.getUniqueId()) != null) {
            InvasionPlayer iPlayer = InvasionPlayer.getInvasionPlayer(player.getUniqueId());
            iPlayer.destroy();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (InvasionPlayer.getInvasionPlayer(player.getUniqueId()) != null) {
            InvasionPlayer iPlayer = InvasionPlayer.getInvasionPlayer(player.getUniqueId());
            iPlayer.destroy();
        }
    }

    @EventHandler
    public void onCreation(InvasionPlayerCreationEvent event) {
        PassivesManager.setupPassives(event.getPlayer());
    }
}