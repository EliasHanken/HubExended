package me.streafe.HubExtended.gameAccessories;

import me.streafe.HubExtended.utils.InstantFirework;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;

import java.util.Random;

public class KillEffectsUtil {

    public static void playFireworkLaunch(Location location){
        FireworkEffect.Builder fwB = FireworkEffect.builder();
        Random r = new Random();
        fwB.flicker(r.nextBoolean());
        fwB.trail(r.nextBoolean());
        fwB.with(FireworkEffect.Type.BALL);
        fwB.withColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        fwB.withFade(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        FireworkEffect fe = fwB.build();

        InstantFirework iF = new InstantFirework(fe,location);
    }
}
