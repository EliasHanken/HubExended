package me.streafe.HubExtended.gameAccessories;

public enum VictoryDances {

    HORSERIDER("HORSERIDER",Rarity.valueOf("COMMON")),
    DRAGONRIDER("DRAGONRIDER",Rarity.valueOf("LEGENDARY")),
    ZOMBIERIDER("ZOMBIERIDER",Rarity.valueOf("EPIC")),
    NONE("NONE");

    String name;
    Rarity rarity;

    VictoryDances(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    VictoryDances(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Rarity getRarity(){
        return rarity;
    }
}
