package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.gameAccessories.GameAccessoriesHandler;
import me.streafe.HubExtended.hub_commands.hub_handler;
import me.streafe.HubExtended.minigames.GameState;
import me.streafe.HubExtended.player_utils.*;
import me.streafe.HubExtended.utils.AnimatedScoreboard;
import me.streafe.HubExtended.utils.TextBuilder;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
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
        // Bukkit.getServer().broadcastMessage((utils.translate(HubExtended.getInstance().getConfig().getString("system.join_message")) + RankEnum.valueOf(hbConfigSetup.get("player.rank")).getPrefix() + " " + p.getName()));

        UpdatePlayerRun updatePlayerRun = new UpdatePlayerRun();

        updatePlayerRun.playerUpdateEveryLong(p,50L);

        AnimatedScoreboard animatedScoreboard = new AnimatedScoreboard(p);
        animatedScoreboard.animateText();
        animatedScoreboard.updateScoreBoard();

        PlayerRankUpdate rankUpdate = new PlayerRankUpdate(p);
        rankUpdate.updatePlayerRank();

        hub_handler hubH = new hub_handler();
        hubH.handleHubPlayers(p,20L);

        p.getInventory().setItem(1,Utils.getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTRkNDliYWU5NWM3OTBjM2IxZmY1YjJmMDEwNTJhNzE0ZDYxODU0ODFkNWIxYzg1OTMwYjNmOTlkMjMyMTY3NCJ9fX0=","&cSettings"));
        p.getInventory().setItem(0,utils.createNewItemWithMeta("&7Compass to send you","&7to other servers!", Material.COMPASS,"&cServer Switcher"));
        p.getInventory().setItem(2,utils.createNewItemWithMeta("&7Duel other noobs","&7choose your opponent", Material.DIAMOND_SWORD,"&cDuel Player &a(Left-click)"));

        HubExtended.getInstance().getSql_class().insertNewPlayerToSQL(p);
    }

    public static void updateOnlinePlayers(Player p){
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

        HubExtended.getInstance().addPlayerToList(new HubPlayer(p));


        hbConfigSetup = new HBConfigSetup(p);
        hbConfigSetup.setHubPlayer();


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

        player.getInventory().clear();

        if(HubExtended.getInstance().getHubPlayer(player.getUniqueId()) == null)return;
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

        event.setQuitMessage("");
        Bukkit.getServer().broadcastMessage((utils.translate(HubExtended.getInstance().getConfig().getString("system.leave_message")) + RankEnum.valueOf(hbConfigSetup.get("player.rank")).getPrefix() + " " + player.getName()));


        if(!hubPlayer.isInGame()){
            return;
        }



            for(HubPlayer partyPlayers : HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerList) {
                partyPlayers.sendMessage(utils.translate("&7" + hubPlayer.getName() + " &cleft the party"));
            }


            HubExtended.getInstance().getLogger().info(utils.translate("&cRemoved HubPlayer ") + player.getName());

            HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).removePlayer(player);
        HubExtended.getInstance().getHubPlayerList().remove(hubPlayer);
    }

    @EventHandler
    public void onPlayerHurt(EntityDamageEvent e){

        if(!(e.getEntity() instanceof Player)){
            return;

        }

        if(e.getCause() == EntityDamageEvent.DamageCause.FALL){
            e.setCancelled(true);
        }
    }

    /*
    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e){

        if(!(e.getEntity() instanceof Player)){
            return;
        }
        if(e.getEntity() instanceof Player){
            Player p = (Player) e.getEntity();
            HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

            if(!(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)){
                return;
            }

            if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).gameState == GameState.STARTED){
                    e.setCancelled(false);
                }

                }else{
                    e.getDamager().sendMessage(utils.translate("&c(!) &7No pvp here"));
                    e.setCancelled(true);
                }

            }
        }

     */



}
