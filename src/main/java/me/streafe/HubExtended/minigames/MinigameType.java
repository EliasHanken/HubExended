package me.streafe.HubExtended.minigames;

public enum MinigameType {

    ARROW("ARROW"),
    BARBARIAN("BARBARIAN"),
    OITC("OITC"),
    FFA("FFA"),
    SKYWARS("SKYWARS");

    String name;

    MinigameType(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }



}
