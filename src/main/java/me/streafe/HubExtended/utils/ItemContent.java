package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.hub_listeners.JoinListener;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ItemContent {

    private static ItemStack[] invContent;
    private static Player player;
    private static String invStringConfig;

    public ItemContent(Player player) {
        this.player = player;
    }


    public void savePlayerInventory(Player p) {

        try {
            String base64 = inventoryToBase64(p.getInventory());
            //Save this string to file.
            HBConfigSetup hbConfigSetup = new HBConfigSetup(player);
            hbConfigSetup.editString("player.inventory","");
            hbConfigSetup.editString("player.inventory",base64);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Could not convert to base64, do not save
    }

    public void savePlayerInventoryBackup(Player p) {

        try {
            String base64 = inventoryToBase64(p.getInventory());
            //Save this string to file.
            HBConfigSetup hbConfigSetup = new HBConfigSetup(player);
            hbConfigSetup.editString("player.inventoryBackup","");
            hbConfigSetup.editString("player.inventoryBackup",base64);
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }

        //Could not convert to base64, do not save
    }

    public ItemStack[] getSavedPlayerInventory(String data) {

        try {
            return inventoryFromBase64(data).getContents();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ItemStack[0];
    }

    public static String inventoryToBase64(Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            dataOutput.writeInt(inventory.getSize());

            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }

            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());

            //Converts the inventory and its contents to base64, This also saves item meta-data and inventory type
        } catch (Exception e) {
            throw new IllegalStateException("Could not convert inventory to base64.", e);
        }
    }

    public static Inventory inventoryFromBase64(String data) throws IOException {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            CraftInventoryCustom inventory = new CraftInventoryCustom(null, dataInput.readInt());

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }

            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new IOException("Could not decode inventory.", e);
        }
    }
}

