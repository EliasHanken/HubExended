package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener {
    Utils utils = new Utils();

    @EventHandler
    public void playerBlockBreak(BlockBreakEvent e){
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("world"))return;
        if(!e.getPlayer().isOp()){
            Player player = e.getPlayer();
            player.sendMessage(utils.translate("&cYou can't break blocks in the hub"));
            e.setCancelled(true);

        }
    }
    @EventHandler
    public void playerBlockBreak(BlockPlaceEvent e){
        if(!e.getPlayer().getWorld().getName().equalsIgnoreCase("world"))return;
        if(!e.getPlayer().isOp()){
            Player player = e.getPlayer();
            player.sendMessage(utils.translate("&cYou can't place blocks in the hub"));
            e.setCancelled(true);

        }
    }
}
