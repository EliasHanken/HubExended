package me.streafe.HubExtended.hub_commands;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class hub_handler {

    private HubPlayer hubPlayer;

    public hub_handler(){

    }

    public void handleHubPlayers(@org.jetbrains.annotations.NotNull Player p, @org.jetbrains.annotations.NotNull Long l){
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());

        BukkitScheduler scheduler = HubExtended.getInstance().getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(HubExtended.getInstance(), () -> {
            p.setFoodLevel(20);
            HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
            p.setLevel((int) hubPlayer.level);
        },0L, l);

    }
}
