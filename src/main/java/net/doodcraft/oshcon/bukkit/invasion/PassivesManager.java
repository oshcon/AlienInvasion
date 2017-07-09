package net.doodcraft.oshcon.bukkit.invasion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PassivesManager {
    public static Map<UUID, Integer> passiveTasks = new ConcurrentHashMap<>();

    public static void setupPassives(Player player) {

        clearPassives(player);

        if (InvasionPlayer.getInvasionPlayer(player.getUniqueId()) == null) {
            return;
        }

        InvasionPlayer iPlayer = InvasionPlayer.getInvasionPlayer(player.getUniqueId());

        if (iPlayer.getInvasionClass() == InvasionClass.SURVIVALIST) {
            // set up recurring potion effects
        }

        if (iPlayer.getInvasionClass() == InvasionClass.SENTRY) {

        }
    }

    public static void clearPassives(Player player) {
        if (passiveTasks.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(passiveTasks.get(player.getUniqueId()));
            passiveTasks.remove(player.getUniqueId());
        }
    }
}