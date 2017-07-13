package net.doodcraft.oshcon.bukkit.invasion.abilities.aliens;

import org.bukkit.entity.LivingEntity;

public class AcidAbility {

    public static void execute(LivingEntity entity, LivingEntity target) {
        if (entity.getWorld() != target.getWorld()) return;
        new AcidSpit(entity, target.getLocation());
    }

    public static void progress() {
        AcidSpit.progressAll();
    }

    public static void remove() {
        AcidSpit.removeAll();
    }
}
