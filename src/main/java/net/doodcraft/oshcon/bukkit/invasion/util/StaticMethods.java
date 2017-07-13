package net.doodcraft.oshcon.bukkit.invasion.util;

import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StaticMethods {

    public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
        List<Entity> entities = new ArrayList<Entity>();
        World world = location.getWorld();

        int smallX = (int) (location.getX() - radius) >> 4;
        int bigX = (int) (location.getX() + radius) >> 4;
        int smallZ = (int) (location.getZ() - radius) >> 4;
        int bigZ = (int) (location.getZ() + radius) >> 4;

        for (int x = smallX; x <= bigX; x++) {
            for (int z = smallZ; z <= bigZ; z++) {
                if (world.isChunkLoaded(x, z)) {
                    entities.addAll(Arrays.asList(world.getChunkAt(x, z).getEntities()));
                }
            }
        }

        Iterator<Entity> entityIterator = entities.iterator();
        while (entityIterator.hasNext()) {
            Entity e = entityIterator.next();
            if (e.getWorld().equals(location.getWorld()) && e.getLocation().distanceSquared(location) > radius * radius) {
                entityIterator.remove();
            } else if (e instanceof Player && ((Player) e).getGameMode().equals(GameMode.SPECTATOR)) {
                entityIterator.remove();
            }
        }

        return entities;
    }

    public static Vector getDirection(Location location, Location destination) {
        double x1, y1, z1;
        double x0, y0, z0;

        x1 = destination.getX();
        y1 = destination.getY();
        z1 = destination.getZ();

        x0 = location.getX();
        y0 = location.getY();
        z0 = location.getZ();

        return new Vector(x1 - x0, y1 - y0, z1 - z0);
    }

    public static void log(String message) {
        try {
            message = "[AlienInvasion] &r" + message;
            sendConsole(message);
        } catch (Exception ex) {
            Logger logger = Bukkit.getLogger();
            logger.log(Level.INFO, removeColor("[AlienInvasion] " + message));
        }
    }

    private static void sendConsole(String message) {
        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
        console.sendMessage(addColor(message));
    }

    public static String addColor(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String removeColor(String message) {
        message = addColor(message);
        return ChatColor.stripColor(message);
    }
}