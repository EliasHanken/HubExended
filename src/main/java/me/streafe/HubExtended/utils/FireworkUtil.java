package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import net.minecraft.server.v1_8_R3.EntityFireworks;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.Items;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class FireworkUtil {

    private Player player;
    private HubPlayer hubPlayer;
    private int height;

    public FireworkUtil(Player player, int maxHeight){
        this.player = player;
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
        this.height = height;
    }

    public void spawnFireWork(){
        Location l = player.getLocation();


        FireworkEffect.Builder fwB = FireworkEffect.builder();
        Random r = new Random();
        fwB.flicker(r.nextBoolean());
        fwB.trail(r.nextBoolean());
        fwB.with(FireworkEffect.Type.BALL);
        fwB.withColor(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        fwB.withFade(Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256)));
        FireworkEffect fe = fwB.build();

        ItemStack item = new ItemStack(Items.FIREWORKS);


        FireworkMeta meta = (FireworkMeta) CraftItemStack.asCraftMirror(item).getItemMeta();
        meta.addEffect(fe);

        CraftItemStack.setItemMeta(item, meta);


        double y = l.getY();
        new BukkitRunnable() {
            @Override
            public void run() {
                EntityFireworks entity = new EntityFireworks(((CraftWorld) player.getWorld()).getHandle(), l.getX(), l.getY(), l.getZ(), item) {
                    public void m() {
                        this.world.broadcastEntityEffect(this, (byte)17);
                        die();
                    }
                };
                entity.setInvisible(true);

                ((CraftWorld)player.getWorld()).getHandle().addEntity(entity);

                l.add(0,1,0);

                if(l.getY() >= y + height)
                    this.cancel();
            }
        }.runTaskTimer(HubExtended.getInstance(), 2, 2);
    }
}
