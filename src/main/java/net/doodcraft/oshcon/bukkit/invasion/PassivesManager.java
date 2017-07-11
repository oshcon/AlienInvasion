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

        // Set walk speed
        player.setWalkSpeed(InvasionClass.getWalkSpeedScale(iPlayer.getInvasionClass()));

        // Set passives and recurring effects
        if (iPlayer.getInvasionClass() == InvasionClass.SURVIVALIST) {
            player.setSilent(true);
        }

        if (iPlayer.getInvasionClass() == InvasionClass.MEDIC) {
            int task = Bukkit.getScheduler().scheduleSyncRepeatingTask(InvasionPlugin.plugin, new Runnable() {
                @Override
                public void run() {
                    // add health
                    if (player.getHealth() <= 19.5 && !player.isDead()) {
                        player.setHealth(player.getHealth() + 0.5);
                    }
                }
            },1L,55L);

            if (!passiveTasks.containsKey(player.getUniqueId())) {
                passiveTasks.put(player.getUniqueId(), task);
            }
        }
    }

    public static void clearPassives(Player player) {
        player.setWalkSpeed(0.2F);
        player.setSilent(false);

        if (passiveTasks.containsKey(player.getUniqueId())) {
            Bukkit.getScheduler().cancelTask(passiveTasks.get(player.getUniqueId()));
            passiveTasks.remove(player.getUniqueId());
        }
    }
}