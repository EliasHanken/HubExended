package me.streafe.HubExtended.minigames;

public enum MinigameType {

    ARROW("ARROW"),
    BARBARIAN("BARBARIAN");

    private String name;

    MinigameType(String name){
        this.name = name;
    }

    String getName(){
        return this.name;
    }



}
