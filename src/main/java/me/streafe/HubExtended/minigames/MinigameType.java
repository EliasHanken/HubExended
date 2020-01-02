package me.streafe.HubExtended.minigames;

public enum MinigameType {

    ARROW("arrow"),
    BARBARIAN("barbarian");

    private String name;

    MinigameType(String name){
        this.name = name;
    }

    String getName(){
        return this.name;
    }

    public static MinigameType fromString(String text){
        for(MinigameType types: MinigameType.values()){
            if(types.name.equalsIgnoreCase(text)){
                return types;
            }
        }
        return null;
    }

}
