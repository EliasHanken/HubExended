package me.streafe.HubExtended.minigames;

public enum GameState {

    LOBBY("LOBBY"), STARTED("STARTED"), FINISHED("FINISHED");

    private String name;

    GameState(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
