package me.streafe.HubExtended.gameAccessories;

public enum WinEffects {

    HORSERIDER("HORSERIDER",Rarity.valueOf("COMMON")),
    DRAGONRIDER("DRAGONRIDER",Rarity.valueOf("LEGENDARY")),
    ZOMBIERIDER("ZOMBIERIDER",Rarity.valueOf("EPIC"));

    String name;
    Rarity rarity;

    WinEffects(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    public String getName(){
        return name;
    }

    public Rarity getRarity(){
        return rarity;
    }
}
