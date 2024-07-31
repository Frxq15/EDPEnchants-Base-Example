package me.frxq.edpenchants.enchant.enchantments;

import com.edwardbelt.edprison.enchantments.manager.obj.Enchantment;
import me.frxq.edpenchants.EDPEnchants;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class ExampleEnchant extends Enchantment {
    public ExampleEnchant() {
        super("example", "BlockBreakEvent");
    }

    @Override
    public void execute(Player player, Block block) {
        player.sendMessage(EDPEnchants.colourize("&dexample enchant triggered"));
    }
}
