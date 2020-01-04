package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MenuListener implements Listener {

    @EventHandler
    public void playerInventoryClick(InventoryClickEvent e){

        if(e.getInventory() == null ){
            return;
        }

        if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()){
            return;
        }

        Player p = (Player) e.getWhoClicked();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());
        if(e.getClickedInventory().getTitle().equalsIgnoreCase("settings")){
            if ((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("sign edit (disabled)"))){
                hubPlayer.signEditEnable = true;
                Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.LEVEL_UP, 1f,1f);
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            }else if ((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("sign edit (enabled)"))){
                hubPlayer.signEditEnable = false;
                Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.LEVEL_UP, 1f,1f);
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            }
        }else{
            return;
        }


    }
}
