package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
        if(hubPlayer.isInGame()){
            Bukkit.getScheduler().scheduleSyncDelayedTask(HubExtended.getInstance(), () -> player.spigot().respawn(), 1L);
            return;
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(HubExtended.getInstance(), () -> player.spigot().respawn(), 1L);

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
            }.runTaskLaterAsynchronously(HubExtended.getInstance(),0L);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
