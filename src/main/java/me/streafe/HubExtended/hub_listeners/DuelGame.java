package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.bungee.BungeeChannelApi;
import me.streafe.HubExtended.bungee.BungeeMessageListener;
import me.streafe.HubExtended.gameAccessories.GameAccessoriesHandler;
import me.streafe.HubExtended.minigames.MinigameCountdownTask;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.utils.PacketUtils;
import me.streafe.HubExtended.utils.Utils;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.*;
import org.bukkit.util.Vector;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;

public class DuelGame implements Listener {

    public Player host;
    public Player opponent;
    private Utils utils = new Utils();
    public boolean started = false;

    public DuelGame(){}

    public DuelGame(Player host, Player opponent){
        this.opponent = opponent;
        this.host = host;
    }

    public void sendDuelInviteToPlayer(){
        TextComponent invite = new TextComponent("[Click to accept]");
        invite.setColor(ChatColor.AQUA);
        invite.setBold(true);
        invite.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duels accept"));
        invite.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Click here to accept the invite \nfrom "+host.getName()+"").color(ChatColor.YELLOW).create()));
        TextComponent invite2 = new TextComponent("[Click to decline]");
        invite2.setColor(ChatColor.RED);
        invite2.setBold(true);
        invite2.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/duels decline"));
        invite2.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new ComponentBuilder("Click here to decline the invite \nfrom "+host.getName()+"").color(ChatColor.YELLOW).create()));
        opponent.sendMessage(utils.translate("&aDuel invitation:"));
        opponent.spigot().sendMessage(invite);
        opponent.spigot().sendMessage(invite2);
        host.sendMessage(utils.translate("&eDuel invite sent to " + opponent.getName()));
    }

    public void acceptDuel(){
        startDuelGame();
    }

    public void declineDuel(){
        host.sendMessage(utils.translate(opponent.getDisplayName() + " &edeclined the duel"));
        deleteDuelGameInstance();
    }

    private void startDuelGame(){
        if(started){
            opponent.sendMessage(utils.translate("&cYou can't start a game that is already started!"));
            return;
        }
        started = true;
        host.teleport(utils.getLocation(HubExtended.getInstance().getConfig().getString("duels.spawn1")));
        opponent.teleport(utils.getLocation(HubExtended.getInstance().getConfig().getString("duels.spawn2")));

        MinigameCountdownTask task = new MinigameCountdownTask(0L,20L) {
            int i = 5;
            @Override
            public void run() {
                if(i==0){
                    cancelTask();

                    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
                    ItemMeta swordMeta = sword.getItemMeta();
                    swordMeta.addEnchant(Enchantment.DAMAGE_ALL,2,true);
                    swordMeta.setDisplayName(utils.translate("&cDuels sword"));
                    swordMeta.addEnchant(Enchantment.FIRE_ASPECT,2,true);
                    sword.setItemMeta(swordMeta);

                    ItemStack heal = new ItemStack(Material.POTION, 1);
                    Potion pot = new Potion(1);
                    pot.setType(PotionType.INSTANT_HEAL);
                    pot.setSplash(true);
                    pot.apply(heal);

                    for(int i = 0; i < opponent.getInventory().getSize(); i++){
                        opponent.getInventory().setItem(i,heal);
                        host.getInventory().setItem(i,heal);
                    }

                    host.getInventory().setItem(0,sword);
                    opponent.getInventory().setItem(0,sword);

                    ItemStack potion = new ItemStack(Material.POTION);
                    PotionMeta meta = (PotionMeta) potion.getItemMeta();
                    meta.setMainEffect(PotionEffectType.FIRE_RESISTANCE);
                    potion.setItemMeta(meta);

                    ItemStack potion2 = new ItemStack(Material.POTION);
                    PotionMeta meta2 = (PotionMeta) potion2.getItemMeta();
                    meta2.setMainEffect(PotionEffectType.SPEED);
                    potion2.setItemMeta(meta);

                    host.getInventory().setItem(1,potion);
                    opponent.getInventory().setItem(1,potion);
                    host.getInventory().setItem(2,potion2);
                    opponent.getInventory().setItem(2,potion2);
                    host.getInventory().setItem(3,new ItemStack(Material.ENDER_PEARL,16));
                    opponent.getInventory().setItem(3,new ItemStack(Material.ENDER_PEARL,16));

                    host.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    host.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    host.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    host.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

                    opponent.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    opponent.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    opponent.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    opponent.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

                    for(ItemStack item : opponent.getInventory().getArmorContents()){
                        ItemMeta meta1 = item.getItemMeta();
                        meta1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2,true);
                        item.setItemMeta(meta1);
                    }
                    for(ItemStack item : host.getInventory().getArmorContents()){
                        ItemMeta meta1 = item.getItemMeta();
                        meta1.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,2,true);
                        item.setItemMeta(meta1);
                    }

                }
                PacketUtils.sendTitle(host,i+"", org.bukkit.ChatColor.YELLOW);
                PacketUtils.sendTitle(opponent,i+"", org.bukkit.ChatColor.YELLOW);
                i--;
                host.teleport(utils.getLocation(HubExtended.getInstance().getConfig().getString("duels.spawn1")));
                opponent.teleport(utils.getLocation(HubExtended.getInstance().getConfig().getString("duels.spawn2")));
            }
        };
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e){

        Player player = e.getPlayer();

        Material m = e.getPlayer().getLocation().getBlock().getType();
        if(m == Material.STATIONARY_WATER || m == Material.WATER){
            if(e.getPlayer().getLocation().getBlockZ() == -240){
                BungeeMessageListener bungeeMessageListener = new BungeeMessageListener();
                bungeeMessageListener.connectToServer(e.getPlayer(),"survival");
                player.teleport(HubExtended.getInstance().getHubLocation());
                player.playSound(player.getLocation(), Sound.PORTAL_TRAVEL,1f,1f);
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 40, 100));
            }

        }


        if(e.getPlayer().getLocation().getY() < 10){
            player.teleport(HubExtended.getInstance().getHubLocation());
            player.setHealth(20d);
        }
    }

    public void createDuelGameInstance(){
        if(HubExtended.getInstance().duelGameHost(host)){
            Player invited = null;
            for(DuelGame duelGame : HubExtended.getInstance().getDuelGamesList()){
                if(duelGame.host == host){
                    invited = duelGame.opponent;
                }
            }
            host.sendMessage(utils.translate("&eYou have already invited " + invited.getDisplayName()));
            return;
        }if(HubExtended.getInstance().duelGameOpponent(opponent)){
            host.sendMessage(utils.translate("&e"+opponent.getDisplayName() + " already has a duel invite incoming, \nwait for them to respond"));
            return;
        }
        HubExtended.getInstance().getDuelGamesList().add(new DuelGame(this.host,this.opponent));
        sendDuelInviteToPlayer();
    }

    public void deleteDuelGameInstance(){
        HubExtended.getInstance().getDuelGamesList().clear();
    }

    @EventHandler
    public void onPlayerDamagePlayer(EntityDamageByEntityEvent e){
        if(!(e.getDamager() instanceof Player)) return;
        if(e.getEntity() instanceof Player){
            Player killed = (Player) e.getEntity();
            Player killer = (Player) e.getDamager();
            if(e.getFinalDamage() >= killed.getHealth()){
                killed.setHealth(20);
                GameAccessoriesHandler gameAccessoriesHandler = new GameAccessoriesHandler(killer);
                gameAccessoriesHandler.playKillEffect(killed.getLocation());
                killed.teleport(HubExtended.getInstance().getHubLocation());
                HubExtended.getInstance().getServer().broadcastMessage(utils.translate(killed.getDisplayName() + " &e was killed by " + killer.getDisplayName()));
                killed.setVelocity(new Vector(0,0,0));
                killed.setHealth(20);
                MinigameCountdownTask countdownTask = new MinigameCountdownTask(5L,20L) {
                    @Override
                    public void run() {
                        killed.setHealth(20);
                        cancelTask();
                    }
                };
            }
        }

    }





}
