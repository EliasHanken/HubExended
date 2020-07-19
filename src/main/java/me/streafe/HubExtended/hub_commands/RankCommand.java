package me.streafe.HubExtended.hub_commands;

import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.RankEnum;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.rmi.CORBA.Util;
import javax.swing.*;
import java.awt.*;

public class RankCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {

        if(cmd.getName().equalsIgnoreCase("setrank")){
            if(sender instanceof Player){
                if (sender.isOp()){
                    if(args.length == 2){
                        if(Bukkit.getPlayer(args[0]) == null){
                            sender.sendMessage(Utils.translateInnerclass(""));
                            sender.sendMessage(Utils.translateInnerclass("&7The player &a" + args[0] + " &cis not online"));
                            sender.sendMessage(Utils.translateInnerclass("&cCan only set rank to online players"));
                            sender.sendMessage(Utils.translateInnerclass(""));
                            return true;
                        }
                        for(RankEnum e : RankEnum.values()){
                            if(e.name().equalsIgnoreCase(args[1])){
                                HBConfigSetup hbConfigSetup = new HBConfigSetup(Bukkit.getPlayer(args[0]));
                                hbConfigSetup.editString("player.rank", args[1].toUpperCase());
                                sender.sendMessage(Utils.translateInnerclass("&7Player &a" + args[0] + " &7is now the rank: " + RankEnum.valueOf(args[1]).getPrefix()));
                                return true;
                            }
                        }
                        sender.sendMessage("");
                        sender.sendMessage(Utils.translateInnerclass("&cThat is not an valid rank!"));
                        sender.sendMessage(Utils.translateInnerclass("&aTry /ranklist"));
                        sender.sendMessage("");

                    }else{
                        sender.sendMessage(Utils.translateInnerclass("&c/setrank <player> <rank>"));
                    }

                }else{
                    sender.sendMessage("§cInsufficient permission");
                }
            }
        }

        else if(cmd.getName().equalsIgnoreCase("ranklist")){
            sender.sendMessage(Utils.translateInnerclass("&e----------------------"));
            sender.sendMessage(Utils.translateInnerclass("&aRanks:"));
            for(RankEnum e : RankEnum.values()){
                sender.sendMessage(Utils.translateInnerclass(e.getPrefix()));
            }
            sender.sendMessage(Utils.translateInnerclass("&e----------------------"));
        }

        return true;
    }
}
