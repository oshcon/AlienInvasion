package net.doodcraft.oshcon.bukkit.invasion;

import net.doodcraft.oshcon.bukkit.invasion.events.InvasionPlayerCreationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InvasionPlayer {

    public static Map<UUID, InvasionPlayer> players = new ConcurrentHashMap<>();

    private UUID uuid;
    private InvasionClass invasionClass;
    private Boolean bleeding;
    private Boolean burned;

    public InvasionPlayer(Player player, InvasionClass invasionClass) {
        this.uuid = player.getUniqueId();
        this.invasionClass = invasionClass;

        if (players.containsKey(uuid)) {
            players.remove(uuid);
            players.put(uuid, this);
        } else {
            players.put(uuid, this);
        }

        StaticMethods.loadInvasionPlayer(this);
        save();
    }

    public UUID getUniqueId() {
        return this.uuid;
    }

    public InvasionClass getInvasionClass() {
        return this.invasionClass;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(this.getUniqueId());
    }

    public void setInvasionClass(InvasionClass invasionClass) {
        this.invasionClass = invasionClass;
        reload();
    }

    public void save() {
        Configuration data = new Configuration(InvasionPlugin.plugin.getDataFolder() + File.separator + "data" + File.separator + this.getUniqueId().toString() + ".yml");
        data.set("UUID", this.getUniqueId().toString());
        data.set("Class", this.getInvasionClass().toString());
        data.save();
    }

    public void reload() {
        save();
        StaticMethods.loadInvasionPlayer(this);
        Bukkit.getPluginManager().callEvent(new InvasionPlayerCreationEvent(this));
    }

    public void destroy() {
        if (players.containsKey(this.getUniqueId())) {
            PassivesManager.clearPassives(this.getPlayer());
            players.remove(this.getUniqueId());
            this.save();
        }
    }

    public static InvasionPlayer getInvasionPlayer(UUID uuid) {
        return InvasionPlayer.getInvasionPlayers().get(uuid);
    }

    public static Map<UUID, InvasionPlayer> getInvasionPlayers() {
        return players;
    }
}