package me.streafe.HubExtended.player_utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

public class PlayerRankUpdate {

    private Player player;
    private HubPlayer hubPlayer;
    private int taskId;
    private Scoreboard sb;
    private String rank;

    Utils utils = new Utils();

    public PlayerRankUpdate(Player player){
        this.player = player;
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
        this.sb = HubExtended.getInstance().getServer().getScoreboardManager().getNewScoreboard();
    }

    public void updatePlayerRank(){

        sb.registerNewTeam("MEMBER");
        sb.registerNewTeam("MODERATOR");
        sb.registerNewTeam("ADMIN");
        sb.registerNewTeam("CO_OWNER");
        sb.registerNewTeam("OWNER");

        sb.getTeam("MEMBER").setPrefix(utils.translate("&7MEMBER &f"));
        sb.getTeam("MODERATOR").setPrefix(utils.translate("&3MODERATOR &f"));
        sb.getTeam("MEMBER").setPrefix(utils.translate("&cADMIN &f"));
        sb.getTeam("MEMBER").setPrefix(utils.translate("&dCO_OWNER &f"));
        sb.getTeam("MEMBER").setPrefix(utils.translate("&4OWNER &f"));

        /*
        taskId = HubExtended.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(HubExtended.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {

         */
                for(Player player : Bukkit.getOnlinePlayers()){
                    HBConfigSetup hbConfigSetup = new HBConfigSetup(player);
                    rank = hbConfigSetup.get("player.rank");
                    if(rank.equalsIgnoreCase("MEMBER")){
                        sb.getTeam(rank.toUpperCase()).addPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
                    }else if(rank.equalsIgnoreCase("MODERATOR")){
                        sb.getTeam(rank.toUpperCase()).addPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
                    } else if(rank.equalsIgnoreCase("ADMIN")){
                        sb.getTeam(rank.toUpperCase()).addPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
                    } else if(rank.equalsIgnoreCase("CO_OWNER")){
                        sb.getTeam(rank.toUpperCase()).addPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
                    } else if(rank.equalsIgnoreCase("OWNER")){
                        sb.getTeam(rank.toUpperCase()).addPlayer(Bukkit.getOfflinePlayer(player.getUniqueId()));
                    }

                    player.setScoreboard(sb);
                }
                /*
            }
        },0L,20L);

                 */
    }
}
