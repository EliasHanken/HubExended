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
                                    double x = HubExtended.getInstance().getConfig().getDouble("world.hubX");
                                    double y = HubExtended.getInstance().getConfig().getDouble("world.hubY");
                                    double z = HubExtended.getInstance().getConfig().getDouble("world.hubZ");
                                    float pitch = (float) HubExtended.getInstance().getConfig().getDouble("world.hubPitch");
                                    float yaw = (float) HubExtended.getInstance().getConfig().getDouble("world.hubYaw");
                                    Location hubLoc = new Location(Bukkit.getWorld("world"),x,y,z,yaw,pitch);
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

                double x = player.getLocation().getX();
                double y = player.getLocation().getY();
                double z = player.getLocation().getZ();
                float pitch = player.getLocation().getPitch();
                float yaw = player.getLocation().getYaw();

                try{
                    HubExtended.getInstance().getConfig().set("world.hubX",x);
                    HubExtended.getInstance().getConfig().set("world.hubY",y);
                    HubExtended.getInstance().getConfig().set("world.hubZ",z);
                    HubExtended.getInstance().getConfig().set("world.hubYaw",yaw);
                    HubExtended.getInstance().getConfig().set("world.hubPitch",pitch);
                    HubExtended.getInstance().saveConfig();
                }catch (Exception e){
                    e.printStackTrace();
                }

                player.sendMessage(utils.translate("&7Hub set to &a" + x + "&7, &a" + y + "&7, &a" + z ));
                player.sendMessage(utils.translate("&7Pitch: &a" + pitch + " &7Yaw: &7" + yaw));
            }


        }
        return true;
    }
}
