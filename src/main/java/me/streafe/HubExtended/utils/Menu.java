package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Menu implements Listener {

    private String name;
    private int size;
    public Inventory inv;
    private HubPlayer hubPlayer;

    private ItemStack signEdit;

    Utils utils = new Utils();

    public Menu(String name, int size){
        this.name = name;
        this.size = size;
    }

    public void createMenu(){
        
    }

    public void settingsMenu(Player player){
        this.inv = Bukkit.createInventory(null,size,name);
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

        player.openInventory(this.inv);

        signEdit = new ItemStack(Material.SIGN);
        ItemMeta signMeta = signEdit.getItemMeta();
        if(hubPlayer.signEditEnable){
            signMeta.setDisplayName(utils.translate("&7Sign edit &a(enabled)"));
        }else{
            signMeta.setDisplayName(utils.translate("&7Sign edit &c(disabled)"));
        }
        signEdit.setItemMeta(signMeta);

        inv.setItem(1,signEdit);
    }


}
