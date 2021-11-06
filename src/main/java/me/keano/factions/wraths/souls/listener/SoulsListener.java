package me.keano.factions.wraths.souls.listener;

import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.wraths.souls.utils.SoulsUtils;
import me.keano.factions.wraths.utils.WrathUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class SoulsListener implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack item = e.getCurrentItem();

        if (!e.getClick().equals(ClickType.LEFT)) return;
        if (cursor == null) return;

        if (EnchantUtils.verifyLore(cursor)) {

            if (item == null || item.getType().equals(Material.AIR)) return;

            if (!EnchantUtils.verifyLore(item)) return;
            if (!EnchantUtils.verifyLore(cursor)) return;
            if (WrathUtils.getWrathOn(item) == null) return;
            if (!SoulsUtils.isSoulItem(cursor)) return;

            WrathUtils.setSouls(item, SoulsUtils.getSouls(cursor));

            if (player.getItemOnCursor().getAmount() > 1) {
                ItemStack itemStack = player.getItemOnCursor();
                itemStack.setAmount(itemStack.getAmount() - 1);
                player.setItemOnCursor(itemStack);
            } else {
                player.setItemOnCursor(new ItemStack(Material.AIR));
            }

            e.setCancelled(true);
        }

    }
}