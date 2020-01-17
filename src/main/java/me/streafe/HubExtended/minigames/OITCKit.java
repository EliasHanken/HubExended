package me.streafe.HubExtended.minigames;

import me.streafe.HubExtended.HubExtended;
import me.streafe.HubExtended.utils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class OITCKit implements Kit {

    Utils utils = new Utils();

    @Override
    public void setOITCInventory(Player player) {
        ItemMeta meta = oitcBow.getItemMeta();
        meta.setDisplayName(utils.translate(HubExtended.getInstance().getConfig().getString("minigames.oitc.items.bow.name")));
        meta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
        meta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);
        meta.spigot().setUnbreakable(true);
        oitcBow.setItemMeta(meta);

        ItemMeta arrowMeta = arrow.getItemMeta();
        arrowMeta.setDisplayName(utils.translate("&aInfinite Arrow"));
        arrow.setItemMeta(arrowMeta);

        player.getInventory().setItem(0,oitcBow);
        player.getInventory().setItem(8,arrow);
    }
}
