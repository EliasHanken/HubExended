package me.streafe.HubExtended.minigames;

import me.streafe.HubExtended.HubExtended;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class MinigameCountdownTask implements Runnable {

    private int taskId;

    public MinigameCountdownTask(Long arg1,Long arg2){
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(HubExtended.getInstance(),this,arg1,arg2);
    }

    public void cancelTask(){
        Bukkit.getScheduler().cancelTask(taskId);
    }

    public boolean isRunning(){
        if(Bukkit.getScheduler().isCurrentlyRunning(taskId)){
            return true;
        }
        return false;
    }

}
