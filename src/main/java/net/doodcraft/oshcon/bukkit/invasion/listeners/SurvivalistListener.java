package net.doodcraft.oshcon.bukkit.invasion.listeners;

import net.doodcraft.oshcon.bukkit.invasion.InvasionClass;
import net.doodcraft.oshcon.bukkit.invasion.events.InvasionPlayerCreationEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SurvivalistListener implements Listener {
    @EventHandler
    public void onCreation(InvasionPlayerCreationEvent event) {
        if (event.getInvasionPlayer().getInvasionClass().equals(InvasionClass.SURVIVALIST)) {
            event.getPlayer().setWalkSpeed(0.4F);
        }
    }
}
