package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.player_utils.RankEnum;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;


public class SpeakListener implements Listener {

    private Player player;
    private HubPlayer hubPlayer;
    private String message;

    Utils utils = new Utils();

    @EventHandler
    public void onPlayerSpeak(AsyncPlayerChatEvent e){
        e.setCancelled(true);
        this.message = e.getMessage();
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(e.getPlayer().getUniqueId());
        this.player = e.getPlayer();
        HBConfigSetup hbConfigSetup = new HBConfigSetup(e.getPlayer());
        if(hbConfigSetup.get("player.rank").equals("MEMBER")){
            Bukkit.getServer().broadcastMessage(utils.translate(this.player.getName() + ": ") + this.message);
        }
        else if(hbConfigSetup.get("player.rank").equals("VIP")){
            Bukkit.getServer().broadcastMessage(utils.translate("&eVIP &7" + this.player.getName() + ": &f" + this.message));
        }
        else if(hbConfigSetup.get("player.rank").equals("MODERATOR")){
            Bukkit.getServer().broadcastMessage(utils.translate("&3MOD &8" + this.player.getName() + ": &f" + this.message));
        }else if(hbConfigSetup.get("player.rank").equals("ADMIN")){
            Bukkit.getServer().broadcastMessage(utils.translate("&cADMIN &7" + this.player.getName() + ": &f" + this.message));
        }else if(hbConfigSetup.get("player.rank").equals("CO_OWNER")) {
            Bukkit.getServer().broadcastMessage(utils.translate("&dCO-OWNER &7" + this.player.getName() + ": &f" + this.message));
        } else if(hbConfigSetup.get("player.rank").equals("OWNER")){
            Bukkit.getServer().broadcastMessage(utils.translate("&4OWNER &7" + this.player.getName() + ": &f" + this.message));
        }
        else if(hbConfigSetup.get("player.rank").equals("DEVELOPER")){
            Bukkit.getServer().broadcastMessage(utils.translate("&4DEVELOPER &7" + this.player.getName() + ": &f" + this.message));
        }
    }
}
