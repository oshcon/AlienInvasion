package net.doodcraft.oshcon.bukkit.invasion.abilities.aliens;

import org.bukkit.entity.LivingEntity;

public class AbilityEntity {
    private LivingEntity entity;
    private LivingEntity target;
    private long init;

    public AbilityEntity (LivingEntity entity, LivingEntity target) {
        this.entity = entity;
        this.target = target;
        this.init = System.currentTimeMillis();
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public void setEntity(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getTarget() {
        return target;
    }

    public void setTarget(LivingEntity target) {
        this.target = target;
    }

    public long getInitializeTime() {
        return init;
    }

    public void updateInitializeTime() {
        this.init = System.currentTimeMillis();
    }
}
