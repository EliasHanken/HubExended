package me.streafe.HubExtended.player_utils;

import me.streafe.HubExtended.HubExtended;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class HubPlayer {

    private Player player;
    public boolean inGame = false;
    private String gameID;
    public boolean signEditEnable = false;
    public boolean superVanish = false;

    public HubPlayer(Player player){
        this.player = player;
    }


    public boolean inGame(HubPlayer hubPlayer){
        if(this.inGame){
            return true;
        }
        return false;
    }

    public void sendMessage(String message){
        this.player.sendMessage(message);
    }

    public void setSignEditEnable(boolean bool){
        this.signEditEnable = bool;
    }

    public boolean isOwnerOfGame(){
        if(HubExtended.getInstance().minigameHashMap.containsKey(getGameID())){
            return true;
        }

        return false;
    }


    public String getGameID() {
        return this.gameID;
    }

    public void setGameID(String id) {
        gameID = id;
    }

    public UUID getUUID(){
        return this.player.getUniqueId();
    }

    public String getName(){
        return Bukkit.getPlayer(getUUID()).getName();
    }
}
