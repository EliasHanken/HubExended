package me.streafe.HubExtended.hub_commands;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class cmd_cooldown {

    private Long cooldown = 100L;
    private HubPlayer hubPlayer;
    private Player player;
    private int taskId;

    public cmd_cooldown(Player player){
        this.player = player;
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
    }

    public void cooldownRunner(){
        taskId = HubExtended.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(HubExtended.getInstance(), () -> {
            if(cooldown>=0L){
                cooldown-=20L;
            }else if(cooldown<=0L){
                hubPlayer.isOnCooldown = false;
                HubExtended.getInstance().getServer().getScheduler().cancelTask(taskId);
            }
        },0L, 20L);
    }

    public Long getSeconds(){
        return (cooldown / 20L);
    }

}
