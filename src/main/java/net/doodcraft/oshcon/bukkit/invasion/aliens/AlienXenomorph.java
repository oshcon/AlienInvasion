package net.doodcraft.oshcon.bukkit.invasion.aliens;

import net.minecraft.server.v1_12_R1.EntityZombie;
import net.minecraft.server.v1_12_R1.GenericAttributes;
import net.minecraft.server.v1_12_R1.World;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Zombie;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class AlienXenomorph extends EntityZombie {

    public AlienXenomorph(World world) {
        super(world);
    }

    protected void initAttributes() {
        super.initAttributes();
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(0.29);
        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0);
        this.getAttributeInstance(GenericAttributes.ATTACK_DAMAGE).setValue(2.00);
        this.setCustomName("§bXenomorph");
        this.setBaby(false);
    }

    public static Zombie spawn(Location loc) {
        org.bukkit.World world = loc.getWorld();
        AlienXenomorph xeno = new AlienXenomorph(((CraftWorld) world).getHandle());
        xeno.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        ((CraftWorld)loc.getWorld()).getHandle().addEntity(xeno, CreatureSpawnEvent.SpawnReason.CUSTOM);
        return (Zombie) xeno.getBukkitEntity();
    }
}