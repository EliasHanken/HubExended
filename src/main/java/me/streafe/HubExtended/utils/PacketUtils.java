package me.streafe.HubExtended.utils;


import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketUtils {

    public static void sendPacket(Player player, Packet<?> packet){
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void sendScoreBoard(Player player){
        Scoreboard board = new Scoreboard();
        ScoreboardObjective obj = board.registerObjective("Test", IScoreboardCriteria.b);

        PacketPlayOutScoreboardObjective removepacket = new PacketPlayOutScoreboardObjective(obj,1);
        PacketPlayOutScoreboardObjective createpacket = new PacketPlayOutScoreboardObjective(obj,0);
        PacketPlayOutScoreboardDisplayObjective display = new PacketPlayOutScoreboardDisplayObjective(1,obj);

        obj.setDisplayName("Test");


    }
}
