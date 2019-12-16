package me.streafe.HubExtended.bungee;

import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Utility;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BungeeMessage implements CommandExecutor {

    private BungeeMessageListener bListener = new BungeeMessageListener();
    private Utils utils = new Utils();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String args[]) {
        if(sender instanceof Player){
            Player player = (Player) sender;



            if(cmd.getName().equalsIgnoreCase("msg")){




                if(args.length > 1){
                    String message = utils.translate("&c[PM] &r&7to -> " + args[0] + "&r: ");
                    String recieved = utils.translate("&c[PM] &r&7from -> " + player.getName() + "&r: ");


                    for (int i = 1; i < args.length; i++){
                        message += args[i] + " ";
                        recieved += args[i] + " ";
                    }



                    if(Bukkit.getPlayer(args[0]) == null || !(Bukkit.getPlayer(args[0]).isOnline())){
                        bListener.sendPlayerForwardMessage(Bukkit.getPlayer(args[0]),utils.translate(recieved));

                        player.sendMessage(utils.translate(message));
                        return true;
                    }

                    bListener.sendPlayerMessage(Bukkit.getPlayer(args[0]),utils.translate(recieved));

                    player.sendMessage(utils.translate(message));

                } else{
                    player.sendMessage(utils.translate("&c/msg [player] [msg]"));
                    return true;
                }
            }
        }
        return true;
    }
}
