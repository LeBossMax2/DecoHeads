package me.rayzr522.decoheads.util;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * @author Rayzr522
 */
public class InventoryUtils {
    public static boolean canPlayerAfford(Player player, ItemStack costStack) {
    	return player.getInventory().containsAtLeast(costStack, costStack.getAmount());
    }

    public static void withdrawPlayer(Player player, ItemStack costStack) {
    	costStack = costStack.clone();
    	for (ItemStack invStack : player.getInventory().getContents())
        {
            if (!costStack.isSimilar(invStack))
            	continue;

            int inv = invStack.getAmount();
            int cost = costStack.getAmount();
            if (cost - inv >= 0)
            {
                costStack.setAmount(cost - inv);
                player.getInventory().remove(invStack);
            }
            else
            {
                costStack.setAmount(0);
                invStack.setAmount(inv - cost);
                break;
            }
        }
    }

    public static boolean giveItem(Player player, ItemStack itemStack) {
    	itemStack = itemStack.clone();
    	int maxStackSize = Math.min(player.getInventory().getMaxStackSize(), itemStack.getMaxStackSize());
    	for (ItemStack invStack : player.getInventory().getContents())
        {
            if (!itemStack.isSimilar(invStack))
            	if (!CustomHead.isSkullSimilar(itemStack, invStack))
            		continue;

            int inv = invStack.getAmount();
            int toAdd = itemStack.getAmount();
            if (inv + toAdd > maxStackSize)
            {
            	itemStack.setAmount(inv + toAdd - maxStackSize);
                invStack.setAmount(maxStackSize);
            }
            else
            {
            	itemStack.setAmount(0);
                invStack.setAmount(inv + toAdd);
                return true;
            }
        }
    	return player.getInventory().addItem(itemStack).isEmpty();
    }
}
