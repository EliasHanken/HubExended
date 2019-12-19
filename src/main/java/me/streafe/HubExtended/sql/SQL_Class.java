package me.streafe.HubExtended.sql;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL_Class {

    private int port;
    private String host;
    private String usr;
    private String password;
    private String dbn;
    private Connection con;

    private Utils utils = new Utils();


    public SQL_Class(String host, int port, String usr, String password, String database){
        this.dbn = database;
        this.password = password;
        this.usr = usr;
        this.host = host;
        this.port = port;
    }

    public void connect() throws SQLException, ClassNotFoundException {
        if(this.con != null && !this.con.isClosed()){
            return;
        }

        synchronized (this){
            if(this.con != null && !this.con.isClosed()){
                return;
            }
            Class.forName("com.mysql.jdbc.Driver");
            this.con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + dbn,usr,password);
            for(Player player : Bukkit.getOnlinePlayers()){
                if(player.isOp()){
                    player.sendMessage("");
                    player.sendMessage(utils.getPluginPrefix() + "connected to the database");
                    player.sendMessage("");
                }
            }
        }
    }



    public Connection getConnection(){
        return this.con;
    }
}
