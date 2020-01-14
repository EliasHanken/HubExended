package me.streafe.HubExtended;

import me.streafe.HubExtended.bungee.BungeeConnect;
import me.streafe.HubExtended.bungee.BungeeMessage;
import me.streafe.HubExtended.bungee.BungeeMessageListener;
import me.streafe.HubExtended.hub_commands.AllowedCommandsPerRank;
import me.streafe.HubExtended.hub_commands.lucky_chest_command;
import me.streafe.HubExtended.hub_commands.menu_command;
import me.streafe.HubExtended.hub_listeners.BlockListener;
import me.streafe.HubExtended.hub_listeners.RespawnListener;
import me.streafe.HubExtended.hub_listeners.JoinListener;
import me.streafe.HubExtended.hub_listeners.SpeakListener;
import me.streafe.HubExtended.minigames.Minigame;
import me.streafe.HubExtended.minigames.MinigameCommand;
import me.streafe.HubExtended.minigames.MinigameHandler;
import me.streafe.HubExtended.minigames.OITCCommand;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.sql.SQL_Class;
import me.streafe.HubExtended.utils.CustomSign;
import me.streafe.HubExtended.utils.ItemContent;
import me.streafe.HubExtended.utils.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;

import java.sql.SQLException;
import java.util.*;

public class HubExtended extends JavaPlugin {

    private static HubExtended instance;
    private String host, usr, pw, dbn;
    private int port;
    private SQL_Class sql_class;
    public Map<String, Minigame> minigameHashMap;
    private Map<UUID,HubPlayer> hubPlayerList;
    private Scoreboard mainScoreboard;

    @Override
    public void onDisable(){
        for(Player playersOnline : getServer().getOnlinePlayers()){
            HBConfigSetup hbConfigSetup = new HBConfigSetup(playersOnline);
            ItemContent itemContent = new ItemContent(playersOnline);

            itemContent.savePlayerInventory(playersOnline);

            playersOnline.getInventory().clear();
        }
    }

    @Override
    public void onEnable(){
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("connect").setExecutor(new BungeeConnect());
        getCommand("msg").setExecutor(new BungeeMessage());
        getCommand("hub").setExecutor(new BungeeConnect());
        getCommand("sethub").setExecutor(new BungeeConnect());

        this.mainScoreboard = this.getServer().getScoreboardManager().getNewScoreboard();



        this.host = getConfig().get("sql.host").toString();
        this.usr = getConfig().get("sql.username").toString();
        this.pw = getConfig().get("sql.password").toString();
        this.dbn = getConfig().get("sql.database").toString();
        this.port = getConfig().getInt("sql.port");

        getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageListener());


        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new CustomSign(),this);
        getServer().getPluginManager().registerEvents(new MenuListener(),this);
        getServer().getPluginManager().registerEvents(new RespawnListener(),this);
        getServer().getPluginManager().registerEvents(new BlockListener(),this);
        getServer().getPluginManager().registerEvents(new SpeakListener(),this);
        getServer().getPluginManager().registerEvents(new Minigame(),this);

        getCommand("minigames").setExecutor(new MinigameCommand());
        getCommand("settings").setExecutor(new menu_command());
        getCommand("luckychest").setExecutor(new lucky_chest_command());

        getCommand("help").setExecutor(new AllowedCommandsPerRank());
        getCommand("oitc").setExecutor(new OITCCommand());



        this.minigameHashMap = new HashMap<>();
        this.hubPlayerList = new HashMap<>();

        this.sql_class = new SQL_Class(host,port,usr,pw,dbn);
        try {
            sql_class.connect();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        for(Player playersOnline : getServer().getOnlinePlayers()){
            try{
                JoinListener.updateOnlinePlayers(playersOnline);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        for(Player playersOnline : getServer().getOnlinePlayers()){
            HBConfigSetup hbConfigSetup = new HBConfigSetup(playersOnline);
            ItemContent itemContent = new ItemContent(playersOnline);

            playersOnline.getInventory().setContents(itemContent.getSavedPlayerInventory(hbConfigSetup.get("player.inventory")));
        }
    }

    public Location getHubLocation(){
        double x = HubExtended.getInstance().getConfig().getDouble("world.hubX");
        double y = HubExtended.getInstance().getConfig().getDouble("world.hubY");
        double z = HubExtended.getInstance().getConfig().getDouble("world.hubZ");
        float pitch = (float) HubExtended.getInstance().getConfig().getDouble("world.hubPitch");
        float yaw = (float) HubExtended.getInstance().getConfig().getDouble("world.hubYaw");
        Location hubLoc = new Location(Bukkit.getWorld("world"),x,y,z,yaw,pitch);

        return hubLoc;
    }

    public Location getLobbyLocation(){
        double x = getConfig().getDouble("minigames.lobbySpawn.X");
        double y = getConfig().getDouble("minigames.lobbySpawn.Y");
        double z = getConfig().getDouble("minigames.lobbySpawn.Z");
        float pitch = (float) getConfig().getDouble("minigames.lobbySpawn.Pitch");
        float yaw = (float) getConfig().getDouble("minigames.lobbySpawn.Yaw");
        Location loc = new Location(getServer().getWorld("world"),x,y,z,yaw,pitch);

        return loc;
    }

    public static HubExtended getInstance(){
        return instance;
    }

    public Minigame getMinigameByID(String id){
        return this.minigameHashMap.get(id);
    }


    public Map<UUID,HubPlayer> getHubPlayerList() {
        return hubPlayerList;
    }

    public void addPlayerToList(HubPlayer hubPlayer) {
        this.hubPlayerList.put(hubPlayer.getUUID(),hubPlayer);
    }

    public HubPlayer getHubPlayer(UUID uuid){
        return this.hubPlayerList.get(uuid);
    }

    public Scoreboard getMainScoreboard() {
        return mainScoreboard;
    }
}
