package net.doodcraft.oshcon.bukkit.invasion.abilities.aliens;

import de.slikey.effectlib.util.ParticleEffect;
import net.doodcraft.oshcon.bukkit.invasion.util.StaticMethods;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

import java.util.concurrent.ConcurrentHashMap;

public class AcidSpit {

    public static ConcurrentHashMap<Integer, AcidSpit> instances = new ConcurrentHashMap<Integer, AcidSpit>();

    private LivingEntity entity;
    private Location origin;
    private Location head;
    private Vector dir;

    private static int ID = Integer.MIN_VALUE;
    private int id;

    public AcidSpit(LivingEntity entity, Location target) {
        this.entity = entity;
        origin = entity.getEyeLocation();
        head = entity.getEyeLocation();
        dir = StaticMethods.getDirection(entity.getLocation(), target).normalize();
        id = ID;
        instances.put(id, this);
        if (ID == Integer.MAX_VALUE) {
            ID = Integer.MIN_VALUE;
        }
        ID++;
    }

    @SuppressWarnings("deprecation")
    private boolean progress() {

        if (entity == null) {
            return false;
        }

        if (entity.getWorld() != head.getWorld()) {
            return false;
        }

        if (origin.distance(head) > 20) {
            return false;
        }

        if (head.getBlock().getType().isTransparent() || head.getBlock().isLiquid()) {
            return false;
        }

        head.add(dir.multiply(1));

        ParticleEffect.SPELL_MOB.display(null, head.add(0.08, 0, 0.08), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
        ParticleEffect.SPELL_MOB.display(null, head.add(-0.08, 0, -0.1), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
        ParticleEffect.SPELL_MOB.display(null, head.add(0.08, 0, -0.08), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
        ParticleEffect.SPELL_MOB.display(null, head.add(-0.08, 0, 0.08), Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);
        ParticleEffect.SPELL_MOB.display(null, head, Color.fromBGR(60, 255, 80), 64, 0, 0, 0, 1, 10);

        for (Entity entity : StaticMethods.getEntitiesAroundPoint(head, 1.25)) {
            if (entity instanceof LivingEntity && entity.getEntityId() != this.entity.getEntityId()) {
                if (entity instanceof Creature) {
                    ((Creature) entity).setTarget(this.entity);
                }
                ((LivingEntity) entity).damage(3.5, this.entity);
                entity.setLastDamageCause(new EntityDamageByEntityEvent(this.entity, entity, EntityDamageEvent.DamageCause.PROJECTILE, 4.0));
                return false;
            }
        }
        return true;
    }

    private void remove() {
        instances.remove(id);
    }

    public static void progressAll() {
        for (int id : instances.keySet()) {
            if (!instances.get(id).progress()) {
                instances.get(id).remove();
            }
        }
    }

    public static void removeAll() {
        for (int id : instances.keySet()) {
            instances.remove(id);
        }
    }
}