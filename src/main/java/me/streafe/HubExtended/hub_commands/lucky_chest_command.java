package me.streafe.HubExtended.hub_commands;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.Menu;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class lucky_chest_command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String args[]) {

        Utils utils = new Utils();

        if(sender instanceof Player){
            Player p = (Player) sender;
            HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());
            cmd_cooldown cmd_cooldown = new cmd_cooldown(p);

            if(cmd.getName().equalsIgnoreCase("luckychest")){
                if(hubPlayer.isOnCooldown){
                    p.sendMessage(utils.translate("&cYou are on a cooldown &8") + cmd_cooldown.getSeconds());
                    return true;
                }

                hubPlayer.isOnCooldown = true;
                cmd_cooldown.cooldownRunner();


                Menu chestOpen = new Menu("chest open",9);
                chestOpen.luckyChest(p);
            }
        }

        return true;
    }
}
