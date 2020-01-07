package me.streafe.HubExtended.player_utils;

import me.streafe.HubExtended.HubExtended;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

public class HBConfigSetup {

    private HubPlayer hubPlayer;
    private Player player;
    private File playerFile;

    public HBConfigSetup(Player player){
        this.player = player;
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(this.player.getUniqueId());

        setHubPlayer();
    }

    public void setHubPlayer(){
        File playersFolder = new File(HubExtended.getInstance().getDataFolder() + "/" + "PlayerFolder");
        if(!playersFolder.exists()){
            playersFolder.mkdir();
        }
        playerFile = new File(HubExtended.getInstance().getDataFolder() +"/PlayerFolder/"+ player.getUniqueId().toString());

        /*
        if(!playerFile.exists()){
            Date joinDate = new Date();
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
            yaml.set("player.name", player.getName());
            yaml.set("player.tokens",3);
            yaml.set("player.joinDate",joinDate.toString());
            yaml.set("player.friends", Arrays.asList("Streafe", "atob"));
            yaml.set("player.wins",0);
            if(!player.getName().equalsIgnoreCase("Streafe")){
                yaml.set("player.rank","MEMBER");
            }else{
                yaml.set("player.rank","OWNER");
            }

            yaml.options().copyDefaults(true);
            try {
                yaml.save(playerFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         */


        if(!playerFile.exists()){
            String joinDate = new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis());
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
            yaml.set("player.name",player.getName());
            yaml.set("player.tokens",hubPlayer.tokens);
            yaml.set("player.tokens",3);
            yaml.set("player.joinDate",joinDate);
            yaml.set("player.friends", Arrays.asList("Streafe", "atob"));
            yaml.set("player.wins",0);
            if(hubPlayer.rank == RankEnum.MEMBER){
                yaml.set("player.rank","MEMBER");
            }else if(hubPlayer.rank == RankEnum.MODERATOR){
                yaml.set("player.rank","MODERATOR");
            }else if(hubPlayer.rank == RankEnum.CO_OWNER){
                yaml.set("player.rank","CO_OWNER");
            }else if(hubPlayer.rank == RankEnum.OWNER){
                yaml.set("player.rank","OWNER");
            }else if(hubPlayer.rank == RankEnum.DEVELOPER){
                yaml.set("player.rank","DEVELOPER");
            }
            yaml.options().copyDefaults(true);
            try {
                yaml.save(playerFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(playerFile.exists()){
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
            yaml.set("player.name",player.getName());
            hubPlayer.tokens = yaml.getInt("player.tokens");
            hubPlayer.friends = Arrays.asList(yaml.getString("player.friends"));
            hubPlayer.wins = yaml.getInt("player.wins");
            if(yaml.get("player.rank").equals("MEMBER")){
                hubPlayer.setRank(RankEnum.MEMBER);
            }else if(yaml.get("player.rank").equals("MODERATOR")){
                hubPlayer.setRank(RankEnum.MODERATOR);
            }else if(yaml.get("player.rank").equals("ADMIN")){
                hubPlayer.setRank(RankEnum.ADMIN);
            }else if(yaml.get("player.rank").equals("CO_OWNER")) {
                hubPlayer.setRank(RankEnum.CO_OWNER);
            } else if(yaml.get("player.rank").equals("OWNER")){
                hubPlayer.setRank(RankEnum.OWNER);
            }else if(hubPlayer.rank == RankEnum.DEVELOPER){
                yaml.set("player.rank","DEVELOPER");
            }
            yaml.options().copyDefaults(true);
            try {
                yaml.save(playerFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void editString(String string,int value){
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
        yaml.set(string,value);
        try {
            yaml.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getInt(String string){
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
        return yaml.getInt(string);
    }

    public String get(String string){
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
        return yaml.getString(string);
    }


}
