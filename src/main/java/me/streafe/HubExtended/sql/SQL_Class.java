package me.streafe.HubExtended.sql;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.hub_listeners.ShopEntities;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;

public class SQL_Class {

    private int port;
    private String host;
    private String usr;
    private String password;
    private String dbn;
    private Connection con;
    private Statement statement;

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

            createDefaulttables();
        }
    }



    public Connection getConnection(){
        return this.con;
    }

    public void createDefaulttables(){
        try {
            statement = con.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS `player_chest_survival` (`uuid` varchar(36), `thunder_sword` varchar(8), `zombie_spawner` varchar(8), `mighty_pickaxe` varchar(8), `tar_helmet` varchar(8))");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /*
        {0:0} first index is true/false, second index is amount
    */
    public void insertNewPlayerToSQL(Player player){
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM `player_chest_survival` WHERE uuid='"+player.getUniqueId()+"'");
            ResultSet rs = statement.executeQuery();
            while(!rs.next()){
                statement.executeUpdate("INSERT INTO `player_chest_survival` (`uuid`, `thunder_sword`, `zombie_spawner`, `mighty_pickaxe`, `tar_helmet`) VALUES ('"+player.getUniqueId()+"','0:0','0:0','0:0','0:0')");
                player.sendMessage(Utils.translateInnerclass(" "));
                player.sendMessage(Utils.translateInnerclass("&aAdded to database!"));
                player.sendMessage(Utils.translateInnerclass(" "));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void updateItemToPlayerChest(Player player, ShopEntities shopEntity, int amount, double price){
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());


        hubPlayer.tokens = hubPlayer.tokens-price;
        HBConfigSetup hbConfigSetup = new HBConfigSetup(player);
        hbConfigSetup.setHubPlayer();
        hbConfigSetup.editString("player.tokens",hbConfigSetup.getDouble("player.tokens")-price);
        try {
            PreparedStatement statement = con.prepareStatement("SELECT * FROM `player_chest_survival`");
            statement.executeUpdate("UPDATE `player_chest_survival` SET "+shopEntity.getName()+" = '1:"+amount+"' WHERE uuid='"+player.getUniqueId()+"'");
            player.sendMessage(Utils.translateInnerclass(" "));
            player.sendMessage(Utils.translateInnerclass("&aUpdated database config file!"));
            player.sendMessage(Utils.translateInnerclass(" "));

            /*
            PreparedStatement statement = con.prepareStatement("SELECT * FROM `player_chest_survival` WHERE uuid='"+player.getUniqueId()+"'");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                statement.executeUpdate("UPDATE `player_chest_survival` (`"+shopEntity.getName()+"`) VALUES ('1:"+amount+"')");
                player.sendMessage(Utils.translateInnerclass(" "));
                player.sendMessage(Utils.translateInnerclass("&aUpdated database config file!"));
                player.sendMessage(Utils.translateInnerclass(" "));
            }

             */

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public boolean alreadyHasBoughtItemOnce(Player player, ShopEntities entity){
        try {
            PreparedStatement statement = con.prepareStatement("SELECT "+entity.getName()+" FROM `player_chest_survival` WHERE uuid='"+player.getUniqueId()+"'");
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String[] l = rs.getString(entity.getName()).split(":");
                if(Integer.parseInt(l[0]) == 1){
                    return true;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
}
