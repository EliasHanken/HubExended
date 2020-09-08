package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;

public class DuelsCommand implements CommandExecutor {
    private Utils utils = new Utils();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(cmd.getName().equalsIgnoreCase("duels")){
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("accept")){
                        for(DuelGame duelGame : HubExtended.getInstance().getDuelGamesList()){
                            if(duelGame.opponent == (Player)sender){
                                duelGame.acceptDuel();
                            }
                        }
                    } else if(args[0].equalsIgnoreCase("decline")){
                        for(DuelGame duelGame : HubExtended.getInstance().getDuelGamesList()){
                            if(duelGame.opponent == (Player)sender){
                                duelGame.declineDuel();
                            }
                        }
                    }else if(args[0].equalsIgnoreCase("setspawn1")){
                        HubExtended.getInstance().getConfig().set("duels.spawn1",utils.saveLoc(player.getLocation()));
                        player.sendMessage(utils.translate("&aSpawn1 &7set for duels"));
                        HubExtended.getInstance().saveConfig();
                    }else if(args[0].equalsIgnoreCase("setspawn2")){
                        HubExtended.getInstance().getConfig().set("duels.spawn2",utils.saveLoc(player.getLocation()));
                        player.sendMessage(utils.translate("&aSpawn2 &7set for duels"));
                        HubExtended.getInstance().saveConfig();
                    }
                }
            }
        }

        return true;
    }
}
