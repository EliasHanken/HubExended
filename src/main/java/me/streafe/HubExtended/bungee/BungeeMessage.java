package me.streafe.HubExtended.bungee;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BungeeMessage implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command c, String s, String args[]) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(c.getName().equalsIgnoreCase("msg")){

            }
        }
        return true;
    }
}
