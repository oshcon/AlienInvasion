package net.doodcraft.oshcon.bukkit.invasion.aliens;

import de.slikey.effectlib.util.ParticleEffect;
import net.doodcraft.oshcon.bukkit.invasion.InvasionPlugin;
import net.minecraft.server.v1_12_R1.EntityCreeper;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.util.HashMap;
import java.util.Map;

public class AlienBoiler extends EntityCreeper {

    public static Map<Entity, Integer> exploderParticleTasks = new HashMap<>();

    public AlienBoiler(World world) {
        super(world);
        startFlameTask(this);
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.19);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0);
        this.setCustomName("§cBoiler");
    }

    private void startFlameTask(AlienBoiler xeno) {
        int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(InvasionPlugin.plugin, new Runnable() {
            @Override
            public void run() {
                if (xeno.isAlive()) {
                    ParticleEffect.FLAME.display(0, 0, 0, 0, 1, xeno.getBukkitEntity().getLocation().add(0.5, 0.8, 0.5), 64.0);
                    ParticleEffect.FLAME.display(0, 0, 0, 0, 1, xeno.getBukkitEntity().getLocation().add(-0.5, 0.9, -0.5), 64.0);
                    ParticleEffect.FLAME.display(0, 0, 0, 0, 1, xeno.getBukkitEntity().getLocation().add(-0.5, 1.0, 0.5), 64.0);
                    ParticleEffect.FLAME.display(0, 0, 0, 0, 1, xeno.getBukkitEntity().getLocation().add(0.5, 1.1, -0.5), 64.0);
                }
            }
        },1L, 10L);

        exploderParticleTasks.put(xeno.getBukkitEntity(), task);
    }

    public static Creeper spawn(Location loc) {
        org.bukkit.World world = loc.getWorld();
        AlienBoiler xeno = new AlienBoiler(((CraftWorld) world).getHandle());
        xeno.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(xeno, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (Creeper) xeno.getBukkitEntity();
    }
}