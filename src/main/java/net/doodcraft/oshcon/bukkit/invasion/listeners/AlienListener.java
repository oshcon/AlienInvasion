package net.doodcraft.oshcon.bukkit.invasion.listeners;

import net.doodcraft.oshcon.bukkit.invasion.InvasionPlugin;
import net.doodcraft.oshcon.bukkit.invasion.aliens.AlienBoiler;
import net.doodcraft.oshcon.bukkit.invasion.aliens.AlienSpitter;
import net.doodcraft.oshcon.bukkit.invasion.aliens.AlienXenomorph;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class AlienListener implements Listener {

    @EventHandler
    public void onDeath(EntityDeathEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Skeleton) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
        if (entity instanceof Zombie) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
        if (entity instanceof Creeper) {
            event.getDrops().clear();
            event.setDroppedExp(0);
        }
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof AlienXenomorph || event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.CUSTOM) {
            return;
        }

        if (entity instanceof Monster) {
            if (entity.getType() == EntityType.ZOMBIE) {
                Bukkit.getScheduler().runTaskLater(InvasionPlugin.plugin, new Runnable() {
                    @Override
                    public void run() {
                        Location loc = entity.getLocation();
                        Zombie xeno = AlienXenomorph.spawn(loc);
                        InvasionPlugin.aliens.add(xeno);
                    }
                },1L);
            }
            if (entity.getType() == EntityType.SKELETON) {
                Bukkit.getScheduler().runTaskLater(InvasionPlugin.plugin, new Runnable() {
                    @Override
                    public void run() {
                        Location loc = entity.getLocation();
                        Skeleton xeno = AlienSpitter.spawn(loc);
                        InvasionPlugin.aliens.add(xeno);
                    }
                },1L);
            }
            if (entity.getType() == EntityType.CREEPER) {
                Bukkit.getScheduler().runTaskLater(InvasionPlugin.plugin, new Runnable() {
                    @Override
                    public void run() {
                        Location loc = entity.getLocation();
                        Creeper xeno = AlienBoiler.spawn(loc);
                        InvasionPlugin.aliens.add(xeno);
                    }
                },1L);
            }
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof Monster) {
                if (!InvasionPlugin.aliens.contains(entity)) {
                    entity.remove();
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (entity instanceof Creeper) {
            if (InvasionPlugin.aliens.contains(entity)) {
                Location loc = entity.getLocation();
                entity.getLocation().getWorld().createExplosion(loc.getX(), loc.getY(), loc.getZ(), 2.0F, true, false);
                entity.remove();
            }
        }
    }
}
