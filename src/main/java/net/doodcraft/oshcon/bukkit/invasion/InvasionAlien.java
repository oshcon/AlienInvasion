package net.doodcraft.oshcon.bukkit.invasion;

import org.bukkit.entity.Entity;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InvasionAlien {

    private static Map<Entity, InvasionAlien> aliens = new ConcurrentHashMap<>();

    Entity entity;
    InvasionAlienType type;

    public InvasionAlien(Entity entity, InvasionAlienType type) {
        this.entity = entity;
        this.type = type;

        aliens.put(this.entity, this);
    }

    public Entity getEntity() {
        return this.entity;
    }

    public InvasionAlienType getType() {
        return this.type;
    }

    public static Map<Entity, InvasionAlien> getAliens() {
        return aliens;
    }
}