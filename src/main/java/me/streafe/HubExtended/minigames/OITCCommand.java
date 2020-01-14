package me.streafe.HubExtended.minigames;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OITCCommand implements CommandExecutor {


    Utils utils = new Utils();
    ArrayList<Location> locationsList = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String args[]) {



        if(sender instanceof Player){
            Player player = (Player) sender;

            if(cmd.getName().equalsIgnoreCase("oitc")){
                if(player.isOp()){
                    if(args.length == 1 && args[0].equalsIgnoreCase("setRP")){
                        Location loc = player.getLocation();
                        locationsList.add(loc);
                        player.sendMessage(utils.translate("&e[OITC] &arespawn point added " + loc.getBlockX()+":"+loc.getBlockY()+":"+loc.getBlockZ()));

                    }else if(args.length == 1 && args[0].equalsIgnoreCase("saveRP")){
                        Utils.saveLocs(locationsList);
                        player.sendMessage(utils.translate("&e[OITC] &arespawns saved!"));
                    }
                }
            }
        }




        return true;
    }


}
