package me.streafe.HubExtended.hub_listeners;

public enum ShopEntities {

    ZOMBIE_SPAWNER("zombie_spawner"), THUNDER_SWORD("thunder_sword"), TAR_HELMET("tar_helmet"), MIGHTY_PICKAXE("mighty_pickaxe");

    String name;

    ShopEntities(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

}
