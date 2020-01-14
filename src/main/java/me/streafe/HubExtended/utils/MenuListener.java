package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

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
            }else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("rank"))){
                e.setCancelled(true);
            }else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Game Settings"))){
                e.setCancelled(true);
                Menu menu = new Menu("Game Settings",9);
                menu.gameSettings(p);
            }
        }


        if(e.getClickedInventory().getTitle().equalsIgnoreCase("chest open")){
            if ((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("open chest (use 1 token)"))){
                if(hubPlayer.tokens <= 0){
                    Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.BAT_DEATH, 5f,1f);
                    e.setCancelled(true);
                    return;
                }
                hubPlayer.tokens = hubPlayer.tokens-1;
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.setHubPlayer();
                hbConfigSetup.editString("player.tokens",hbConfigSetup.getInt("player.tokens")-1);
                Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.LEVEL_UP, 5f,1f);
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();

                FireworkUtil fireworkUtil = new FireworkUtil(p,1);
                fireworkUtil.spawnFireWork();
            }
        }


    }
}
