package me.streafe.HubExtended.player_utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.gameAccessories.KillEffects;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class HBConfigSetup {

    private HubPlayer hubPlayer;
    private Player player;
    private File playerFile;

    Utils utils = new Utils();

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
            yaml.set("player.level",0.0);
            yaml.set("player.inventory", "");
            yaml.set("player.gameAccessories.killEffects","");
            yaml.set("player.killEffectInUse","NONE");
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

            hubPlayer.level = yaml.getDouble("player.level");
            hubPlayer.inventory = yaml.getString("player.inventory");
            hubPlayer.killEffect = KillEffects.valueOf(yaml.getString("player.killEffectInUse").toUpperCase());

            if(yaml.get("player.rank").equals("MEMBER")){
                hubPlayer.setRank(RankEnum.MEMBER);
            }else if(yaml.get("player.rank").equals("VIP")){
                hubPlayer.setRank(RankEnum.VIP);
            } else if(yaml.get("player.rank").equals("MODERATOR")){
                hubPlayer.setRank(RankEnum.MODERATOR);
            }else if(yaml.get("player.rank").equals("ADMIN")){
                hubPlayer.setRank(RankEnum.ADMIN);
            }else if(yaml.get("player.rank").equals("CO_OWNER")) {
                hubPlayer.setRank(RankEnum.CO_OWNER);
            } else if(yaml.get("player.rank").equals("OWNER")){
                hubPlayer.setRank(RankEnum.OWNER);
            }else if(yaml.get("player.rank").equals("DEVELOPER")){
                hubPlayer.setRank(RankEnum.DEVELOPER);
            }
            yaml.options().copyDefaults(true);
            try {
                yaml.save(playerFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public void ifExists(){
        String joinDate = new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis());
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
        yaml.set("player.name",player.getName());
        yaml.set("player.tokens",hubPlayer.tokens);
        yaml.set("player.tokens",3);
        yaml.set("player.joinDate",joinDate);
        yaml.set("player.friends", Arrays.asList("Streafe", "atob"));
        yaml.set("player.wins",0);
        yaml.set("player.experience",0.0);
        if(hubPlayer.rank == RankEnum.MEMBER){
            yaml.set("player.rank","MEMBER");
        }else if(hubPlayer.rank == RankEnum.VIP){
            yaml.set("player.rank","VIP");
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

    public void ifNotExists() {
        String joinDate = new SimpleDateFormat("dd-MM-yyyy").format(System.currentTimeMillis());
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
        yaml.set("player.name", player.getName());
        yaml.set("player.tokens", hubPlayer.tokens);
        yaml.set("player.tokens", 3);
        yaml.set("player.joinDate", joinDate);
        yaml.set("player.friends", Arrays.asList("Streafe", "atob"));
        yaml.set("player.wins", 0);
        yaml.set("player.experience", 0.0);
        yaml.set("player.inventory", "");
        yaml.set("player.gameAccessories.killEffects.inUse","");
        if (hubPlayer.rank == RankEnum.MEMBER) {
            yaml.set("player.rank", "MEMBER");
        } else if (hubPlayer.rank == RankEnum.VIP) {
            yaml.set("player.rank", "VIP");
        }else if (hubPlayer.rank == RankEnum.MODERATOR) {
            yaml.set("player.rank", "MODERATOR");
        } else if (hubPlayer.rank == RankEnum.CO_OWNER) {
            yaml.set("player.rank", "CO_OWNER");
        } else if (hubPlayer.rank == RankEnum.OWNER) {
            yaml.set("player.rank", "OWNER");
        } else if (hubPlayer.rank == RankEnum.DEVELOPER) {
            yaml.set("player.rank", "DEVELOPER");
        }
        yaml.options().copyDefaults(true);
        try {
            yaml.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
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



    public void addKillEffects(ArrayList<KillEffects> killList){
        List<String> list = new ArrayList<>();

        for(KillEffects killEffects : killList){

            String addString = killEffects.getName() + "." + killEffects.getRarity() + ":";
            list.add(addString);

        }

        if(get("player.gameAccessories.killEffects") == null){
            editString("player.gameAccessories.killEffects",list);
        }else{
            editString("player.gameAccessories.killEffects",get("player.gameAccessories.killEffects") + list);
        }

    }

    public void addKillEffects(KillEffects killEffects){

        String addString = killEffects.getName() + ":";

        if(get("player.gameAccessories.killEffects") == null){
            editString("player.gameAccessories.killEffects",addString);
        }else{
            editString("player.gameAccessories.killEffects",get("player.gameAccessories.killEffects") + addString);
        }

    }

    public List<ItemStack> getAllKillEffectsItems(){
        if(get("player.gameAccessories.killEffects").equalsIgnoreCase("")){
            return null;
        }
        List<ItemStack> effects = new ArrayList<>();

        try{
            String[] l = get("player.gameAccessories.killEffects").split(":");

            for(int i = 0; i < l.length;i++){
                effects.add(getKillEffectItem(KillEffects.valueOf(l[i].toUpperCase())));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return effects;

    }

    public int getKillEffectSize(){
        String[] l = get("player.gameAccessories.killEffects").split(":");

        return l.length;
    }



    public ItemStack getKillEffectItem(KillEffects killEffects){
        if(killEffects == KillEffects.HEADROCKET){
            return Utils.getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTNhOTZjYzI5MDMxNTJlM2VlNTU4NDJhYmYyMzkxMzc5ODBhZmY3NjEwOTAwNGIzYmQ3YTVjODU0OGNlIn19fQ==","&6&lLEGENDARY &7&cHead Rocket");
        }else if(killEffects == KillEffects.PARTYEXPLOSION){
            return utils.createItem("&bCOMMON &7&oParty Explosion", Material.FIREWORK);
        }else if(killEffects == KillEffects.BLOODEXPLOSION){
            return Utils.getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODk4OWRjOTY4MWI2Y2Y5ZDNiNGE4ZTJjNTU0ZWExYmNlNjMyNjc5ZDMxOTZhNDU0MDgzNWFjOGQzYWQzMTIifX19","&d&lEPIC &c&oBlood Explosion");
        }else if(killEffects == KillEffects.FINALSMASH){
            return utils.createItem("&6&lLEGENDARY &b&oFinal Smash",Material.ARMOR_STAND);
        }else if(killEffects == KillEffects.NONE){
            return utils.createItem("&7NONE", Material.GLASS);
        }
        return null;
    }

    public static ArrayList<Location> getLocations(){
        ArrayList<Location> locsList = new ArrayList<>();
        try{
            List<String> locs = HubExtended.getInstance().getConfig().getStringList("minigames.oitc.respawnP");
            for(String s : locs) {
                String[] l = s.split(":");
                Location newL = new Location(Bukkit.getWorld(l[0]), Double.parseDouble(l[1]), Double.parseDouble(l[2]), Double.parseDouble(l[3]));
                locsList.add(newL);
            }
        } catch(Exception e){}
        return locsList;
    }

    public void editString(String string,String value){
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
        yaml.set(string,value);
        try {
            yaml.save(playerFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void editString(String string,List<String> value){
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

    public double getDouble(String string){
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
        return yaml.getDouble(string);
    }

    public String get(String string){
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(playerFile);
        return yaml.getString(string);
    }


}
