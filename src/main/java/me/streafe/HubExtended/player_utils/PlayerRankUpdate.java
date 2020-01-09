package me.streafe.HubExtended.player_utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class PlayerRankUpdate {

    private Player player;
    private HubPlayer hubPlayer;
    private BukkitTask taskId;
    private Scoreboard sb;
    private String rank;
    private Team team;

    Utils utils = new Utils();

    public PlayerRankUpdate(Player player){
        this.player = player;
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
        this.sb = HubExtended.getInstance().getMainScoreboard();

    }

    public void updatePlayerRank(){
        taskId = HubExtended.getInstance().getServer().getScheduler().runTaskTimer(HubExtended.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                if(hubPlayer.rank == RankEnum.MEMBER){
                    if(sb.getTeam("MEMBER") == null){
                        sb.registerNewTeam("MEMBER");
                    }
                    sb.getTeam("MEMBER").setPrefix(utils.translate("&7"));

                    sb.getTeam("MEMBER").addPlayer(player);
                    player.setDisplayName(sb.getTeam("MEMBER").getPrefix() + player.getName());

                    team = sb.getTeam("MEMBER");
                }
                else if(hubPlayer.rank == RankEnum.MODERATOR){
                    if(sb.getTeam("MODERATOR") == null){
                        sb.registerNewTeam("MODERATOR");
                    }
                    sb.getTeam("MODERATOR").setPrefix(utils.translate("&3MOD | &3"));

                    sb.getTeam("MODERATOR").addPlayer(player);
                    player.setDisplayName(sb.getTeam("MODERATOR").getPrefix() + player.getName());

                    team = sb.getTeam("MODERATOR");
                }
                else if(hubPlayer.rank == RankEnum.ADMIN){
                    if(sb.getTeam("ADMIN") == null){
                        sb.registerNewTeam("ADMIN");
                    }
                    sb.getTeam("ADMIN").setPrefix(utils.translate("&cADMIN | &c"));

                    sb.getTeam("ADMIN").addPlayer(player);
                    player.setDisplayName(sb.getTeam("ADMIN").getPrefix() + player.getName());

                    team = sb.getTeam("ADMIN");
                }
                else if(hubPlayer.rank == RankEnum.CO_OWNER){
                    if(sb.getTeam("CO_OWNER") == null){
                        sb.registerNewTeam("CO_OWNER");
                    }
                    sb.getTeam("CO_OWNER").setPrefix(utils.translate("&dCO-OWNER | &d"));

                    sb.getTeam("CO_OWNER").addPlayer(player);
                    player.setDisplayName(sb.getTeam("CO_OWNER").getPrefix() + player.getName());

                    team = sb.getTeam("CO_OWNER");
                }
                else if(hubPlayer.rank == RankEnum.OWNER){
                    if(sb.getTeam("OWNER") == null){
                        sb.registerNewTeam("OWNER");
                    }
                    sb.getTeam("OWNER").setPrefix(utils.translate("&4OWNER | &4"));

                    sb.getTeam("OWNER").addPlayer(player);
                    player.setDisplayName(sb.getTeam("OWNER").getPrefix() + player.getName());

                    team = sb.getTeam("OWNER");

                }
                else if(hubPlayer.rank == RankEnum.DEVELOPER){
                    if(sb.getTeam("DEVELOPER") == null){
                        sb.registerNewTeam("DEVELOPER");
                    }
                    sb.getTeam("DEVELOPER").setPrefix(utils.translate("&4DEV | &4"));

                    sb.getTeam("DEVELOPER").addPlayer(player);
                    player.setDisplayName(sb.getTeam("DEVELOPER").getPrefix() + player.getName());

                    team = sb.getTeam("DEVELOPER");

                }

                for(Player player : HubExtended.getInstance().getServer().getOnlinePlayers()){
                    player.setScoreboard(sb);
                }
            }
        },0,20L);
    }
}
