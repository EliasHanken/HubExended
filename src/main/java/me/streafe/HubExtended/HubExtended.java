package me.streafe.HubExtended;

import com.avaje.ebean.RawSql;
import me.streafe.HubExtended.bungee.BungeeConnect;
import me.streafe.HubExtended.bungee.BungeeMessage;
import me.streafe.HubExtended.bungee.BungeeMessageListener;
import me.streafe.HubExtended.hub_listeners.JoinListener;
import me.streafe.HubExtended.minigames.Minigame;
import me.streafe.HubExtended.minigames.MinigameCommand;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.sql.SQL_Class;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;
import java.util.*;

public class HubExtended extends JavaPlugin {

    private static HubExtended instance;
    private String host, usr, pw, dbn;
    private int port;
    private SQL_Class sql_class;
    public Map<String, Minigame> minigameHashMap;
    private Map<UUID,HubPlayer> hubPlayerList;

    public void onEnable(){
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("connect").setExecutor(new BungeeConnect());
        getCommand("msg").setExecutor(new BungeeMessage());
        getCommand("hub").setExecutor(new BungeeConnect());
        getCommand("sethub").setExecutor(new BungeeConnect());

        this.host = getConfig().get("sql.host").toString();
        this.usr = getConfig().get("sql.username").toString();
        this.pw = getConfig().get("sql.password").toString();
        this.dbn = getConfig().get("sql.database").toString();
        this.port = getConfig().getInt("sql.port");

        getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageListener());


        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        getCommand("minigames").setExecutor(new MinigameCommand());


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
}
