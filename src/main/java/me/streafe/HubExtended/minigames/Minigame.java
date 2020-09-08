package me.streafe.HubExtended.minigames;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.gameAccessories.GameAccessoriesHandler;
import me.streafe.HubExtended.hub_listeners.DuelGame;
import me.streafe.HubExtended.player_utils.HBConfigSetup;
import me.streafe.HubExtended.player_utils.HubPlayer;
import me.streafe.HubExtended.utils.FireworkUtil;
import me.streafe.HubExtended.utils.PacketUtils;
import me.streafe.HubExtended.utils.TextBuilder;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import javax.rmi.CORBA.Util;
import java.util.*;

public class Minigame implements Listener {

    private int maxPlayers;
    private String gameID;
    public List<HubPlayer> playerList;
    public Player owner;
    private MinigameType type;
    public GameState gameState = GameState.LOBBY;
    private Location spawnLoc;
    public int playerAmount;
    public MinigameType minigameType;
    public HubPlayer winner;
    private Map<UUID, ItemStack[]> playerInv;
    public Map<UUID,Integer> playerScores;
    public int skywarsPlayersAlive;
    public List<UUID> skywarsPlayers;

    Utils utils = new Utils();


    public Minigame(int maxPlayers, MinigameType type){
        setRandomID();
        this.setPlayerList(new ArrayList<>());
        this.setType(type);
        this.setMaxPlayers(maxPlayers);
        this.minigameType = type;
        playerList = new ArrayList<>();
        skywarsPlayers = new ArrayList<>();
    }

    public Minigame(){

    }



    public void startGame(){
        for(HubPlayer players : getPlayerList()) {
            this.gameState = GameState.STARTED;
            players.setInGame(true);
            players.gamePoints = 0;
            PacketUtils.sendTitle(Bukkit.getPlayer(players.getUUID()), "Game started " + utils.translate("&7" + minigameType), ChatColor.GREEN);
        }

            if(minigameType == MinigameType.OITC){
                OITCKit kit = new OITCKit();
                startOITC();
                for(HubPlayer players : getPlayerList()){
                    kit.setOITCInventory(Bukkit.getPlayer(players.getUUID()));
                }

            }

            else if(minigameType == MinigameType.SKYWARS){

                for(int i = 0; i<playerAmount;i++){
                    skywarsPlayers.add(getPlayerList().get(i).getUUID());
                    skywarsPlayersAlive+=1;
                }
                this.gameState = GameState.STARTED;
                for(HubPlayer players : getPlayerList()){
                    players.setInGame(true);
                    players.gamePoints = 0;
                    PacketUtils.sendTitle(Bukkit.getPlayer(players.getUUID()),"Game started " + utils.translate("&7"+minigameType), ChatColor.GREEN);

                }
                startSkyWars();
            }




    }


    /*
    @EventHandler
    public void damage(PlayerDeathEvent ev){
        if(ev.getEntity() != null){

            if((HubExtended.getInstance().getMinigameByID(HubExtended.getInstance().getHubPlayer(ev.getEntity().getUniqueId()).getGameID()).minigameType == MinigameType.SKYWARS)) {
                GameAccessoriesHandler gah = new GameAccessoriesHandler(ev.getEntity().getKiller());
                gah.playKillEffect(ev.getEntity().getLocation().add(0, 1, 0));

                HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(ev.getEntity().getUniqueId());
                skywarsPlayers.remove(hubPlayer.getUUID());
                skywarsPlayersAlive-=1;

                HubPlayer hubPlayerKiller = HubExtended.getInstance().getHubPlayer(ev.getEntity().getKiller().getUniqueId());
                hubPlayerKiller.gamePoints += 1;

            }
        }


    }

     */

    /*
    @EventHandler
    public void damagePlayerKillEffect(PlayerDeathEvent ev){
        if(ev.getEntity() != null){
            GameAccessoriesHandler gah = new GameAccessoriesHandler(ev.getEntity().getKiller());
            gah.playKillEffect(ev.getEntity().getLocation().add(0, 1, 0));

        }


    }

     */

    /*
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e){
        if((HubExtended.getInstance().getMinigameByID(HubExtended.getInstance().getHubPlayer(e.getPlayer().getUniqueId()).getGameID()).minigameType == MinigameType.SKYWARS)){
            if(!(HubExtended.getInstance().getMinigameByID(HubExtended.getInstance().getHubPlayer(e.getPlayer().getUniqueId()).getGameID()).gameState == GameState.LOBBY)){
                e.getPlayer().teleport(HubExtended.getInstance().getLobbyLocation());
            }else{
                e.getPlayer().setGameMode(GameMode.ADVENTURE);
            }
        }
    }

     */

    @EventHandler
    public void onPlayerShootProjectile(EntityDamageByEntityEvent e){

        if(e.getEntity() instanceof Monster || e.getEntity() instanceof Animals) return;
        if(e.getEntity() instanceof NPC) return;

        if(HubExtended.getInstance().getMinigameByID( HubExtended.getInstance().getHubPlayer(e.getEntity().getUniqueId()).getGameID() ) == null)return;


        if((HubExtended.getInstance().getMinigameByID(HubExtended.getInstance().getHubPlayer(e.getEntity().getUniqueId()).getGameID()).minigameType == MinigameType.OITC)){
            if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Arrow)) return;

            Entity entity = e.getEntity();
            Arrow arrow = (Arrow) e.getDamager();

            if(!(arrow.getShooter() instanceof Player)) return;

            Player player = (Player) arrow.getShooter();

            if(entity instanceof Player){
                if(entity.getUniqueId() == ((Player) arrow.getShooter()).getUniqueId()){
                    entity.sendMessage(utils.translate("&cDon't shoot your self"));
                    return;
                }
                if(e.getDamager() instanceof Arrow){


                /*
                UUID uuid = e.getEntity().getUniqueId();

                HubPlayer shooterH = HubExtended.getInstance().getHubPlayer(uuid);
                HubPlayer hitPlayerH = HubExtended.getInstance().getHubPlayer(hitPlayer.getUniqueId());

                 */
                    GameAccessoriesHandler gah = new GameAccessoriesHandler(player);
                    gah.playKillEffect(arrow.getLocation());

                    arrow.remove();

                    int getIndex = Utils.getRandomNumberInRange(1, Utils.getLocations().size() -1);
                    HubPlayer hubPlayerShooter = HubExtended.getInstance().getHubPlayer(player.getUniqueId());
                    hubPlayerShooter.gamePoints += 1;
                    hubPlayerShooter.sendMessage(utils.translate("&a[+1] &7for killing &o&a" + entity.getName()));

                    entity.teleport(Utils.getLocations().get(getIndex));

                /*
                if(hubPlayerShooter.gamePoints >= 1){
                    hubPlayerShooter.getGame().winner = hubPlayerShooter;
                    endGame();
                }

                 */

                /*
                ((Player) entity).playSound(entity.getLocation(),Sound.CAT_PURREOW,1f,1f);
                 */

                /*
                player.playSound(player.getLocation(), Sound.VILLAGER_YES,1f,1f);


                 */
                    e.setCancelled(true);
                }
            }
        }



        /*
        if(!shooterH.inGame && !hitPlayerH.inGame)return;
        if(HubExtended.getInstance().getMinigameByID(shooterH.getGameID()).gameState != GameState.STARTED && HubExtended.getInstance().getMinigameByID(hitPlayerH.getGameID()).gameState != GameState.STARTED) return;

        hitPlayer.teleport(HubExtended.getInstance().getLobbyLocation());
        shooter.sendMessage(utils.translate("&+1 point for killing &c&o") + hitPlayer.getName() + "§a" + shooterH.gamePoints);
        shooterH.gamePoints += 1;

        if(shooterH.gamePoints >= 10){
            gameState = GameState.FINISHED;
        }

         */
    }

    @EventHandler
    public void onPlayerHurt(EntityDamageByEntityEvent e){


        /*
        if(!(e.getEntity() instanceof Player)){
            if(e.getCause() != EntityDamageEvent.DamageCause.PROJECTILE)return;

            Projectile projectile = (Projectile) e.getDamager();

            if((projectile.getShooter() instanceof Player) && (e.getEntity() instanceof Player)){
                ((Player) projectile.getShooter()).sendMessage(utils.translate("&a+1 point for killing &c&o"+e.getEntity()));

                e.getEntity().teleport(HubExtended.getInstance().getLobbyLocation());
            }
        }
         */
        if(!e.getEntity().getWorld().getName().equalsIgnoreCase("world"))return;
        if(!e.getDamager().getWorld().getName().equalsIgnoreCase("world"))return;
        if(e.getDamager() instanceof Player){
            HubPlayer hubPlayer = HubExtended.getInstance().getHubPlayer(e.getDamager().getUniqueId());
            if(((Player) e.getDamager()).getPlayer().isOp()){
                e.setCancelled(false);
                return;
            }

            for(DuelGame duelGame : HubExtended.getInstance().getDuelGamesList()){
                if(!(duelGame.opponent == e.getDamager() || duelGame.host == e.getDamager())){
                    e.setCancelled(true);
                }else{
                    e.setCancelled(false);
                    return;
                }
            }

            if(!hubPlayer.inGame){
                for(DuelGame duelGame : HubExtended.getInstance().getDuelGamesList()){
                    if(!(duelGame.opponent == e.getDamager() || duelGame.host == e.getDamager())){
                        e.setCancelled(true);
                        hubPlayer.sendMessage(utils.translate("&c(!) &7no pvp!"));
                        return;
                    }
                }
            }




            if(hubPlayer.inGame){
                if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).gameState == GameState.STARTED){
                    if(HubExtended.getInstance().getMinigameByID(hubPlayer.getGameID()).minigameType == MinigameType.OITC){
                        if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK){
                            e.setCancelled(true);
                        }else{
                            e.setCancelled(false);
                        }
                    }else{
                        e.setCancelled(false);
                    }


                }
                else{
                    e.setCancelled(true);
                    hubPlayer.sendMessage(utils.translate("&c(!) &7no pvp!"));
                }
            }
        }else{
            e.getDamager().remove();
            e.setCancelled(true);
        }






    }

    public void startOITC(){
        MinigameCountdownTask oitc = new MinigameCountdownTask(0L,10L) {
            @Override
            public void run() {

                if(winner != null){
                    endGame();
                    gameState = GameState.LOBBY;
                    cancelTask();
                }

                for(int i = 0; i < playerAmount ; i++){
                    HubPlayer hubPlayers = playerList.get(i);
                    if(hubPlayers.gamePoints >= 25){
                        winner = hubPlayers;
                        HBConfigSetup hbConfigSetup = new HBConfigSetup(hubPlayers.getPlayer());
                        hbConfigSetup.editString("player.wins",hubPlayers.wins + 1);
                    }
                }


            }
        };
    }

    public void startSkyWars(){
        MinigameCountdownTask skywars = new MinigameCountdownTask(0L,10L) {
            @Override
            public void run() {

                if(winner != null){
                    endGame();
                    gameState = GameState.LOBBY;
                    cancelTask();
                }

                if(skywarsPlayersAlive <= 1){
                    winner = HubExtended.getInstance().getHubPlayer(skywarsPlayers.get(0));
                    HBConfigSetup hbConfigSetup = new HBConfigSetup(winner.getPlayer());
                    hbConfigSetup.editString("player.wins",hbConfigSetup.getInt("player.wins") + 1);
                }

            }
        };
    }

    public void gameEnd(){

    }



    public void endGame(){
        for(int i = 0; i<playerAmount;i++){
            getPlayerList().get(i).sendMessage(utils.translate("&cGame End!"));
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                for(HubPlayer players : getPlayerList()){
                    Bukkit.getPlayer(players.getUUID()).teleport(HubExtended.getInstance().getLobbyLocation());
                    String winnerPlayer;
                    if(winner != null){
                       winnerPlayer = winner.getName();
                    }else{
                        winnerPlayer = "?";
                    }
                    PacketUtils.sendTitle(Bukkit.getPlayer(players.getUUID()),winnerPlayer + " won the game!", ChatColor.RED);
                    gameState = GameState.LOBBY;
                    players.setInGame(true);
                    players.gamePoints = 0;
                    players.getPlayer().getInventory().clear();
                    winner = null;
                    skywarsPlayersAlive = 0;
                }
            }
        }.runTaskLaterAsynchronously(HubExtended.getInstance(),1L);
    }

    public void addNewPlayer(Player player){
        this.getPlayerList().add(HubExtended.getInstance().getHubPlayer(player.getUniqueId()));
        playerAmount++;
    }

    public void removePlayer(Player player){
        this.getPlayerList().remove(HubExtended.getInstance().getHubPlayer(player.getUniqueId()));
        playerAmount--;
    }

    public void setType(MinigameType type){
        this.minigameType = type;
    }

    public void setOwner(Player player){
        this.owner = player;
    }

    public Player getOwner(){
        if(this.owner != null){
            return this.owner;
        }
        return null;
    }

    private void setRandomID(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 5;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();

        this.setGameID(generatedString);
    }

    public String getGameID(){
        if(this.gameID != null){
            return this.gameID;
        }
        return null;
    }

    public ArrayList<HubPlayer> getPlayerList(){
        return (ArrayList<HubPlayer>) this.playerList;
    }

    public List<String> getPlayerListString(){
        List<String> toRealName = new ArrayList<>();
        for(int i = 0; i < playerList.size(); i++){
            toRealName.add(playerList.get(i).getName());
        }
        return toRealName;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public void setGameID(String gameID) {
        this.gameID = gameID;
    }

    public void setPlayerList(List<HubPlayer> playerList) {
        this.playerList = playerList;
    }

    public MinigameType getType() {
        return type;
    }

    public boolean isStarted() {
        if(this.gameState == GameState.STARTED || this.gameState == GameState.FINISHED){
            return true;
        }
        return false;
    }


    public ItemStack[] getPlayerInv(UUID uuid) {
        return this.playerInv.get(uuid);
    }

    public void addPlayerInv(UUID uuid, ItemStack[] inv) {
        this.playerInv.put(uuid,inv);
    }
}
