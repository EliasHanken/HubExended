package me.streafe.HubExtended.hub_commands;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.Menu;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public class menu_command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String args[]) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

            if(cmd.getName().equalsIgnoreCase("settings")){
                Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.LEVEL_UP, 1f,1f);

                Menu settings = new Menu("Settings",18);
                settings.settingsMenu(p);
            }
        }

        return true;
    }
}
