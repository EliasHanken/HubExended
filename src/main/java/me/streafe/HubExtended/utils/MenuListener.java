package me.streafe.HubExtended.utils;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.bungee.BungeeMessageListener;
import me.streafe.HubExtended.gameAccessories.KillEffect;
import me.streafe.HubExtended.gameAccessories.KillEffects;
import me.streafe.HubExtended.gameAccessories.VictoryDances;
import me.streafe.HubExtended.minigames.MinigameCountdownTask;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MenuListener implements Listener {



    @EventHandler
    public void playerInventoryClick(InventoryClickEvent e){


        if(e.getInventory() == null ){
            return;
        }

        if(e.getCurrentItem() == null || !e.getCurrentItem().hasItemMeta()){
            return;
        }



        Player p = (Player) e.getWhoClicked();
        HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(p.getUniqueId());



        if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Settings")){
            Menu menu = new Menu("Settings",36);
            menu.settingsMenu(p);
            e.setCancelled(true);
        }

        else if(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Server Switcher")){
            Menu menu = new Menu("Server Switcher",27);
            menu.serverSwitcher(p);
            e.setCancelled(true);
        }

        if(e.getCurrentItem().getItemMeta().getDisplayName().contains("secret")){
            e.setCancelled(true);
        }

        if(e.getCurrentItem().getItemMeta().getDisplayName().contains("|")){
            e.setCancelled(true);
        }

        if(e.getClickedInventory().getTitle().equalsIgnoreCase("settings")){
            if ((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("sign edit (disabled)"))){
                hubPlayer.signEditEnable = true;
                Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.LEVEL_UP, 1f,1f);
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            }else if ((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("sign edit (enabled)"))){
                hubPlayer.signEditEnable = false;
                Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.LEVEL_UP, 1f,1f);
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
            }else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("rank"))){
                e.setCancelled(true);
            }else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Game Settings"))){
                e.setCancelled(true);
                Menu menu = new Menu("Game Settings",36);
                menu.gameSettings(p);
            }


        }

        if(e.getClickedInventory().getTitle().equalsIgnoreCase("Server Switcher")){
            if ((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Hub"))){
                e.setCancelled(true);
                BungeeMessageListener bungeeMessageListener = new BungeeMessageListener();
                bungeeMessageListener.connectToServer((Player) e.getWhoClicked(),"hub");
            }else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Survival"))){
                e.setCancelled(true);
                p.sendMessage(Utils.translateInnerclass("&cServer soon up!"));
                p.sendMessage(Utils.translateInnerclass("&cAnnouncement when up!"));
                BungeeMessageListener bungeeMessageListener = new BungeeMessageListener();
                bungeeMessageListener.connectToServer((Player) e.getWhoClicked(),"survival");
            }

        }


        if(e.getClickedInventory().getTitle().equalsIgnoreCase("chest open")){
            if ((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("open chest (use 1 token)"))){
                if(hubPlayer.tokens <= 0){
                    Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.BAT_DEATH, 5f,1f);
                    e.setCancelled(true);
                    return;
                }
                hubPlayer.tokens = hubPlayer.tokens-1;
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.setHubPlayer();
                hbConfigSetup.editString("player.tokens",hbConfigSetup.getInt("player.tokens")-1);
                Bukkit.getWorld(p.getLocation().getWorld().getName()).playSound(p.getLocation(), Sound.LEVEL_UP, 5f,1f);
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();

                FireworkUtil fireworkUtil = new FireworkUtil(p,1);
                fireworkUtil.spawnFireWork();
            }
        }

        else if(e.getClickedInventory().getTitle().equalsIgnoreCase("Game Settings")){
            if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("|"))){
                e.setCancelled(true);
            }

            else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Kill Effects"))){
                Menu menu = new Menu("Kill Effects",18);
                menu.killEffects(p);
                e.setCancelled(true);
            }

            else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Victory Dances"))){
                Menu menu = new Menu("Victory Dances",18);
                menu.victoryDances(p);
                e.setCancelled(true);
            }

            else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("Death Noises"))){
                e.setCancelled(true);
            }

            else if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("back"))){
                Menu menu = new Menu("Settings",36);
                menu.settingsMenu(p);
                e.setCancelled(true);
            }

        }
        else if(e.getClickedInventory().getTitle().equalsIgnoreCase("Victory Dances")){

            if((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("back"))){
                Menu menu = new Menu("Game Settings",36);
                menu.gameSettings(p);
                e.setCancelled(true);
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Dragon Rider")){
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.victoryDanceInUse", VictoryDances.DRAGONRIDER.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L,5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34,hbConfigSetup.getVictoryDanceItem(hubPlayer.victoryDances));
                        cancelTask();
                    }
                };

                hubPlayer.victoryDances = VictoryDances.DRAGONRIDER;
                p.playSound(p.getLocation(),Sound.NOTE_STICKS,1f,1f);
                e.setCancelled(true);
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Zombie Rider")){
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.victoryDanceInUse",VictoryDances.ZOMBIERIDER.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L,5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34,hbConfigSetup.getVictoryDanceItem(hubPlayer.victoryDances));
                        cancelTask();
                    }
                };

                hubPlayer.victoryDances = VictoryDances.ZOMBIERIDER;
                p.playSound(p.getLocation(),Sound.NOTE_STICKS,1f,1f);
                e.setCancelled(true);
            }else if(e.getCurrentItem().getItemMeta().getDisplayName().contains("Horse Rider")){
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.victoryDanceInUse",VictoryDances.HORSERIDER.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L,5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34,hbConfigSetup.getVictoryDanceItem(hubPlayer.victoryDances));
                        cancelTask();
                    }
                };

                hubPlayer.victoryDances = VictoryDances.HORSERIDER;
                p.playSound(p.getLocation(),Sound.NOTE_STICKS,1f,1f);
                e.setCancelled(true);

            }

            if(e.getCurrentItem().getItemMeta().getDisplayName().contains("NONE")){
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.victoryDanceInUse",VictoryDances.NONE.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L,5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34,hbConfigSetup.getVictoryDanceItem(hubPlayer.victoryDances));
                        cancelTask();
                    }
                };

                hubPlayer.victoryDances = VictoryDances.NONE;
                p.playSound(p.getLocation(),Sound.NOTE_STICKS,1f,1f);
                e.setCancelled(true);

            }


        }


        else if(e.getClickedInventory().getTitle().equalsIgnoreCase("Kill Effects")) {

            if ((ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("back"))) {
                Menu menu = new Menu("Game Settings", 36);
                menu.gameSettings(p);
                e.setCancelled(true);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Head Rocket")) {
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.killEffectInUse", KillEffects.HEADROCKET.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L, 5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34, hbConfigSetup.getKillEffectItem(hubPlayer.killEffect));
                        cancelTask();
                    }
                };

                hubPlayer.killEffect = KillEffects.HEADROCKET;
                p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
                e.setCancelled(true);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Party Explosion")) {
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.killEffectInUse", KillEffects.PARTYEXPLOSION.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L, 5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34, hbConfigSetup.getKillEffectItem(hubPlayer.killEffect));
                        cancelTask();
                    }
                };

                hubPlayer.killEffect = KillEffects.PARTYEXPLOSION;
                p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
                e.setCancelled(true);
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Blood Explosion")) {
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.killEffectInUse", KillEffects.BLOODEXPLOSION.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L, 5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34, hbConfigSetup.getKillEffectItem(hubPlayer.killEffect));
                        cancelTask();
                    }
                };

                hubPlayer.killEffect = KillEffects.BLOODEXPLOSION;
                p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
                e.setCancelled(true);

            } else if (e.getCurrentItem().getItemMeta().getDisplayName().contains("Final Smash")) {
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.killEffectInUse", KillEffects.FINALSMASH.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L, 5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34, hbConfigSetup.getKillEffectItem(hubPlayer.killEffect));
                        cancelTask();
                    }
                };

                hubPlayer.killEffect = KillEffects.FINALSMASH;
                p.playSound(p.getLocation(), Sound.NOTE_STICKS, 1f, 1f);
                e.setCancelled(true);

            }

            if(e.getCurrentItem().getItemMeta().getDisplayName().contains("NONE")){
                HBConfigSetup hbConfigSetup = new HBConfigSetup(p);
                hbConfigSetup.editString("player.killEffectInUse", KillEffects.NONE.getName());

                MinigameCountdownTask task = new MinigameCountdownTask(0L,5L) {
                    @Override
                    public void run() {
                        e.getClickedInventory().setItem(34,hbConfigSetup.getKillEffectItem(hubPlayer.killEffect));
                        cancelTask();
                    }
                };

                hubPlayer.killEffect = KillEffects.NONE;
                p.playSound(p.getLocation(),Sound.NOTE_STICKS,1f,1f);
                e.setCancelled(true);

            }
        }


    }

    @EventHandler
    public void onPlayerClickItemInHand(PlayerInteractEvent e){
        if(!(e.getPlayer().getItemInHand().hasItemMeta()))return;
        if(e.getPlayer().getItemInHand() == null)return;
        if(!(e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Settings") || e.getPlayer().getItemInHand().getItemMeta().getDisplayName().contains("Server Switcher") )) return;
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_BLOCK){
            e.setCancelled(true);
            if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("Settings")){
                Menu menu = new Menu("Settings",36);

                MinigameCountdownTask task = new MinigameCountdownTask(0L,20L) {
                    @Override
                    public void run() {
                        menu.settingsMenu(e.getPlayer());
                        cancelTask();
                    }
                };
            }
            else if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("Server Switcher")){
                Menu menu = new Menu("Server Switcher",27);

                MinigameCountdownTask task = new MinigameCountdownTask(0L,20L) {
                    @Override
                    public void run() {
                        menu.serverSwitcher(e.getPlayer());
                        cancelTask();
                    }
                };
            }
        }
    }

    @EventHandler
    public void playerDropItem(PlayerDropItemEvent e){
        if(!e.getItemDrop().getItemStack().hasItemMeta()) return;
        if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("Settings")){
            e.setCancelled(true);
        }else if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("One shot one kill")){
            e.setCancelled(true);
        }else if(e.getItemDrop().getItemStack().getItemMeta().getDisplayName().contains("Infinite Arrow")){
            e.setCancelled(true);
        }
    }
}
