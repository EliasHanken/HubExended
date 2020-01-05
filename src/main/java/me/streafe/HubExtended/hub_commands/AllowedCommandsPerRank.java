package me.streafe.HubExtended.hub_commands;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AllowedCommandsPerRank implements CommandExecutor {

    Utils utils = new Utils();
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String args[]) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(cmd.getName().equalsIgnoreCase("help")){
                if(args.length == 0){
                    player.sendMessage(utils.translate("&7-x- &dCommands and help &7-x-"));
                    player.sendMessage(utils.translate("&e/settings &7(opens the settings menu)"));
                    player.sendMessage(utils.translate("&e/stats &7(shows your server stats) * coming soon"));
                    player.sendMessage(utils.translate("&e/connect &a(hub, practice)"));
                    player.sendMessage(utils.translate("&e/luckychest &7(let's you try your luck)"));
                    player.sendMessage(utils.translate("&e/hub &7(teleports you back to hub)"));
                }
            }

            /*
            else if(cmd.getName().equalsIgnoreCase("gamemode")){

            }



            else if(cmd.getName().equalsIgnoreCase("ban")){

            }

            else if(cmd.getName().equalsIgnoreCase("unban"));
            */

        }


        return true;
    }
}
