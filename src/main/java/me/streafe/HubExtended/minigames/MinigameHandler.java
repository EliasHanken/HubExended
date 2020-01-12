package me.streafe.HubExtended.minigames;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class MinigameHandler implements Listener {

    Utils utils = new Utils();

    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e){

        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

            if(e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE){
                if(hubPlayer.inGame){
                    if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).gameState == GameState.STARTED){

                    }
                    else{
                        e.getDamager().sendMessage(utils.translate("&c(!) &7No pvp here"));
                        e.setCancelled(true);
                    }
                }else{
                    e.getDamager().sendMessage(utils.translate("&c(!) &7No pvp here"));
                    e.setCancelled(true);
                }

            }
        }
    }
}
