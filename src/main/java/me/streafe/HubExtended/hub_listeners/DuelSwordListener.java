package me.streafe.HubExtended.hub_listeners;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class DuelSwordListener implements Listener {

    private Utils utils = new Utils();

    @EventHandler
    public void onPlayerUseSword(PlayerInteractAtEntityEvent e){
        if(!(e.getRightClicked() instanceof Player))return;
        if(e.getPlayer().getItemInHand() == null) return;
        else if(ChatColor.stripColor(e.getPlayer().getItemInHand().getItemMeta().getDisplayName()).contains("Duel Player")){
            /*
            if(HubExtended.getInstance().getDuelGamePlayer(e.getPlayer()).alreadyHost){
                for(DuelGame duelGame : HubExtended.getInstance().getDuelGamesList()){
                    if(duelGame.host == e.getPlayer()){
                        if(!duelGame.started){
                            duelGame.deleteDuelGameInstance();
                            DuelGame newDuelGame = new DuelGame(e.getPlayer(), (Player) e.getRightClicked());
                            newDuelGame.createDuelGameInstance();
                            newDuelGame.sendDuelInviteToPlayer();
                            return;
                        }
                    }
                }
            }else if(HubExtended.getInstance().getDuelGamePlayer(e.getPlayer()).inStartedGame){
                e.getPlayer().sendMessage(utils.translate("&cAlready in game!"));
                return;
            }else if(HubExtended.getInstance().getDuelGamePlayer((Player) e.getRightClicked()).invitedByPlayer == e.getPlayer()){
                e.getPlayer().sendMessage(utils.translate("&eYou have already invited " + ((Player) e.getRightClicked()).getDisplayName()));
                return;
            }else if(HubExtended.getInstance().getDuelGamePlayer((Player) e.getRightClicked()).invitedByPlayer != null){
                Player opponent = HubExtended.getInstance().getDuelGamePlayer((Player) e.getRightClicked());
                e.getPlayer().sendMessage(utils.translate("&e"+opponent.getDisplayName() + " already has a duel invite incoming, \nwait for them to respond"));
                return;
            }
            */
            Player opponent = (Player) e.getRightClicked();
            DuelGame newDuelGame = new DuelGame(e.getPlayer(), opponent);
            newDuelGame.createDuelGameInstance();
        }
    }
}
