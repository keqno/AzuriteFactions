package me.keano.factions.kits.listener;

import me.keano.factions.Factions;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.kits.KitEnchant;
import me.keano.factions.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class KitEnchantListener implements Listener {

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) return;

        Player player = (Player) e.getWhoClicked();
        ItemStack cursor = e.getCursor();
        ItemStack item = e.getCurrentItem();

        if (!e.getClick().equals(ClickType.LEFT)) return;
        if (cursor == null) return;

        for (KitEnchant enchant : Factions.getInstance().getKitHandler().getKitEnchants().values()) {
            if (EnchantUtils.verifyLore(cursor) && enchant.getBookItem().getItemMeta().getLore()
                    .containsAll(cursor.getItemMeta().getLore())) {

                if (item == null || item.getType().equals(Material.AIR)) return;

                ItemBuilder itemBuilder = new ItemBuilder(item);
                itemBuilder.addLoreLine(cursor.getItemMeta().getDisplayName());
                itemBuilder.setLore(EnchantUtils.rearrangeLore(itemBuilder.toItemStack().getItemMeta().getLore()));
                e.setCurrentItem(itemBuilder.toItemStack());

                if (player.getItemOnCursor().getAmount() > 1) {
                    ItemStack itemStack = player.getItemOnCursor();
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    player.setItemOnCursor(itemStack);
                } else {
                    player.setItemOnCursor(null);
                }

                e.setCancelled(true);
            }
        }
    }
}
