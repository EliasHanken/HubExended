package me.streafe.HubExtended.gameAccessories;

public enum KillEffects {
    BLOODEXPLOSION("BLOODEXPLOSION",Rarity.valueOf("EPIC")),
    HEADROCKET("HEADROCKET",Rarity.valueOf("LEGENDARY")),
    FINALSMASH("FINALSMASH",Rarity.valueOf("LEGENDARY")),
    PARTYEXPLOSION("PARTYEXPLOSION",Rarity.valueOf("COMMON")),
    NONE("NONE");

    String name;
    Rarity rarity;

    KillEffects(String name, Rarity rarity){
        this.name = name;
        this.rarity = rarity;
    }

    KillEffects(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public Rarity getRarity(){
        return rarity;
    }
}
