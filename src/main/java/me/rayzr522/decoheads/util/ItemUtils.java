package me.rayzr522.decoheads.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ItemUtils {
    public static boolean isInvalid(ItemStack item) {
        return item == null || item.getType() == Material.AIR;
    }

    public static ItemStack setName(ItemStack item, String name) {
        if (isInvalid(item)) {
            return item;
        }

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(TextUtils.colorize(name));
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack setLore(ItemStack item, String... lore) {
        if (isInvalid(item) || lore == null) {
            return item;
        }

        ItemMeta meta = item.getItemMeta();
        List<String> loreList = Arrays.stream(lore)
                .map(TextUtils::colorize)
                .collect(Collectors.toList());

        meta.setLore(loreList);
        item.setItemMeta(meta);

        return item;
    }
    
    public static ItemStack parseStack(String txt)
    {
    	String[] parts = txt.trim().split(" ");
    	if (parts.length < 0 || parts.length > 2)
    		return null;
    	
    	Material item = Material.matchMaterial(parts[0]);
    	int count = 1;
    	if (parts.length >= 2)
    	{
    		count = Integer.parseInt(parts[2]);
    	}
    	return new ItemStack(item, count);
    }

}
