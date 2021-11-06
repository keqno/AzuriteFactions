package me.keano.factions.kits.enchants.sword;

import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.kits.KitEnchant;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ZeusBloodKitEnchant extends KitEnchant {

    public ZeusBloodKitEnchant() {
        super(
                "ZeusBlood",
                "Has a chance to strike the enemy with lightning and knockback them.",
                6
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

            EnchantUtils.takeHealth(damaged, 1.5);

            for (Entity entity : damager.getNearbyEntities(4, 4, 4)) {
                if (!(entity instanceof Player)) continue;

                Player nearby = (Player) entity;

                if (FactionUtils.isTeamMate(damager, nearby)) continue;

                EnchantUtils.pushAway(nearby, damager.getLocation());
            }

            sendActivateMessage(damager, getDisplayName());
        }
    }
}
