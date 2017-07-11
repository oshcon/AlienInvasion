package net.doodcraft.oshcon.bukkit.invasion;

import de.slikey.effectlib.util.ParticleEffect;
import net.doodcraft.oshcon.bukkit.enderpads.api.EnderPadCreateEvent;
import net.doodcraft.oshcon.bukkit.invasion.events.InvasionPlayerCreationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.HashMap;
import java.util.Map;

public class InvasionListener implements Listener {

    public static Map<Entity, Integer> exploderParticleTasks = new HashMap<>();

    @EventHandler
    public void onSpawn(EntitySpawnEvent event) {

        Entity entity = event.getEntity();

        if (entity instanceof Monster) {

            if (entity.getType() == EntityType.ZOMBIE) {
                Zombie zombie = (Zombie) entity;
                zombie.setCustomName("MELEE ALIEN");
                zombie.setCustomNameVisible(true);
                new InvasionAlien(entity, InvasionAlienType.NORMAL_MELEE);
                return;
            }

            if (entity.getType() == EntityType.SKELETON) {
                Skeleton skeleton = (Skeleton) entity;
                skeleton.setCustomName("RANGED ALIEN");
                skeleton.setCustomNameVisible(true);
                new InvasionAlien(entity, InvasionAlienType.NORMAL_RANGED);
                return;
            }

            if (entity.getType() == EntityType.CREEPER) {
                Creeper creeper = (Creeper) entity;
                creeper.setCustomName("EXPLODER ALIEN");
                creeper.setCustomNameVisible(true);

                // spawn particles for the exploder
                int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(InvasionPlugin.plugin, new Runnable() {

                    @Override
                    public void run() {
                        if (!entity.isDead()) {
                            ParticleEffect.FLAME.display(0, 0, 0, 0, 5, ((Creeper) entity).getEyeLocation(), 64.0);
                        }
                    }
                },1L, 10L);

                exploderParticleTasks.put(entity, task);
                new InvasionAlien(entity, InvasionAlienType.NORMAL_RANGED);
                return;
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof Monster) {
                if (!InvasionAlien.getAliens().containsKey(entity)) {
                    entity.remove();
                }
            }
        }
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
    public void onEntityDamage(EntityDamageByEntityEvent event) {
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