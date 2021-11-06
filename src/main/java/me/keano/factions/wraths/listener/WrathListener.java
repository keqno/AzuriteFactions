package me.keano.factions.wraths.listener;

import me.keano.factions.Factions;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ItemBuilder;
import me.keano.factions.wraths.Wrath;
import me.keano.factions.wraths.utils.WrathUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class WrathListener implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack item = e.getCurrentItem();

        if (!e.getClick().equals(ClickType.LEFT)) return;
        if (cursor == null) return;

        for (Wrath wrath : Factions.getInstance().getWrathHandler().getWraths()) {
            if (EnchantUtils.verifyLore(cursor) && wrath.getBookItem().getItemMeta().getLore()
                    .containsAll(cursor.getItemMeta().getLore())) {

                if (item == null || item.getType().equals(Material.AIR)) return;

                if (!EnchantUtils.isEnchantable(item, wrath.getType())) {
                    player.sendMessage(CC.t("&cYou cannot put a wrath on this item!"));
                    return;
                }

                if (WrathUtils.getWrathOn(item) != null) {
                    player.sendMessage(CC.t("&cYou are only able to use 1 wrath per item."));
                    return;
                }

                ItemBuilder ib = new ItemBuilder(item);
                ib.addLoreLine("&c&l&o- Wrath Of " + wrath.getName() + " &70");
                ib.toItemStack().getItemMeta().setLore(EnchantUtils.rearrangeLore(ib.toItemStack().getItemMeta().getLore()));
                e.setCurrentItem(ib.toItemStack());

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
}