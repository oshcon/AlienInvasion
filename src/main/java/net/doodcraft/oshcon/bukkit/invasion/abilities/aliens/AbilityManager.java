package net.doodcraft.oshcon.bukkit.invasion.abilities.aliens;

public class AbilityManager implements Runnable {
    public void run() {
        AbilityEntityHandler.progress();
        AcidAbility.progress();
    }
}
