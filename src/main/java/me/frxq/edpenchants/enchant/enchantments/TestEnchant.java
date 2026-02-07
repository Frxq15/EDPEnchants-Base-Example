package me.frxq.edpenchants.enchant.enchantments;

import com.edwardbelt.edprison.enchantments.manager.obj.Enchantment;
import me.frxq.edpenchants.EDPEnchants;
import me.frxq.edpenchants.util.BlockUtils;
import me.frxq.edpenchants.util.RegionUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EnderCrystal;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class TestEnchant extends Enchantment {
    private EDPEnchants plugin;
    private BlockUtils blockUtils;
    private RegionUtils regionUtils;
    public TestEnchant(EDPEnchants plugin) {
        super("test", "BlockBreakEvent");
        this.plugin = plugin;
        this.blockUtils = plugin.getBlockUtils();
        this.regionUtils = plugin.getRegionUtils();
    }

    @Override
    public void execute(Player player, Block block) {
        Location loc = block.getLocation();

        List<EnderCrystal> crystals = new ArrayList<>();

        // Spawn Ender Crystals as a visual effect
        for (int i = 0; i < 3; i++) {
            Location crystalLoc = RegionUtils.getRandomNearbyBlockLocation(block);
            EnderCrystal crystal = (EnderCrystal) player.getWorld().spawnEntity(crystalLoc, EntityType.ENDER_CRYSTAL);
            crystals.add(crystal);

            // Add some visual particles around the crystal
            player.getWorld().spawnParticle(Particle.END_ROD, crystalLoc, 20, 0.5, 0.5, 0.5, 0.05);
        }

        // Schedule explosion and block removal after 5 seconds
        Bukkit.getScheduler().runTaskLater(EDPEnchants.getInstance(), () -> {
            int totalBlocksDestroyed = 0;

            for (EnderCrystal crystal : crystals) {
                if (crystal.isValid()) {
                    Location explosionLoc = crystal.getLocation();

                    // Create explosion effect
                    crystal.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, explosionLoc, 1);
                    crystal.getWorld().playSound(explosionLoc, Sound.ENTITY_GENERIC_EXPLODE, 1, 1);

                    // Remove blocks in the explosion radius
                    CompletableFuture<Integer> removedBlocks = BlockUtils.removeBlocksInRadius(
                            explosionLoc.getBlock(),
                            3, // Explosion radius
                            "SPHERE"
                    );

                    // Count the destroyed blocks
                    try {
                        totalBlocksDestroyed += removedBlocks.get(); // Wait for the async task
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    // Remove the crystal entity
                    crystal.remove();
                }
            }

            // Notify the player
            player.sendMessage("Crystal explosions destroyed a total of " + totalBlocksDestroyed + " blocks!");
        }, 100L); // 5-second delay
    }

}
