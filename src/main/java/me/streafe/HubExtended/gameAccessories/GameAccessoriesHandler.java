package me.streafe.HubExtended.gameAccessories;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.minigames.MinigameCountdownTask;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.FireworkUtil;
import me.streafe.HubExtended.utils.InstantFirework;
import me.streafe.HubExtended.utils.ParticleEffect;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GameAccessoriesHandler {

    private HubPlayer hubPlayer;
    private Player player;
    private HBConfigSetup hbConfigSetup;
    private Entity entity;

    public GameAccessoriesHandler(Player player){
        this.player = player;
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
        this.hbConfigSetup = new HBConfigSetup(this.player);
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(this.player.getUniqueId());
    }


    public void playKillEffect(Location location){
        if(hubPlayer.killEffect == KillEffects.NONE){
            return;
        }else if(hubPlayer.killEffect == KillEffects.HEADROCKET){
            KillEffectHeadRocket headRocket = new KillEffectHeadRocket();
            headRocket.play(player, location);
            player.playSound(location, Sound.FIREWORK_LAUNCH,(float)0.5,(float)-2);
        }else if(hubPlayer.killEffect == KillEffects.PARTYEXPLOSION){
            InstantFirework iF = new InstantFirework(FireworkUtil.getRandomExplosion(),location);
        }else if(hubPlayer.killEffect == KillEffects.BLOODEXPLOSION){
            for(Player all : Bukkit.getOnlinePlayers()){
                all.playEffect(location, Effect.STEP_SOUND,152);
            }
        }else if(hubPlayer.killEffect == KillEffects.FINALSMASH){
            KillEffectFinalSmash finalSmash = new KillEffectFinalSmash();
            finalSmash.play(player,location);
            player.playSound(location, Sound.FIREWORK_LAUNCH,(float)0.5,(float)-4);
        }
    }




}
