package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

public class AnimatedScoreboard {

    private Player player;
    public HubPlayer hubPlayer;
    public int tokens;
    private String tokenString;
    private int taskId;
    private int animateTask;
    public String animatedText = "&f&lStreafe's Server";
    int time;

    Utils utils = new Utils();

    public AnimatedScoreboard(Player player){
        this.player = player;
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
    }

    public void animateText(){
        time = 14;
        animateTask = HubExtended.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(HubExtended.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                if(time == 14){
                    animatedText = "&6&lS&ftreafe's Server";
                    time--;
                }
                if(time == 13){
                    animatedText = "&f&lS&6treafe's Server";
                    time--;
                }
                if(time == 12){
                    animatedText = "&f&lSt&6reafe's Server";
                    time--;
                }
                if(time == 11){
                    animatedText = "&f&lStr&6eafe's Server";
                    time--;
                }
                if(time == 10){
                    animatedText = "&f&lStre&6afe's Server";
                    time--;
                }
                if(time == 9){
                    animatedText = "&f&lStrea&6fe's Server";
                    time--;
                }
                if(time == 8){
                    animatedText = "&f&lStreaf&6e's Server";
                    time--;
                }
                if(time == 7){
                    animatedText = "&f&lStreafe'&6s Server";
                    time--;
                }
                if(time == 6){
                    animatedText = "&f&lStreafe's &6Server";
                    time--;
                }
                if(time == 5){
                    animatedText = "&f&lStreafe's S&6erver";
                    time--;
                }
                if(time == 4){
                    animatedText = "&f&lStreafe's Se&6rver";
                    time--;
                }
                if(time == 3){
                    animatedText = "&f&lStreafe's Ser&6ver";
                    time--;
                }
                if(time == 2){
                    animatedText = "&f&lStreafe's Serv&6er";
                    time--;
                }
                if(time == 1){
                    animatedText = "&f&lStreafe's Serve&6r";
                    time--;
                }
                if(time == 0){
                    animatedText = "&f&lStreafe's Server";
                    time=10;
                }
            }
        },0L,20L);
    }

    public void view(){

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();

        Objective objective = scoreboard.registerNewObjective("Tokens","dummy");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        objective.setDisplayName(utils.translate("&a&lStreafe's Server"));

        Score tokens = objective.getScore("Tokens:" + hubPlayer.tokens);
        tokens.setScore(1);

        player.setScoreboard(scoreboard);
        updateScoreBoard();
    }

    public void updateScoreBoard(){
        taskId = HubExtended.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(HubExtended.getInstance(), new BukkitRunnable() {
            @Override
            public void run() {
                HBConfigSetup hbConfigSetup = new HBConfigSetup(player);

                tokens = hbConfigSetup.getInt("player.tokens");
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard scoreboard = manager.getNewScoreboard();

                Objective objective = scoreboard.registerNewObjective("Tokens","dummy");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
                objective.setDisplayName(utils.translate(animatedText + ""));

                Score space4 = objective.getScore(utils.translate(" "));
                space4.setScore(5);

                /*
                Score space = objective.getScore(utils.translate("&7--x---x--"));
                space.setScore(5);
                 */

                Score tokens = objective.getScore(utils.translate("&7Tokens: &e") + hubPlayer.tokens);
                tokens.setScore(2);

                Score space2 = objective.getScore(utils.translate("&7"));
                space2.setScore(3);

                String prefix = "";

                if(hbConfigSetup.get("player.rank").equalsIgnoreCase("MEMBER")){
                    prefix = "&7";
                } else if(hbConfigSetup.get("player.rank").equalsIgnoreCase("MODERATOR")){
                    prefix = "&3";
                } else if(hbConfigSetup.get("player.rank").equalsIgnoreCase("ADMIN")){
                    prefix = "&c";
                } else if(hbConfigSetup.get("player.rank").equalsIgnoreCase("CO_OWNER")){
                    prefix = "&d";
                } else if(hbConfigSetup.get("player.rank").equalsIgnoreCase("OWNER")){
                    prefix = "&4";
                }

                Score rank = objective.getScore(utils.translate("&7Rank: ") + utils.translate(prefix + hbConfigSetup.get("player.rank")));
                rank.setScore(4);


                Score space3 = objective.getScore(utils.translate("  "));
                space3.setScore(1);

                player.setScoreboard(scoreboard);
            }
        },0L,20L);
    }


}
