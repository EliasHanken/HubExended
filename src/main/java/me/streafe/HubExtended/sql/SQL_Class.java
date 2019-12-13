package me.streafe.HubExtended.sql;

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


    public SQL_Class(String host, int port, String usr, String password, String database){
        this.dbn = host;
        this.password = password;
        this.usr = usr;
        this.host = host;
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
        }
    }



    public Connection getConnection(){
        return this.con;
    }
}
