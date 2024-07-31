package me.frxq.edpenchants.enchant;

import com.edwardbelt.edprison.EdPrison;
import me.frxq.edpenchants.EDPEnchants;
import me.frxq.edpenchants.enchant.enchantments.ExampleEnchant;

public class EnchantRegistry {
    public EDPEnchants plugin = EDPEnchants.getInstance();

    public EnchantRegistry(EDPEnchants plugin) {
        this.plugin = plugin;
    }

    public void register() {
        ExampleEnchant exampleEnchant = new ExampleEnchant();
    }
}
