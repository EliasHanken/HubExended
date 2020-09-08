package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.sql.SQL_Class;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class ShopSignsListener implements Listener {


    @EventHandler
    public void onPlayerClickShopSign(PlayerInteractEvent e){
        Player p = (Player) e.getPlayer();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
            if(e.getClickedBlock().getType() == Material.WALL_SIGN){
                Sign s = (Sign) e.getClickedBlock().getState();

                double tokens = HubExtended.getInstance().getHubPlayer(p.getUniqueId()).tokens;

                int amount = Integer.parseInt(s.getLine(1).replace('x','0'));
                int price = Integer.parseInt(s.getLine(2));
                String itemName = s.getLine(0);

                if(HubExtended.getInstance().getSql_class().alreadyHasBoughtItemOnce(p,ShopEntities.valueOf(itemName))){
                    p.sendMessage(" ");
                    p.sendMessage(Utils.translateInnerclass("&cError: &7This item is already in your survival chest\n Make sure to take it out of there before buying it again\n(will be fixed soon)"));
                    p.sendMessage(" ");
                    return;
                }

                if(tokens >= price){
                    p.sendMessage(" ");
                    p.sendMessage(Utils.translateInnerclass("&c(-" + price + ") &7tokens"));
                    p.sendMessage(Utils.translateInnerclass("&aYou bought " + itemName + " &7for " + price + " &7tokens"));
                    p.sendMessage(" ");

                    /*
                        Adds item to sql and remove coins

                     */
                    HubExtended.getInstance().getSql_class().updateItemToPlayerChest(p,ShopEntities.valueOf(itemName),amount,price);

                    p.playSound(p.getLocation(), Sound.CLICK,1f,1f);
                }else{
                    p.sendMessage(Utils.translateInnerclass("&cYou have insufficient tokens to buy this item!"));
                    p.playSound(p.getLocation(), Sound.CAT_MEOW,1f,1f);
                }

            }
        }
    }
}
