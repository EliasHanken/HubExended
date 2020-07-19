package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.player_utils.RankEnum;
import me.streafe.HubExtended.utils.TextBuilder;
import me.streafe.HubExtended.utils.Utils;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


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
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level + " &7"+this.player.getName() + ": ") + this.message);
        }
        else if(hbConfigSetup.get("player.rank").equals("VIP")){
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level+" &eVIP &7" + this.player.getName() + ": &f" + this.message));
        }
        else if(hbConfigSetup.get("player.rank").equals("ALIEN")){
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level+" &5ALIEN &7" + this.player.getName() + ": &f" + this.message));
        }
        else if(hbConfigSetup.get("player.rank").equals("ALIENPLUSS")){
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level+" &5ALIEN+ &7" + this.player.getName() + ": &f" + this.message));
        }
        else if(hbConfigSetup.get("player.rank").equals("MODERATOR")){
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level+" &3MOD &8" + this.player.getName() + ": &f" + this.message));
        }else if(hbConfigSetup.get("player.rank").equals("ADMIN")){
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level+" "+"&cADMIN &7" + this.player.getName() + ": &f" + this.message));
        }else if(hbConfigSetup.get("player.rank").equals("CO_OWNER")) {
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level+" "+"&dCO-OWNER &7" + this.player.getName() + ": &f" + this.message));
        } else if(hbConfigSetup.get("player.rank").equals("OWNER")){
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level+" "+"&4OWNER &7" + this.player.getName() + ": &f" + this.message));
        }
        else if(hbConfigSetup.get("player.rank").equals("DEVELOPER")){
            Bukkit.getServer().broadcastMessage(utils.translate("&a"+hubPlayer.level+" "+"&4DEVELOPER &7" + "&c&lS&e&lt&4&lr&2&le&5&la&1&lf&6&le" + ": &f" + this.message));
        }
    }
}
