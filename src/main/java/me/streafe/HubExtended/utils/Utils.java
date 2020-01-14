package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Utils {

    public String translate(String string){
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

    public ItemStack createNewItemWithMeta(String line1C, String line2C, Material material, String displayName){
        ItemStack itemStack = new ItemStack(material);
        ItemMeta meta = itemStack.getItemMeta();
        List<String> list = new ArrayList<>();
        list.add(this.translate(line1C));
        list.add(this.translate(line2C));

        meta.setDisplayName(this.translate(displayName));
        meta.setLore(list);

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
}
