package me.streafe.HubExtended.gameAccessories;


import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.minigames.MinigameCountdownTask;
import me.streafe.HubExtended.utils.ConfigUtil;
import me.streafe.HubExtended.utils.ParticleEffect;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.MathHelper;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityEquipment;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityTeleport;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;
import net.minecraft.server.v1_8_R3.World;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

public class KillEffectHeadRocket extends KillEffect {

    public KillEffectHeadRocket() {
        super("Head Rocket", "HEAD_ROCKET", Material.SKULL_ITEM);
    }

    @Override
    public void play(Player p, Location location) {
        Location loc = location;
        World world = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand entityArmorStand = new EntityArmorStand(world);
        entityArmorStand.setLocation(loc.getX(), loc.getY(), loc.getZ(),
                MathHelper.d((entityArmorStand.pitch * 256F) / 360F),
                MathHelper.d((entityArmorStand.yaw * 256F) / 360F));
        entityArmorStand.setInvisible(true);
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) skull.getItemMeta();
        skullMeta.setOwner(Bukkit.getOfflinePlayer(p.getUniqueId()).getName());
        skull.setItemMeta(skullMeta);
        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(skull);
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(entityArmorStand);
        PacketPlayOutEntityEquipment packetPlayOutEntityEquipment = new PacketPlayOutEntityEquipment(entityArmorStand.getId(), 4,
                nmsItemStack);
        for (Player all : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packet);
            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityEquipment);
        }

        MinigameCountdownTask task = new MinigameCountdownTask(0L,1L) {
            int i = 18;
            Location lastPos = new Location(loc.getWorld(), entityArmorStand.locX, entityArmorStand.locY, entityArmorStand.locZ);
            @Override
            public void run() {
                if (i > 0) {
                    entityArmorStand.locY += 0.5;
                    Location pos = new Location(loc.getWorld(), entityArmorStand.locX, entityArmorStand.locY, entityArmorStand.locZ);
                    if (pos.getBlock().getType() == Material.AIR) {
                        PacketPlayOutEntityHeadRotation packetPlayOutEntityHeadRotation = new PacketPlayOutEntityHeadRotation(
                                entityArmorStand,
                                (byte) MathHelper.floor(((entityArmorStand.yaw += 0.1) * 256.0F) / 360.0F));
                        PacketPlayOutEntityTeleport packetPlayOutEntityTeleport = new PacketPlayOutEntityTeleport(entityArmorStand);
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityTeleport);
                            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityHeadRotation);
                        }
                        ParticleEffect.CLOUD.display(0, 0, 0, 0, 1, pos, 256f);
                        lastPos = pos;
                        i--;
                    } else {
                        i = 0;
                    }
                    if (i == 0) {
                        PacketPlayOutEntityDestroy packetPlayOutEntityDestroy = new PacketPlayOutEntityDestroy(entityArmorStand.getId());
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) all).getHandle().playerConnection.sendPacket(packetPlayOutEntityDestroy);
                            all.playEffect(lastPos, Effect.STEP_SOUND, 152);
                        }
                        cancelTask();

                    }

                }
            }
        };







    }
}