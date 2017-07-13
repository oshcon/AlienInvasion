package net.doodcraft.oshcon.bukkit.invasion.aliens;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.entity.EntityType;

public enum Aliens {

    XENOMORPH("Xenomorph", 54, EntityType.ZOMBIE, EntityZombie.class, AlienXenomorph.class),
    SPITTER("Spitter", 51, EntityType.SKELETON, EntitySkeleton.class, AlienSpitter.class),
    BOILER("Boiler", 50, EntityType.CREEPER, EntityCreeper.class, AlienBoiler.class);

    private String name;
    private int id;
    private EntityType entityType;
    private Class<? extends Entity> nmsClass;
    private Class<? extends Entity> customClass;
    private MinecraftKey key;
    private MinecraftKey oldKey;

    private Aliens(String name, int id, EntityType entityType, Class<? extends Entity> nmsClass, Class<? extends Entity> customClass) {
        this.name = name;
        this.id = id;
        this.entityType = entityType;
        this.nmsClass = nmsClass;
        this.customClass = customClass;
        this.key = new MinecraftKey(name);
        this.oldKey = EntityTypes.b.b(nmsClass);
    }

    public static void registerEntities() { for (Aliens alien : Aliens.values()) alien.register(); }

    public static void unregisterEntities() { for (Aliens alien : Aliens.values()) alien.unregister(); }

    private void register() {
        EntityTypes.d.add(key);
        EntityTypes.b.a(id, key, customClass);
    }

    private void unregister() {
        EntityTypes.d.remove(key);
        EntityTypes.b.a(id, oldKey, nmsClass);
    }

    public String getName() {
        return name;
    }

    public int getID() {
        return id;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public Class<?> getCustomClass() {
        return customClass;
    }
}