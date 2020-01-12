package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.hub_commands.hub_handler;
import me.streafe.HubExtended.minigames.GameState;
import me.streafe.HubExtended.player_utils.*;
import me.streafe.HubExtended.utils.AnimatedScoreboard;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener implements Listener {

    Utils utils = new Utils();
    public static HBConfigSetup hbConfigSetup;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());



        HubExtended.getInstance().addPlayerToList(new HubPlayer(p));


        hbConfigSetup = new HBConfigSetup(p);
        hbConfigSetup.setHubPlayer();

        e.setJoinMessage("");
        Bukkit.getServer().broadcastMessage((utils.translate(HubExtended.getInstance().getConfig().get("system.join_message").toString()) + RankEnum.valueOf(hbConfigSetup.get("player.rank")).getPrefix() + " " + p.getName()));

        UpdatePlayerRun updatePlayerRun = new UpdatePlayerRun();

        updatePlayerRun.playerUpdateEveryLong(p,50L);

        AnimatedScoreboard animatedScoreboard = new AnimatedScoreboard(p);
        animatedScoreboard.animateText();
        animatedScoreboard.updateScoreBoard();

        PlayerRankUpdate rankUpdate = new PlayerRankUpdate(p);
        rankUpdate.updatePlayerRank();

        hub_handler hubH = new hub_handler();
        hubH.handleHubPlayers(p,20L);


    }

    public static HBConfigSetup getHbConfigSetup(){
        return hbConfigSetup;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

        event.setQuitMessage("");
        Bukkit.getServer().broadcastMessage((utils.translate(HubExtended.getInstance().getConfig().get("system.leave_message").toString()) + RankEnum.valueOf(hbConfigSetup.get("player.rank")).getPrefix() + " " + player.getName()));


        if(!hubPlayer.isInGame()){
            return;
        }

        if(hubPlayer.isInGame()){
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
            HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

            if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                if(hubPlayer.inGame){
                    if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).gameState == GameState.STARTED){
                    }else{
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
