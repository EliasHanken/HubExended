package me.streafe.HubExtended.player_utils;

import me.streafe.HubExtended.HubExtended;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

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

        if(!playerFile.exists()){
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
            yaml.set("player.name",player.getName());
            yaml.set("player.tokens",hubPlayer.tokens);
            if(hubPlayer.rank == RankEnum.MEMBER){
                yaml.set("player.rank","MEMBER");
            }else if(hubPlayer.rank == RankEnum.MODERATOR){
                yaml.set("player.rank","MODERATOR");
            }else if(hubPlayer.rank == RankEnum.CO_OWNER){
                yaml.set("player.rank","CO_OWNER");
            }else if(hubPlayer.rank == RankEnum.OWNER){
                yaml.set("player.rank","OWNER");
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
