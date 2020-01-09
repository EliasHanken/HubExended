package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.player_utils.PlayerRankUpdate;
import me.streafe.HubExtended.player_utils.UpdatePlayerRun;
import me.streafe.HubExtended.utils.AnimatedScoreboard;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    Utils utils = new Utils();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

        HubExtended.getInstance().addPlayerToList(new HubPlayer(p));

        HBConfigSetup hbConfigSetup = new HBConfigSetup(p);

        UpdatePlayerRun updatePlayerRun = new UpdatePlayerRun();

        updatePlayerRun.playerUpdateEveryLong(p,50L);

        AnimatedScoreboard animatedScoreboard = new AnimatedScoreboard(p);
        animatedScoreboard.animateText();
        animatedScoreboard.updateScoreBoard();

        PlayerRankUpdate rankUpdate = new PlayerRankUpdate(p);
        rankUpdate.updatePlayerRank();

    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

        if(hubPlayer.inGame == false){
            return;
        }

        if(hubPlayer.inGame){
            HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).removePlayer(player);

            for(HubPlayer partyPlayers : HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerList){
                partyPlayers.sendMessage(utils.translate("&7" + hubPlayer.getName() + " &cleft the party"));
            }
        }
    }

    @EventHandler
    public void onPlayerHurt(EntityDamageEvent e){

        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();

            if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e){

        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();

            if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                e.getDamager().sendMessage(utils.translate("&c(!) &7No pvp here"));
                e.setCancelled(true);
            }
        }
    }


}
