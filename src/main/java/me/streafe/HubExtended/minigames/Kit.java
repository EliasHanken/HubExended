package me.streafe.HubExtended.minigames;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Kit {

    ItemStack oitcBow = new ItemStack(Material.BOW);
    ItemStack arrow = new ItemStack(Material.ARROW);

    void setOITCInventory(Player player);

}
