package me.streafe.HubExtended.player_utils;

import org.bukkit.ChatColor;

public enum RankEnum {

    MEMBER(ChatColor.DARK_GRAY + "MEMBER", "MEMBER"),
    VIP(ChatColor.GREEN + "VIP", "VIP"),
    ALIEN(ChatColor.LIGHT_PURPLE + "ALIEN", "ALIEN"),
    ALIENPLUSS(ChatColor.LIGHT_PURPLE +"ALIEN+", "ALIEN+"),
    MODERATOR(ChatColor.AQUA + "MODERATOR", "MODERATOR"),
    ADMIN(ChatColor.RED + "ADMIN", "ADMIN"),
    CO_OWNER(ChatColor.LIGHT_PURPLE + "CO-OWNER", "CO-OWNER"),
    OWNER(ChatColor.DARK_RED + "OWNER", "OWNER"),
    DEVELOPER(ChatColor.DARK_RED + "DEVELOPER", "DEVELOPER");

    private String prefix;
    private String name;

    RankEnum(String prefix, String name){
        this.prefix = prefix;
        this.name = name;
    }

    public String getPrefix(){
        return prefix;
    }

    public String getName(){
        return name;
    }



}
