package me.streafe.HubExtended.bungee;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class BungeeConnect implements CommandExecutor {

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
                else{
                    player.sendMessage("§c/connect <server>");
                }
            }

            else if(c.getName().equalsIgnoreCase("hub")){
                player.sendMessage(utils.translate("&aReturning to hub"));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        bListener.connectToServer(player,"hub");
                        this.cancel();

                        try{
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    int x = HubExtended.getInstance().getConfig().getInt("world.hubX");
                                    int y = HubExtended.getInstance().getConfig().getInt("world.hubY");
                                    int z = HubExtended.getInstance().getConfig().getInt("world.hubZ");
                                    Location hubLoc = new Location(Bukkit.getWorld("world"),x,y,z);
                                    player.teleport(hubLoc);
                                }
                            }.runTaskLaterAsynchronously(HubExtended.getInstance(),40L);
                        }catch(Exception e){
                            e.printStackTrace();
                        }



                    }
                }.runTaskLaterAsynchronously(HubExtended.getInstance(),40L);

            }

            else if(c.getName().equalsIgnoreCase("sethub")) {

                int x = player.getLocation().getBlockX();
                int y = player.getLocation().getBlockY();
                int z = player.getLocation().getBlockZ();

                try{
                    HubExtended.getInstance().getConfig().set("world.hubX",x);
                    HubExtended.getInstance().getConfig().set("world.hubY",y);
                    HubExtended.getInstance().getConfig().set("world.hubZ",z);
                    HubExtended.getInstance().saveConfig();
                }catch (Exception e){
                    e.printStackTrace();
                }

                player.sendMessage(utils.translate("&aHub set to " + x + ", " + y + ", " + z ));
            }


        }
        return true;
    }
}
