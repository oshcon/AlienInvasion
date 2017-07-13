package net.doodcraft.oshcon.bukkit.invasion.abilities.aliens;

import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class AbilityEntityHandler implements Listener {

    public static ConcurrentHashMap<UUID, AbilityEntity> entityarray = new ConcurrentHashMap<UUID, AbilityEntity>();

    static Random rand = new Random();

    public AbilityEntityHandler() {

    }

    public static void addEntity(LivingEntity entity, LivingEntity target) {
        if (!(entity instanceof Creature)) {
            return;
        }
        if (entityarray.containsKey(entity.getUniqueId())) {
            entityarray.get(entity.getUniqueId()).setTarget(target);
            return;
        }

        entityarray.put(entity.getUniqueId(), new AbilityEntity(entity, target));
    }

    public static void removeEntity(LivingEntity entity) {
        if (entityarray.containsKey(entity.getUniqueId())) {
            entityarray.remove(entity.getUniqueId());
        }
    }

    public static void progress() {
        for (UUID uuid : entityarray.keySet()) {
            AbilityEntity aEntity = entityarray.get(uuid);
            LivingEntity entity = aEntity.getEntity();
            LivingEntity target = aEntity.getTarget();
            Creature e = (Creature) entity;
            if (entity == null || entity.isDead() || e.getTarget() == null || e.getTarget().isDead()) {
                entityarray.remove(uuid);
                return;
            }
            if (target == null || target.isDead()) {
                entityarray.remove(uuid);
                return;
            }

            if (rand.nextInt(17) == 0) {
                if (!entity.hasLineOfSight(target)) {
                    return;
                }

                AcidAbility.execute(entity, target);
            }
        }
    }

    public static void remove() {
        entityarray.clear();
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (event.getTarget() == null) {
            return;
        }
        if (event.getEntity() instanceof LivingEntity && event.getTarget() instanceof LivingEntity) {
            if (event.getEntity() instanceof Skeleton) {
                LivingEntity entity = (LivingEntity) event.getEntity();
                AbilityEntityHandler.addEntity(entity, (LivingEntity) event.getTarget());
            }
        }
    }
}
