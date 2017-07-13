package net.doodcraft.oshcon.bukkit.invasion.events;

import net.doodcraft.oshcon.bukkit.invasion.player.InvasionPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class InvasionPlayerCreationEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    private InvasionPlayer iPlayer;

    public InvasionPlayerCreationEvent(InvasionPlayer iPlayer) {
        this.iPlayer = iPlayer;
    }

    public InvasionPlayer getInvasionPlayer() {
        return this.iPlayer;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(iPlayer.getUniqueId());
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}