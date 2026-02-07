package me.frxq.edpenchants.util;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.util.Location;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.frxq.edpenchants.EDPEnchants;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;

public class BlockUtils {
    public static boolean isBlockValid(Block block) {
        if (block == null || block.getType() == Material.AIR){
            return false;
        }
        Location loc = BukkitAdapter.adapt(block.getLocation());
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionQuery query = container.createQuery();
        ApplicableRegionSet set = query.getApplicableRegions(loc);
        Iterator<ProtectedRegion> regionsIt = set.getRegions().iterator();

        ProtectedRegion region;
        while (regionsIt.hasNext()) {
            region = regionsIt.next();
            String regionId = region.getId();

            if (regionId.startsWith("mine-") || regionId.startsWith("mine")) {
                return true;
            }
        }
        return false;
    }
    /**
     * Removes blocks in a radius around a specified block
     *
     * @param startBlock the block to start removing blocks from
     * @param radius     the radius to remove blocks in
     * @param effect     the effect to remove blocks in
     * @return the number of blocks removed
     */
    public static CompletableFuture<Integer> removeBlocksInRadius(Block startBlock, double radius, String effect) {
        CompletableFuture<Integer> future = new CompletableFuture<>();
        AtomicInteger blocksRemoved = new AtomicInteger(0);
        //

        new BukkitRunnable() {
            @Override
            public void run() {
                List<Block> blocksToRemove = new ArrayList<>();

                for (int x = -((int) radius); x <= radius; x++) {
                    for (int y = -((int) radius); y <= radius; y++) {
                        for (int z = -((int) radius); z <= radius; z++) {
                            Block block = startBlock.getRelative(x, y, z);
                            double distance = Math.sqrt(x * x + y * y + z * z);

                            if (effect.equalsIgnoreCase("SPHERE") && distance <= radius && isBlockValid(block)) {
                                blocksToRemove.add(block);
                            }
                        }
                    }
                }

                for (Block block : blocksToRemove) {
                    blocksRemoved.getAndAdd(1);
                    block.setType(Material.AIR);
                }

                future.complete(blocksRemoved.get());
            }
        }.runTask(EDPEnchants.getInstance());
        return future;
    }
}
