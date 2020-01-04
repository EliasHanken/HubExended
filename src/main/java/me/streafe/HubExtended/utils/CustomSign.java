package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CustomSign implements Listener {

    Utils utils = new Utils();

    @EventHandler
    public void onSignPlace(SignChangeEvent e){
        Player p = e.getPlayer();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

        /*
        if(e.getClickedBlock().getType() != Material.WALL_SIGN){
            e.setCancelled(false);
            return;
        }

        if(e.getAction() != Action.LEFT_CLICK_BLOCK){
            e.setCancelled(false);
            return;
        }

         */

        //TODO CREATE AN CUSTOM SETTINGS MENU

        if(!hubPlayer.signEditEnable){
            return;
        }else{
            if(p.isOp()){
                for(int i = 0; i < 4;i++){
                    e.setLine(i,utils.translate(e.getLine(i)));
                }
            }
        }




    }
}
