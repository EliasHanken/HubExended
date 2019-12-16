package me.streafe.HubExtended;

import me.streafe.HubExtended.bungee.BungeeConnect;
import me.streafe.HubExtended.bungee.BungeeMessage;
import me.streafe.HubExtended.bungee.BungeeMessageListener;
import me.streafe.HubExtended.sql.SQL_Class;
import org.bukkit.plugin.java.JavaPlugin;

public class HubExtended extends JavaPlugin {

    private static HubExtended instance;
    private String host, usr, pw, dbn;
    private int port;
    private SQL_Class sql_class = new SQL_Class(this.host,this.port,this.usr,this.pw,this.dbn);

    public void onEnable(){
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();
        getCommand("connect").setExecutor(new BungeeConnect());
        getCommand("msg").setExecutor(new BungeeMessage());

        this.host = getConfig().get("sql.host").toString();
        this.usr = getConfig().get("sql.username").toString();
        this.pw = getConfig().get("sql.password").toString();
        this.dbn = getConfig().get("sql.database").toString();
        this.port = getConfig().getInt("sql.port");

        getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
        getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageListener());


    }

    public static HubExtended getInstance(){
        return instance;
    }


}
