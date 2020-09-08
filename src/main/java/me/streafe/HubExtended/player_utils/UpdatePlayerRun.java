package me.streafe.HubExtended.player_utils;

import me.streafe.HubExtended.HubExtended;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class UpdatePlayerRun {



    public void playerUpdateEveryLong(Player p, Long l){
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

        /*
        new BukkitRunnable() {
            @Override
            public void run() {
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);

                hubPlayer.tokens = hbConfigSetup.getInt("player.tokens");
                hubPlayer.sendMessage("updated!");
            }
        }.runTaskLaterAsynchronously(HubExtended.getInstance(),l);

         */

        BukkitScheduler scheduler = HubExtended.getInstance().getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(HubExtended.getInstance(), () -> {
            HBConfigSetup hbConfigSetup = new HBConfigSetup(p);

            hubPlayer.tokens = hbConfigSetup.getInt("player.tokens");
        },0L, l);
    }
}
