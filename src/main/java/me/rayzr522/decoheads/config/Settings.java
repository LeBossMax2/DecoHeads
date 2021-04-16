package me.rayzr522.decoheads.config;

import me.rayzr522.decoheads.DecoHeads;
import me.rayzr522.decoheads.util.ConfigVersionChecker;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class Settings {

    /**
     * The version to check in the config
     */
    private static final int CONFIG_VERSION = 1;

    private final DecoHeads plugin;
    private YamlConfiguration config;

    public Settings(DecoHeads plugin) {
        this.plugin = plugin;
    }

    public void load() throws IOException {
        config = ConfigVersionChecker.updateConfig("settings.yml", CONFIG_VERSION);
    }

    public boolean save() {
        try {
            plugin.getConfigHandler().saveConfig("settings.yml", config);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isPriceEnabled() {
        // Ensure price is not enabled when the price wrapper is not available
        return plugin.getEconomy() != null && config.getBoolean("price.enabled");
    }

    public void setPriceEnabled(boolean priceEnabled) {
        // Ensure price is not enabled when the price wrapper is not available
        config.set("price.enabled", plugin.getEconomy() != null && priceEnabled);
    }

    public boolean shouldShowFreeHeads() {
        return config.getBoolean("price.show-free-heads");
    }

    public void setShowFreeHeads(boolean showFreeHeads) {
        config.set("price.show-free-heads", showFreeHeads);
    }

    public ItemStack getDefaultHeadCost() {
        return config.getItemStack("price.default-cost", new ItemStack(Material.DIAMOND));
    }

    public void setDefaultHeadCost(ItemStack headCost) {
        config.set("price.default-cost", headCost);
    }

    public boolean isCustomHeadsEnabled() {
        return config.getBoolean("custom-heads.enabled");
    }

    public void setCustomHeadsEnabled(boolean customHeadsEnabled) {
        config.set("custom-heads.enabled", customHeadsEnabled);
    }

    public ItemStack getCustomHeadsCost() {
        return config.getItemStack("custom-heads.cost");
    }

    public void setCustomHeadsCost(ItemStack customHeadsCost) {
        config.set("custom-heads.cost", customHeadsCost);
    }

    public boolean isUpdaterEnabled() {
        return config.getBoolean("updater-enabled");
    }

    public void setUpdaterEnabled(boolean updaterEnabled) {
        config.set("updater-enabled", updaterEnabled);
    }
}
