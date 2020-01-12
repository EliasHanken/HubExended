package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.player_utils.RankEnum;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu implements Listener {

    private String name;
    private int size;
    public Inventory inv;
    private HubPlayer hubPlayer;

    private ItemStack signEdit, openLuckyChestItem, rankItem;

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

        rankItem = new ItemStack(Material.IRON_SWORD);
        ItemMeta rankMeta = rankItem.getItemMeta();
        rankMeta.setDisplayName(utils.translate("&7Rank"));
        rankMeta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);

        if(hubPlayer.rank == RankEnum.MEMBER){
            rankMeta.setLore(Arrays.asList(utils.translate("&7Member")));
        }else if(hubPlayer.rank == RankEnum.MODERATOR){
            rankMeta.setLore(Arrays.asList(utils.translate("&3Moderator")));
        }else if(hubPlayer.rank == RankEnum.ADMIN){
            rankMeta.setLore(Arrays.asList(utils.translate("&cAdmin")));
        }else if(hubPlayer.rank == RankEnum.CO_OWNER){
            rankMeta.setLore(Arrays.asList(utils.translate("&dCo-Owner")));
        }else if(hubPlayer.rank == RankEnum.OWNER){
            rankMeta.setLore(Arrays.asList(utils.translate("&4Owner")));
        }else if(hubPlayer.rank == RankEnum.DEVELOPER){
            rankMeta.setLore(Arrays.asList(utils.translate("&4Owner")));
        }
        rankItem.setItemMeta(rankMeta);

        inv.setItem(0,signEdit);
        inv.setItem(1,rankItem);


    }

    public void luckyChest(Player player){
        this.inv = Bukkit.createInventory(null,size,name);
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

        player.openInventory(this.inv);

        this.openLuckyChestItem = new ItemStack(Material.GREEN_RECORD);

        ItemMeta luckyMeta = this.openLuckyChestItem.getItemMeta();
        luckyMeta.setDisplayName(utils.translate("&aOpen Chest &8(use 1 token)"));
        luckyMeta.getItemFlags().clear();
        List<String> lore = new ArrayList<>();
        lore.add(utils.translate("&7tokens left: &a") + hubPlayer.tokens);
        luckyMeta.setLore(lore);

        this.openLuckyChestItem.setItemMeta(luckyMeta);

        this.inv.setItem(0,openLuckyChestItem);

    }


}
