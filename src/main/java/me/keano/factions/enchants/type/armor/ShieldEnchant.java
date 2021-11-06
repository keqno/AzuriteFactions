package me.keano.factions.enchants.type.armor;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ShieldEnchant extends Enchant {

    public ShieldEnchant() {
        super(
                "Shield",
                5,
                "Armor",
                "Has a chance to set the damage dealt to you to 0.",
                1,
                1
        );
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!EnchantUtils.isPlayer(e.getDamager(), e.getEntity())) return;

        Player damaged = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        ItemStack[] itemStacks = damaged.getInventory().getArmorContents();

        if (FactionUtils.isTeamMate(damager, damaged)) return;

        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null) continue;
            if (!EnchantUtils.verifyLore(itemStack)) continue;

            for (String string : itemStack.getItemMeta().getLore()) {
                if (!string.contains(getName())) continue;
                if (!chanceSuccessful(getLevel(string))) continue;

                e.setDamage(0);
                sendActivateMessage(damaged, getDisplayName(itemStack));
            }
        }
    }
}
