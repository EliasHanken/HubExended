package me.streafe.HubExtended.gameAccessories;

public enum DeathNoises {

    VILLAGER("VILLAGER",Rarity.valueOf("COMMON")),
    POOP("POOP",Rarity.valueOf("RARE")),
    BRAWL("BRAWL",Rarity.valueOf("EPIC"));

    String name;
    Rarity rarity;

    DeathNoises(String name, Rarity rarity){
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
