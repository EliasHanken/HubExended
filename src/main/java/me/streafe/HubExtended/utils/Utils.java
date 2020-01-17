package me.streafe.HubExtended.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.streafe.HubExtended.HubExtended;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class Utils {

    public String translate(String string){
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public static String translateInnerclass(String string){
        return ChatColor.translateAlternateColorCodes('&',string);
    }

    public void sendTitle(Player player, int fadeIn, int duration, int fadeOut, String message){
        IChatBaseComponent titleJson = new IChatBaseComponent.ChatSerializer().a("{\"text\":}" + translate(message)  +"\"}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,titleJson,fadeIn,duration,fadeOut);
    }

    public void sendSubtitle(Player player, int fadeIn, int duration, int fadeOut, String message){
        IChatBaseComponent titleJson = new IChatBaseComponent.ChatSerializer().a("{\"text\":}" + translate(message)  +"\"}");
        PacketPlayOutTitle title = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,titleJson,fadeIn,duration,fadeOut);

    }

    public void createItemStackFromConfig(){

    }

    public ItemStack createItem(String name, Material material){
        ItemStack item = new org.bukkit.inventory.ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(translate(name));

        item.setItemMeta(meta);

        return item;
    }

    public ItemStack createNewItemWithMeta(String line1C, String line2C, Material material, String displayName, Enchantment enchantment){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> list = new ArrayList<>();
        list.add(this.translate(line1C));
        list.add(this.translate(line2C));

        meta.setDisplayName(this.translate(displayName));
        meta.setLore(list);
        meta.addEnchant(enchantment,1,true);
        meta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);

        itemStack.setItemMeta(meta);

        return itemStack;
    }

    public String getPluginPrefix(){
        return translate(HubExtended.getInstance().getConfig().getString("system.plugin_prefix"));
    }

    public static ArrayList<Location> getLocations(){
        ArrayList<Location> locsList = new ArrayList<>();
        try{
            List<String> locs = HubExtended.getInstance().getConfig().getStringList("minigames.oitc.respawnP");
            for(String s : locs) {
                String[] l = s.split(":");
                Location newL = new Location(Bukkit.getWorld(l[0]), Double.parseDouble(l[1]), Double.parseDouble(l[2]), Double.parseDouble(l[3]));
                locsList.add(newL);
            }
        } catch(Exception e){}
        return locsList;
    }

    public static void saveLocs(ArrayList<Location> locs) {
        List<String> list = new ArrayList<>();
        for(Location l : locs) {
            String toSave = l.getWorld().getName() + ":" + l.getX() + ":" + l.getY() + ":" + l.getZ();
            list.add(toSave);
        }
        HubExtended.getInstance().getConfig().set("minigames.oitc.respawnP",list);
        HubExtended.getInstance().saveConfig();
    }

    public static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }

    public static ItemStack getCustomTextureHead(String value,String displayName) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName(translateInnerclass(displayName));
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        head.setItemMeta(meta);
        return head;
    }

    public static ItemStack getCustomTextureHead(String value,String displayName,String line1, String line2, String line3, String line4, String line5) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setDisplayName(translateInnerclass(displayName));

        List<String> list = new ArrayList<>();
        list.add(translateInnerclass(line1));
        list.add(translateInnerclass(line2));
        list.add(translateInnerclass(line3));
        list.add(translateInnerclass(line4));
        list.add(translateInnerclass(line5));

        meta.setLore(list);

        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
            e.printStackTrace();
        }
        head.setItemMeta(meta);
        return head;
    }
}
