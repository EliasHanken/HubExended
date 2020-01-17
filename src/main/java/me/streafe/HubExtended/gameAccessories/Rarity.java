package me.streafe.HubExtended.gameAccessories;

public enum Rarity {

    LEGENDARY("LEGENDARY"),
    EPIC("EPIC"),
    RARE("RARE"),
    COMMON("COMMON");

    String name;

    Rarity(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
