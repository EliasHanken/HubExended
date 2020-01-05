package me.streafe.HubExtended.player_utils;

import org.bukkit.ChatColor;

public enum RankEnum {

    MEMBER(ChatColor.DARK_GRAY + "MEMBER")
    ,MODERATOR(ChatColor.AQUA + "MODERATOR")
    ,ADMIN(ChatColor.RED + "ADMIN")
    ,CO_OWNER(ChatColor.LIGHT_PURPLE + "CO-OWNER")
    ,OWNER(ChatColor.DARK_RED + "OWNER");

    private String prefix;

    RankEnum(String prefix){
        this.prefix = prefix;
    }

    public String getPrefix(){
        return prefix;
    }

}
