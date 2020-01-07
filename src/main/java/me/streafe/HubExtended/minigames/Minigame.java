package me.streafe.HubExtended.minigames;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Minigame implements Listener {

    private int maxPlayers;
    private String gameID;
    public List<HubPlayer> playerList;
    private Player owner;
    private MinigameType type;
    private boolean started = false;
    private Location spawnLoc;
    public int playerAmount;
    public MinigameType minigameType;


    public Minigame(int maxPlayers, MinigameType type){
        setRandomID();
        this.setPlayerList(new ArrayList<>());
        this.setType(type);
        this.setMaxPlayers(maxPlayers);
        this.minigameType = type;
    }

    public void startGame(){
        for(HubPlayer players : playerList){
            players.sendMessage("Game started!");
        }
    }

    public void addNewPlayer(Player player){
        this.getPlayerList().add(new HubPlayer(player));
        playerAmount++;
    }

    public void removePlayer(Player player){
        this.getPlayerList().remove(new HubPlayer(player));
    }

    public void setType(MinigameType type){
        this.type = type;
    }

    public void setOwner(Player player){
        this.owner = player;
    }

    public Player getOwner(){
        return this.owner;
    }

    private void setRandomID(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        this.setGameID(generatedString);
    }

    public String getGameID(){
        if(this.gameID != null){
            return this.gameID;
        }
        return null;
    }

    public List<HubPlayer> getPlayerList(){
        return playerList;
    }

    public List<String> getPlayerListString(){
        List<String> toRealName = new ArrayList<>();
        for(int i = 0; i < playerList.size(); i++){
            toRealName.add(playerList.get(i).getName());
        }
        return toRealName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setPlayerList(List<HubPlayer> playerList) {
        this.playerList = playerList;
    }

    public MinigameType getType() {
        return type;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

}
