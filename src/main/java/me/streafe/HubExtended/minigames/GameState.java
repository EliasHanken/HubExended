package me.streafe.HubExtended.minigames;

public enum GameState {

    LOBBY("LOBBY"), STARTED("STARTED"), FINISHED("FINISHED");

    String name;

    GameState(String name){
        this.name = name;
    }

    String getName(){
        return this.name;
    }
}
