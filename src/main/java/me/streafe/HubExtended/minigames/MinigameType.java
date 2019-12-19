package me.streafe.HubExtended.minigames;

public enum MinigameType {

    ARROW("arrow");

    String name;

    MinigameType(String name){
        this.name = name;
    }

    String getName(){
        return this.name;
    }

}
