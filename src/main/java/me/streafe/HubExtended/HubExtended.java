package me.streafe.HubExtended;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import me.streafe.HubExtended.api_handler.API;
import me.streafe.HubExtended.bungee.BungeeChannelApi;
import me.streafe.HubExtended.bungee.BungeeConnect;
import me.streafe.HubExtended.bungee.BungeeMessage;
import me.streafe.HubExtended.bungee.BungeeMessageListener;
import me.streafe.HubExtended.hub_commands.AllowedCommandsPerRank;
import me.streafe.HubExtended.hub_commands.RankCommand;
import me.streafe.HubExtended.hub_commands.lucky_chest_command;
import me.streafe.HubExtended.hub_commands.menu_command;
import me.streafe.HubExtended.hub_listeners.BlockListener;
import me.streafe.HubExtended.hub_listeners.RespawnListener;
import me.streafe.HubExtended.hub_listeners.JoinListener;
import me.streafe.HubExtended.hub_listeners.SpeakListener;
import me.streafe.HubExtended.minigames.Minigame;
import me.streafe.HubExtended.minigames.MinigameCommand;
import me.streafe.HubExtended.minigames.OITCCommand;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.sql.SQL_Class;
import me.streafe.HubExtended.utils.CustomSign;
import me.streafe.HubExtended.utils.ItemContent;
import me.streafe.HubExtended.utils.MenuListener;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.scoreboard.Scoreboard;

import java.sql.SQLException;
import java.util.*;

public class HubExtended extends JavaPlugin implements PluginMessageListener {

    private API api = (API) Bukkit.getServer().getPluginManager().getPlugin("HubExtended");

    private static HubExtended instance;
    private String host, usr, pw, dbn;
    private int port;
    private SQL_Class sql_class;
    public Map<String, Minigame> minigameHashMap;
    private Map<UUID,HubPlayer> hubPlayerList;
    private Scoreboard mainScoreboard;
    private String nmsVersion;
    private BungeeChannelApi bungeeChannelApi;

    private Utils utils = new Utils();


    @Override
    public void onDisable(){
        for(Player playersOnline : getServer().getOnlinePlayers()){
            /*
            HBConfigSetup hbConfigSetup = new HBConfigSetup(playersOnline);
            ItemContent itemContent = new ItemContent(playersOnline);



            itemContent.savePlayerInventory(playersOnline);

             */

            playersOnline.getInventory().clear();
        }
    }

    @Override
    public void onEnable(){
        instance = this;
        this.nmsVersion = getNmsVersion();
        if(!this.nmsVersion.equalsIgnoreCase("v1_8_R3")){
            getServer().getConsoleSender().sendMessage(Utils.translateInnerclass("&cDisabling HubExtended not v1_8_R3"));
            getServer().getConsoleSender().sendMessage(Utils.translateInnerclass("&cYou need server to be running v1_8_R3"));
            this.getServer().getPluginManager().disablePlugin(this);
        }

        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getConsoleSender().sendMessage(Utils.translateInnerclass("&aRunning on spigot " + getNmsVersion()));
        getCommand("connect").setExecutor(new BungeeConnect());
        getCommand("msg").setExecutor(new BungeeMessage());
        getCommand("hub").setExecutor(new BungeeConnect());
        getCommand("sethub").setExecutor(new BungeeConnect());
        getCommand("setrank").setExecutor(new RankCommand());
        getCommand("ranklist").setExecutor(new RankCommand());

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

        if(getConfig().getString("sql.enable").equalsIgnoreCase("true")){
            this.sql_class = new SQL_Class(host,port,usr,pw,dbn);
            try {
                sql_class.connect();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }



        for(Player playersOnline : getServer().getOnlinePlayers()){
            try{
                JoinListener.updateOnlinePlayers(playersOnline);
                playersOnline.getInventory().setItem(1,Utils.getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTRkNDliYWU5NWM3OTBjM2IxZmY1YjJmMDEwNTJhNzE0ZDYxODU0ODFkNWIxYzg1OTMwYjNmOTlkMjMyMTY3NCJ9fX0=","&cSettings"));
                playersOnline.getInventory().setItem(0,utils.createNewItemWithMeta("&7Compass to send you","&7to other servers!", Material.COMPASS,"&cServer Switcher"));
                playersOnline.getInventory().setItem(2,utils.createNewItemWithMeta("&7Duel other noobs","&7choose your opponent", Material.DIAMOND_SWORD,"&cDuel Player &a(Left-click)"));


            }catch (Exception e){
                e.printStackTrace();
            }
        }

        /*
        for(Player playersOnline : getServer().getOnlinePlayers()){
            HBConfigSetup hbConfigSetup = new HBConfigSetup(playersOnline);
            ItemContent itemContent = new ItemContent(playersOnline);

            playersOnline.getInventory().setContents(itemContent.getSavedPlayerInventory(hbConfigSetup.get("player.inventory")));
        }


         */

        this.bungeeChannelApi = BungeeChannelApi.of(this);

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

    public String getNmsVersion(){
        return this.getServer().getClass().getPackage().getName().replace(".",",").split(",")[3];
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


    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(!channel.equals("BungeeCord")){
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subchannel = in.readUTF();
        if (subchannel.equals("SomeSubChannel")) {
            // Use the code sample in the 'Response' sections below to read
            // the data.
        }
    }

    public BungeeChannelApi getBungeeChannelApi() {
        return bungeeChannelApi;
    }
}
