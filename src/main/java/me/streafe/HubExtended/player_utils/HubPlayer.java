package me.streafe.HubExtended.player_utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.gameAccessories.KillEffects;
import me.streafe.HubExtended.minigames.Minigame;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HubPlayer {

    public Player player;
    public boolean inGame;
    public String gameID;
    public boolean signEditEnable = false;
    public boolean superVanish = false;
    public int tokens;
    public boolean isOnCooldown = false;
    public RankEnum rank = RankEnum.MEMBER;
    public List<String> friends;
    public int wins;
    public double level;
    public String inventory;
    public int gamePoints = 0;
    public KillEffects killEffect;

    public HubPlayer(Player player){
        this.player = player;
        friends = new ArrayList<>();
    }


    public boolean inGame(HubPlayer hubPlayer){
        if(this.isInGame()){
            return true;
        }
        return false;
    }

    public void setRank(RankEnum rank){
        this.rank = rank;
    }

    public void sendMessage(String message){
        this.player.sendMessage(message);
    }

    public Minigame getGame(){
        if(HubExtended.getInstance().getMinigameByID(gameID) == null){
            return null;
        }

        else if(HubExtended.getInstance().getMinigameByID(gameID) != null){
            return HubExtended.getInstance().getMinigameByID(gameID);
        }

        return null;

    }

    public void setSignEditEnable(boolean bool){
        this.signEditEnable = bool;
    }

    public int getTokens(){
        return this.tokens;
    }

    public void addTokens(int tokens){
        this.tokens+=tokens;
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

    public Player getPlayer(){
        return this.player;
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
