package me.keano.factions.enchants.type.sword;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class UnstableEnchant extends Enchant {

    public UnstableEnchant() {
        super(
                "Unstable",
                5,
                "Sword",
                "Has a chance to deal 1.5 hearts to anyone in a 6x6x6 radius.",
                2,
                1
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

            for (Entity entity : damager.getNearbyEntities(6, 6, 6)) {
                if (!(entity instanceof Player)) continue;
                Player nearby = (Player) entity;
                if (FactionUtils.isTeamMate(damaged, nearby)) continue;
                EnchantUtils.takeHealth(nearby, 1.5);
            }
            sendActivateMessage(damager, getDisplayName(item));
        }
    }
}
