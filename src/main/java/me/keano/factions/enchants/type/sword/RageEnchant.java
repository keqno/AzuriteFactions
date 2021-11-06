package me.keano.factions.enchants.type.sword;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class RageEnchant extends Enchant {

    public RageEnchant() {
        super(
                "Rage",
                5,
                "Sword",
                "Has a chance to do more damage in combat.",
                4
        );
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!EnchantUtils.isPlayer(e.getDamager(), e.getEntity())) return;

        Player damager = (Player) e.getDamager();
        Player damaged = (Player) e.getEntity();
        ItemStack item = damager.getItemInHand();

        if (FactionUtils.isTeamMate(damager, damaged)) return;
        if (item == null) return;
        if (!EnchantUtils.verifyLore(item)) return;

        for (String string : item.getItemMeta().getLore()) {
            if (!string.contains(getName())) continue;
            if (!chanceSuccessful(getLevel(string))) continue;

            switch (getLevel(string)) {
                case 1:
                    EnchantUtils.takeHealth(damaged, 1.0);
                    break;
                case 2:
                    EnchantUtils.takeHealth(damaged, 1.5);
                    break;
                case 3:
                    EnchantUtils.takeHealth(damaged, 2.0);
                    break;
                case 4:
                    EnchantUtils.takeHealth(damaged, 2.5);
                    break;
                case 5:
                    EnchantUtils.takeHealth(damaged, 3.0);
            }
            sendActivateMessage(damager, getDisplayName(item));
        }
    }
}
