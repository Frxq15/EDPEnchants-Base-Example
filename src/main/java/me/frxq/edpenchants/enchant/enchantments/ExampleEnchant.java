package me.frxq.edpenchants.enchant.enchantments;

import com.edwardbelt.edprison.enchantments.manager.obj.Enchantment;
import me.frxq.edpenchants.EDPEnchants;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ExampleEnchant extends Enchantment {
    public ExampleEnchant() {
        super("example", "BlockBreakEvent");
    }

    @Override
    public void execute(Player player, Block block) {
        Location loc = block.getLocation();
        loc.getWorld().createExplosion(loc, 105, true);
        new BukkitRunnable() {
            double t = 0.0;
            public void run() {
                this.t += 0.3141592653589793;
                for (double theta = 0.0; theta <= 6.283185307179586; theta += 0.39269908169872414) {
                    final double x = this.t * Math.cos(theta);
                    final double y = Math.exp(-0.1 * this.t) * Math.sin(this.t) + 1.5;
                    final double z = this.t * Math.sin(theta);
                    loc.add(x, y, z);
                    player.playEffect(loc, Effect.MOBSPAWNER_FLAMES, 1);
                    loc.subtract(z, y, z);
                }
                if (this.t > 20.0) {
                    this.cancel();
                }
            }
        }.runTaskTimer(EDPEnchants.getInstance(), 0L, 1L);
    }
}
