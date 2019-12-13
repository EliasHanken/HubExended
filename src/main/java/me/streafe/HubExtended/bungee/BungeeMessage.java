package me.streafe.HubExtended.bungee;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BungeeMessage implements CommandExecutor {

    private BungeeMessageListener bListener = new BungeeMessageListener();
    private Utils utils = new Utils();

    @Override
    public boolean onCommand(CommandSender sender, Command c, String s, String args[]) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            if(c.getName().equalsIgnoreCase("connect")){
                if(args.length == 1){

                    player.sendMessage(utils.translate("&aConnection to " + args[0] + "..."));
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            bListener.connectToServer(player,args[0]);
                            this.cancel();
                        }
                    }.runTaskLaterAsynchronously(HubExtended.getInstance(),40L);
                }
            }
        }
        return true;
    }
}
