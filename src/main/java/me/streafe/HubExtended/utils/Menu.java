package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.gameAccessories.KillEffects;
import me.streafe.HubExtended.gameAccessories.VictoryDances;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.player_utils.RankEnum;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Menu implements Listener {

    private String name;
    private int size;
    public Inventory inv;
    private HubPlayer hubPlayer;
    private int hubOnline;
    private int survivalOnline;

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
        }else if(hubPlayer.rank == RankEnum.VIP){
            rankMeta.setLore(Arrays.asList(utils.translate("&aVIP")));
        }else if(hubPlayer.rank == RankEnum.ALIEN){
            rankMeta.setLore(Arrays.asList(utils.translate("&5ALIEN")));
        }else if(hubPlayer.rank == RankEnum.ALIENPLUSS){
            rankMeta.setLore(Arrays.asList(utils.translate("&5ALIEN+")));
        }else if(hubPlayer.rank == RankEnum.MODERATOR){
            rankMeta.setLore(Arrays.asList(utils.translate("&3MOD")));
        }else if(hubPlayer.rank == RankEnum.ADMIN){
            rankMeta.setLore(Arrays.asList(utils.translate("&cADMIN")));
        }else if(hubPlayer.rank == RankEnum.CO_OWNER){
            rankMeta.setLore(Arrays.asList(utils.translate("&dCo-Owner")));
        }else if(hubPlayer.rank == RankEnum.OWNER){
            rankMeta.setLore(Arrays.asList(utils.translate("&4Owner")));
        }else if(hubPlayer.rank == RankEnum.DEVELOPER){
            rankMeta.setLore(Arrays.asList(utils.translate("&4DEVELOPER")));
        }
        rankItem.setItemMeta(rankMeta);

        ItemStack gameSettings = utils.createNewItemWithMeta("","&7use this to acces the settings in game",Material.BOW,"&aGame Settings", Enchantment.ARROW_INFINITE);

        for (int i = 0; i<inv.getSize();i++){
            inv.setItem(i,utils.createItem("&c|",Material.STAINED_GLASS_PANE));
        }


        inv.setItem(11,signEdit);
        inv.setItem(13,rankItem);
        inv.setItem(15,gameSettings);
        inv.setItem(31,utils.createItem("&cBack",Material.BED));


    }

    public void serverSwitcher(Player player){
        this.inv = Bukkit.createInventory(null,size,name);
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

        player.openInventory(this.inv);

        for(int i = 0; i < this.inv.getSize();i++){
            this.inv.setItem(i,utils.createItem("&c|",Material.STAINED_GLASS_PANE));
        }


        HubExtended.getInstance().getBungeeChannelApi().getPlayerCount("hub").whenComplete((result,error) -> {
            hubOnline = result;
        });

        this.inv.setItem(10,utils.createNewItemWithMeta("&7Players online: &e"+hubOnline+"","&7Thank you &e"+player.getName()+" &7for playing!",Material.BOOKSHELF,"&a&lHub"));
        HubExtended.getInstance().getBungeeChannelApi().getPlayerCount("survival").whenComplete((result,error) -> {
            survivalOnline = result;
        });
        this.inv.setItem(11,utils.createNewItemWithMeta("&7Players online: &e"+survivalOnline+"","&7Version: &a1.16.1",Material.STICK,"&c&lSurvival"));
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

    public void gameSettings(Player player){
        this.inv = Bukkit.createInventory(null,size,name);
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

        player.openInventory(this.inv);

        for(int i = 0; i<size;i++){
           this.inv.setItem(i,utils.createItem("&c|",Material.STAINED_GLASS_PANE));
        }

        inv.setItem(11,Utils.getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzIxOTYxNjQyZDk4Y2I4MDFhMTc2MDhiYTRhMjMyOTc3YjQ2MmVmNjY3OWI5NzhjOWJiNjQ5NWQxNTE2MjczIn19fQ=="
                ,"&a&lKill Effects"));

        inv.setItem(13,Utils.getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmE3MThlZjc3ODJlNzRmMWRjNDQ1OWYyYmUzYTE0ZjRlMDc1NGNkODdkOGMxNGFmNDQxZGE1ODE1OWEifX19"
                ,"&6&lVictory Dances"));

        inv.setItem(15,Utils.getCustomTextureHead("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTE1OTk5MTI5Mjk3N2Y1YTQ3YjFlNTk4NGFkYTM4MTBlOWY4YWMwNWViM2UzZTc0MzAzNmUwMzM5OTVhMjM0MyJ9fX0="
                ,"&8&lDeath Noises"));

        inv.setItem(31,utils.createItem("&cBack",Material.BED));
    }

    public void killEffects(Player player){
        this.inv = Bukkit.createInventory(null,36,"Kill Effects");
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());


        player.openInventory(this.inv);

        inv.setItem(31,utils.createItem("&cBack",Material.BED));

        inv.setItem(35,utils.createItem("&cNONE",Material.BARRIER));





        for(int i = 0; i<9;i++){
            this.inv.setItem(i,utils.createItem("&c&o&ksecret",Material.STAINED_GLASS_PANE));
        }

        HBConfigSetup hbConfigSetup = new HBConfigSetup(player);
        inv.setItem(34,hbConfigSetup.getKillEffectItem(KillEffects.valueOf(hbConfigSetup.get("player.killEffectInUse"))));

        if(!(hbConfigSetup.getAllKillEffectsItems() == null)){
            for(int i = 0; i < hbConfigSetup.getKillEffectSize(); i++){
                inv.setItem(i,hbConfigSetup.getAllKillEffectsItems().get(i));
            }
        }else {
            return;
        }
    }

    public void victoryDances(Player player){
        this.inv = Bukkit.createInventory(null,36,"Victory Dances");
        this.hubPlayer = HubExtended.getInstance().getHubPlayer(player.getUniqueId());

        player.openInventory(inv);

        inv.setItem(31,utils.createItem("&cBack",Material.BED));

        inv.setItem(35,utils.createItem("&cNONE",Material.BARRIER));

        for(int i = 0; i<9;i++){
            this.inv.setItem(i,utils.createItem("&c&o&ksecret",Material.STAINED_GLASS_PANE));
        }

        HBConfigSetup hbConfigSetup = new HBConfigSetup(player);
        inv.setItem(34,hbConfigSetup.getVictoryDanceItem(VictoryDances.valueOf(hbConfigSetup.get("player.victoryDanceInUse"))));

        if(!(hbConfigSetup.getAllVictoryDanceItems() == null)){
            for(int i = 0; i < hbConfigSetup.getVictoryDanceSize(); i++){
                inv.setItem(i,hbConfigSetup.getAllVictoryDanceItems().get(i));
            }
        }else {
            return;
        }
    }




}
