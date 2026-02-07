package me.frxq.edpenchants.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class RegionUtils {
    /**
     * Gets a random location in a region
     * @param world the world of the region
     * @param minPoints the minimum points of the region
     * @param maxPoints the maximum points of the region
     * @param yLevel the y level of the region
     * @return the random location in the region
     */
    public static Location getRandomLocationInBounds(World world, Vector minPoints, Vector maxPoints, int yLevel) {
        Random random = new Random();

        double x = minPoints.getX() + (random.nextDouble() * (maxPoints.getX() - minPoints.getX()));
        double z = minPoints.getZ() + (random.nextDouble() * (maxPoints.getZ() - minPoints.getZ()));

        return new Location(world, x, yLevel, z);
    }
    /**
     * Gets the players within a radius of a location
     * @param location the location to get the players within
     * @param radius the radius to get the players within
     * @return the players within the radius
     */
    public static List<UUID> getPlayersWithinRadius(Location location, double radius) {
        List<UUID> players = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getWorld().equals(location.getWorld()) && player.getLocation().distance(location) <= radius) {
                players.add(player.getUniqueId());
            }
        }
        return players;
    }
    /**
     * Finds the middle of a mine
     * @param max_points the max points of the mine
     * @param min_points the min points of the mine
     * @param world the world of the mine
     * @return the middle of the mine
     */
    public static Location findMiddleOfMine(Vector max_points, Vector min_points, World world) {
        double x = (max_points.getX() + min_points.getX()) / 2;
        double z = (max_points.getZ() + min_points.getZ()) / 2;
        return new Location(world, x, max_points.getY() + 7, z);
    }

    /**
     * Gets a random nearby block location
     * @param block the block to get a random nearby location from
     * @return the random nearby block location
     */
    public static Location getRandomNearbyBlockLocation(Block block) {
        Location randomBlockLocation = block.getLocation().clone().add(
                (Math.random() * 3) - 1.5,
                (Math.random() * 3) - 1.5,
                (Math.random() * 3) - 1.5
        );

        while (!BlockUtils.isBlockValid(randomBlockLocation.getBlock()) && !randomBlockLocation.equals(block.getLocation())) {
            randomBlockLocation = block.getLocation().clone().add(
                    (Math.random() * 3) - 1.5,
                    (Math.random() * 3) - 1.5,
                    (Math.random() * 3) - 1.5
            );
        }
        int roundedX = randomBlockLocation.getBlockX();
        int roundedY = randomBlockLocation.getBlockY();
        int roundedZ = randomBlockLocation.getBlockZ();

        return new Location(block.getWorld(), roundedX, roundedY, roundedZ);
    }
}
