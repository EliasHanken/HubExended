package me.streafe.HubExtended.minigames;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class MinigameCommand implements CommandExecutor {
    Utils utils = new Utils();

    private int taskid;


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String args[]) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

            Minigame minigame;

            if (cmd.getName().equalsIgnoreCase("minigames")) {
                if (args.length == 3 && args[0].equalsIgnoreCase("create")) {
                    if(hubPlayer.isOwnerOfGame()){
                        hubPlayer.sendMessage(utils.getPluginPrefix() + utils.translate("&cYou are already a owner of a game!"));
                        return true;
                    }
                    minigame = new Minigame(Integer.parseInt(args[2]), MinigameType.ARROW);
                    hubPlayer.sendMessage(utils.getPluginPrefix() + "Game ID: " + utils.translate("&d&l"+minigame.getGameID()) + utils.translate("&7")+ " (share with friends)");
                    minigame.addNewPlayer(player);
                    hubPlayer.inGame = true;

                    hubPlayer.setGameID(minigame.getGameID());
                    HubExtended.getInstance().minigameHashMap.put(minigame.getGameID(), minigame);
                    HubExtended.getInstance().minigameHashMap.get(minigame.getGameID()).setOwner(player);

                    hubPlayer.sendMessage(utils.translate("&7Max players: ") + HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getMaxPlayers());
                    hubPlayer.sendMessage(utils.translate("&7Is started: ") + HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).isStarted());
                } else if (args.length == 1 && args[0].equalsIgnoreCase("delete")) {
                    if (HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getOwner() == player) {
                        HubExtended.getInstance().minigameHashMap.remove(hubPlayer.getGameID());
                        hubPlayer.sendMessage(utils.getPluginPrefix() + "you deleted your game");
                        hubPlayer.setGameID("");
                        hubPlayer.inGame = false;
                    } else {
                        hubPlayer.sendMessage(utils.getPluginPrefix() + "You are not a current owner of a game");
                    }
                } else if (args.length == 2 && args[0].equalsIgnoreCase("join")) {
                    if(HubExtended.getInstance().getMinigameByID(args[1]).playerList.contains(hubPlayer)){
                        hubPlayer.sendMessage(utils.getPluginPrefix() + "&cYou are already in this game");
                        return true;
                    }
                    else if(HubExtended.getInstance().getHubPlayer(player.getUniqueId()).inGame){
                        hubPlayer.sendMessage(utils.getPluginPrefix() + utils.translate("&cYou are already in a game"));
                        return true;
                    }
                    HubExtended.getInstance().getMinigameByID(args[1]).addNewPlayer(player);
                    player.sendMessage(utils.getPluginPrefix() + "you joined the game with ID: " + HubExtended.getInstance().getMinigameByID(args[1]).getGameID());
                    hubPlayer.setGameID(args[1]);
                    hubPlayer.sendMessage("Your game id is: " + hubPlayer.getGameID());

                    for(HubPlayer partyPlayers : HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerList){
                        if(!(partyPlayers.getName() == hubPlayer.getName())){
                            partyPlayers.sendMessage(utils.translate("&7" + hubPlayer.getName() + " &cleft the party"));
                        }
                    }

                } else if(args.length == 1 && args[0].equalsIgnoreCase("list")){
                    hubPlayer.sendMessage(utils.getPluginPrefix() + "Your game id is: " + hubPlayer.getGameID());
                    hubPlayer.sendMessage("Gametype: " + HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getType().toString());
                    hubPlayer.sendMessage("started: " + HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).isStarted());
                    hubPlayer.sendMessage(utils.translate("&7List:"));
                    hubPlayer.sendMessage(utils.translate("&7") + HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getPlayerListString());
                }
                else if(args.length == 1 && args[0].equalsIgnoreCase("leave")){
                    if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getOwner() == player){
                        HubExtended.getInstance().minigameHashMap.remove(hubPlayer.getGameID());
                    }

                    for(HubPlayer partyPlayers : HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerList){
                        if(!(partyPlayers.getName() == hubPlayer.getName())){
                            partyPlayers.sendMessage(utils.translate("&7" + hubPlayer.getName() + " &cleft the party"));
                        }
                    }
                    HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerList.remove(hubPlayer);
                    HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerAmount--;
                    hubPlayer.inGame = false;
                    hubPlayer.setGameID("");
                    hubPlayer.sendMessage(utils.getPluginPrefix() + "You left the gameparty");


                }

                else if(args.length == 1 && args[0].equalsIgnoreCase("start")){
                    if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getOwner() == player){
                        if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerAmount < 2){
                            player.sendMessage(utils.translate("&cYou are less than 2 players in the party!"));
                            player.sendMessage(utils.translate("&cWant to start it anyway? Do /minigames startanyway"));
                            return true;
                        }
                        HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).setStarted(true);
                        for(HubPlayer players : HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getPlayerList()){
                            players.sendMessage(utils.translate("&aGame starting in: "));
                            player.sendMessage("");

                            this.taskid = HubExtended.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(HubExtended.getInstance(), new Runnable() {
                                int i = 10;
                                @Override
                                public void run() {
                                    if(i <= 0){
                                        HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).startGame();
                                        Bukkit.getScheduler().cancelTask(taskid);
                                        return;
                                    }
                                    if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerAmount<2){
                                        hubPlayer.sendMessage(utils.translate("&cSomeone left your lobby"));
                                        Bukkit.getScheduler().cancelTask(taskid);
                                    }
                                    players.sendMessage(utils.translate("&a") + i);
                                    i--;

                                }
                            },0L, 20L);
                        }
                    }
                }

                else if(args.length == 1 && args[0].equalsIgnoreCase("startanyway")){
                    if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getOwner() == player){
                        HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).setStarted(true);
                        for(HubPlayer players : HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).getPlayerList()){
                            players.sendMessage(utils.translate("&aGame starting in: "));
                            player.sendMessage("");

                            this.taskid = HubExtended.getInstance().getServer().getScheduler().scheduleSyncRepeatingTask(HubExtended.getInstance(), new Runnable() {
                                int i = 10;
                                @Override
                                public void run() {
                                    if(i <= 0){
                                        HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).startGame();
                                        Bukkit.getScheduler().cancelTask(taskid);
                                        return;
                                    }
                                    if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).playerAmount<2){
                                        hubPlayer.sendMessage(utils.translate("&cSomeone left your lobby"));
                                        Bukkit.getScheduler().cancelTask(taskid);
                                    }
                                    players.sendMessage(utils.translate("&a") + i);
                                    i--;

                                }
                            },0L, 20L);
                        }
                    }
                }

                else {
                    hubPlayer.sendMessage("§c/minigame [create] [type] [max players]");
                }
            }
        }
        return true;
    }
}
