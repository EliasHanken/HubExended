package me.streafe.HubExtended;

import me.streafe.HubExtended.bungee.BungeeMessageListener;
import me.streafe.HubExtended.sql.SQL_Class;
import org.bukkit.plugin.java.JavaPlugin;

public class HubExtended extends JavaPlugin {

    private static HubExtended instance;
    private static String host, usr, pw, dbn;
    private static int port;
    private SQL_Class sql_class = new SQL_Class(this.host,this.port,this.usr,this.pw,this.dbn);

    public void onEnable(){
        instance = this;
        getConfig().options().copyDefaults(true);
        saveConfig();

        this.host = getConfig().get("sql.host").toString();
        this.usr = getConfig().get("sql.username").toString();
        this.pw = getConfig().get("sql.password").toString();
        this.dbn = getConfig().get("sql.databasename").toString();
        this.port = getConfig().getInt("sql.port");

        this.getServer().getMessenger().registerOutgoingPluginChannel(this,"BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeMessageListener());
    }

    public static HubExtended getInstance(){
        return instance;
    }


}
