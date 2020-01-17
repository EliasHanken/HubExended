package me.streafe.HubExtended.bungee;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.sun.javafx.iio.ios.IosDescriptor;
import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.function.DoubleToIntFunction;

public class BungeeMessageListener implements PluginMessageListener {

    private Utils utils = new Utils();

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if(!channel.equals("BungeeCord")){
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(message);
        String subChannel = in.readUTF();

        switch (subChannel){
            case "Message":
                break;

        }

    }

    public void connectToServer(Player player, String server){
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
        if(hubPlayer.inGame){
            player.sendMessage(Utils.translateInnerclass("&cYou are in game, use /minigames leave"));
            return;
        }
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(HubExtended.getInstance(),"BungeeCord",out.toByteArray());

    }

    public void sendPlayerForwardMessage(Player player, String message){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ForwardToPlayer");
        out.writeUTF(player.getName());
        out.writeUTF("MyChannel");

        ByteArrayOutputStream msgbytes = new ByteArrayOutputStream();
        DataOutputStream msgout = new DataOutputStream(msgbytes);

        try{
            msgout.writeUTF(message);
            msgout.writeShort(123);

            out.writeShort(msgbytes.toByteArray().length);
            out.write(msgbytes.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public void sendPlayerMessage(Player target, String message){

        try{
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            out.writeUTF("Message");
            out.writeUTF(target.getName());
            out.writeUTF(message);
            target.sendPluginMessage(HubExtended.getInstance(),"BungeeCord",b.toByteArray());
        } catch (IOException e){
            e.printStackTrace();
        }



    }

    public void sendPluginMessage(String sub, Player player, String message, String target){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();

        out.writeUTF(sub);
        out.writeUTF(target);

    }

    public void kickPlayer(String playerName, String reason){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("KickPlayer");
        out.writeUTF(playerName);
        out.writeUTF(reason);
    }

    public static String getServerName(Player player){
        String text = "";
        return text;
    }

}
