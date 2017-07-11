package net.doodcraft.oshcon.bukkit.invasion;

import de.slikey.effectlib.util.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.GameMode;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class AcidManager implements Listener {

    public static Map<Entity, Integer> acidParticleTasks = new HashMap<>();

    @EventHandler
    public void onShoot(ProjectileLaunchEvent event) {
        if (event.getEntity().getShooter() instanceof Skeleton) {

            if (InvasionAlien.getAliens().containsKey(event.getEntity().getShooter())) {
                Projectile acid = event.getEntity();
                acid.setBounce(false);

                int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(InvasionPlugin.plugin, new Runnable(){

                    @Override
                    public void run() {
                        ParticleEffect.SPELL_MOB.display(null, acid.getLocation().add(0.1, 0, 0.1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                        ParticleEffect.SPELL_MOB.display(null, acid.getLocation().add(-0.1, 0, -0.1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                        ParticleEffect.SPELL_MOB.display(null, acid.getLocation().add(0.1, 0, -0.1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                        ParticleEffect.SPELL_MOB.display(null, acid.getLocation().add(-0.1, 0, 0.1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                        ParticleEffect.SPELL_MOB.display(null, acid.getLocation(), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                    }
                },1,1);

                if (!acidParticleTasks.containsKey(acid)) {
                    acidParticleTasks.put(acid, task);
                }
            }
        }
    }

    @EventHandler
    public void onHit(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Arrow) {
            if (acidParticleTasks.containsKey(event.getEntity())) {

                if (event.getHitBlock() != null) {
                    ParticleEffect.SPELL_MOB.display(null, event.getHitBlock().getLocation().add(1, 0, 1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                    ParticleEffect.SPELL_MOB.display(null, event.getHitBlock().getLocation().add(-1, 0, -1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                    ParticleEffect.SPELL_MOB.display(null, event.getHitBlock().getLocation().add(-1, 0, 1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                    ParticleEffect.SPELL_MOB.display(null, event.getHitBlock().getLocation().add(1, 0, -1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
                    ParticleEffect.SPELL_MOB.display(null, event.getHitBlock().getLocation(), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);

                    Bukkit.getScheduler().cancelTask(acidParticleTasks.get(event.getEntity()));
                }

                if (event.getHitEntity() != null) {
                    ParticleEffect.SPELL_MOB.display(null, event.getHitEntity().getLocation(), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 20);
                    Bukkit.getScheduler().cancelTask(acidParticleTasks.get(event.getEntity()));

                    if (event.getHitEntity() instanceof Player) {
                        Player player = (Player) event.getHitEntity();

                        int add = 0;

                        for (PotionEffect effect : player.getActivePotionEffects()) {
                            if (effect.getType().equals(PotionEffectType.CONFUSION)) {
                                add = (effect.getDuration() * 20);
                                player.removePotionEffect(effect.getType());
                            }
                        }

                        PotionEffect nausea = new PotionEffect(PotionEffectType.CONFUSION, (6 * 20) + add, 1, false, false);

                        if (player.getGameMode() != GameMode.CREATIVE) {
                            player.addPotionEffect(nausea);
                        }
                    }
                }

                acidParticleTasks.remove(event.getEntity());
                event.getEntity().remove();
            }
        }
    }
}