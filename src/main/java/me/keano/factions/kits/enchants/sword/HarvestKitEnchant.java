package me.keano.factions.kits.enchants.sword;

import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.kits.KitEnchant;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class HarvestKitEnchant extends KitEnchant {

    public HarvestKitEnchant() {
        super(
                "Harvest",
                "Take 5 food bars from anyone in a 5x5x5 radius and deal 2 hearts.",
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


            for (Entity entity : damager.getNearbyEntities(3, 3, 3)) {
                if (!(entity instanceof Player)) continue;

                Player nearby = (Player) entity;

                if (FactionUtils.isTeamMate(nearby, damager)) continue;

                nearby.setFoodLevel(nearby.getFoodLevel() - 5);
            }

            damaged.getWorld().playSound(damager.getLocation(), Sound.BAT_TAKEOFF, 1, 1);
            EnchantUtils.takeHealth(damaged, 2.0);
            sendActivateMessage(damager, getDisplayName());
        }
    }
}