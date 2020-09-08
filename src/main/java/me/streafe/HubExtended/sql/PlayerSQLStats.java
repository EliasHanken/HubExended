package me.streafe.HubExtended.sql;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PlayerSQLStats {

    private final Player player;
    private final SQL_Class sql_class;

    public PlayerSQLStats(Player player, SQL_Class sql_class){
        this.player = player;
        this.sql_class = sql_class;
    }

    public void givePlayerTopTenPlayersBook(){
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setAuthor("Server");
        bookMeta.setTitle(Utils.translateInnerclass("&4&lLeaderboard"));
        ArrayList<String> pages = new ArrayList<>();

        String leaderboard = Utils.translateInnerclass("&a&l&nSeason 1\n");

        try {
            PreparedStatement statement = sql_class.getConnection().prepareStatement("SELECT name, elo FROM `clubplayers` ORDER BY elo DESC");
            ResultSet rs = statement.executeQuery();
            int i = 1;
            while(rs.next()){
                if(i>=10){
                    break;
                }
                leaderboard += (Utils.translateInnerclass("&4&l"+rs.getString("elo") + " &r&0- &9&l" + rs.getString("name")) + "\n");
                i++;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        pages.add(0, leaderboard);
        bookMeta.setPages(pages);

        book.setItemMeta(bookMeta);
        this.player.getInventory().addItem(book);

    }
}
