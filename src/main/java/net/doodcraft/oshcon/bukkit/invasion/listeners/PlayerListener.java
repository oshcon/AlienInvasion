package net.doodcraft.oshcon.bukkit.invasion.listeners;

import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadCreateEvent;
import net.doodcraft.oshcon.bukkit.invasion.events.InvasionPlayerCreationEvent;
import net.doodcraft.oshcon.bukkit.invasion.player.InvasionClass;
import net.doodcraft.oshcon.bukkit.invasion.player.InvasionPlayer;
import net.doodcraft.oshcon.bukkit.invasion.player.PassivesManager;
import net.doodcraft.oshcon.bukkit.invasion.util.StaticMethods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.*;

public class PlayerListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player p = event.getPlayer();
        String msg = event.getMessage();

        if (p.hasPermission("invasion.colorchat")) {
            msg = StaticMethods.addColor(event.getMessage());
        }

        event.setMessage(msg);
        event.setFormat("%s§8: §7" + "%s");
    }

    @EventHandler
    public void onBreak(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("invasion.build")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        // create our own death messages
    }

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

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (InvasionPlayer.getInvasionPlayer(player.getUniqueId()) != null) {
                InvasionPlayer iPlayer = InvasionPlayer.getInvasionPlayer(player.getUniqueId());
                double damage = InvasionClass.getDamageScale(iPlayer.getInvasionClass()) * event.getDamage();
                StaticMethods.log(player.getName() + "'s Damage | OLD: (" + event.getDamage() + ") NEW: (" + damage + ")");
                event.setDamage(damage);
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            if (InvasionPlayer.getInvasionPlayer(player.getUniqueId()) != null) {
                InvasionPlayer iPlayer = InvasionPlayer.getInvasionPlayer(player.getUniqueId());
                double damage = InvasionClass.getDamageDealScale(iPlayer.getInvasionClass()) * event.getDamage(EntityDamageEvent.DamageModifier.BASE);
                event.setDamage(damage);
            }
        }
    }

    @EventHandler
    public void onEnderPadCreate(EnderPadCreateEvent event) {
        Player player = event.getPlayer();

        if (InvasionPlayer.getInvasionPlayer(player.getUniqueId()) != null) {
            InvasionPlayer iPlayer = InvasionPlayer.getInvasionPlayer(player.getUniqueId());
            InvasionClass iClass = iPlayer.getInvasionClass();

            if (iClass != InvasionClass.ENGINEER) {
                player.sendMessage("§cOnly Engineers can build EnderPads.");
                event.setCancelled(true);
            }
        }
    }
}
