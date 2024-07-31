package me.frxq.edpenchants;

import me.frxq.edpenchants.enchant.EnchantRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class EDPEnchants extends JavaPlugin {
    private static EDPEnchants instance;
    private static EnchantRegistry enchantRegistry;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        log("Enabling EDPEnchants..");
        registry();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {
        log("Disabling EDPEnchants..");
        // Plugin shutdown logic
    }
    public void log(String input) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD+"[Tracker] "+input);
    }
    public static String formatMsg(String input) {
        return ChatColor.translateAlternateColorCodes('&', getInstance().getConfig().getString(input));
    }
    public static String colourize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
    public static EDPEnchants getInstance() { return instance; }

    public void registry() {
        enchantRegistry = new EnchantRegistry(this);
        enchantRegistry.register();
    }
}
