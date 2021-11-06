package me.keano.factions.kits.enchants.sword;

import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.kits.KitEnchant;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class FameKitEnchant extends KitEnchant {

    public FameKitEnchant() {
        super(
                "Fame",
                "Give anyone in a 4x4x4 radius poison or deal 2.5 hearts.",
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

            int random = new Random().nextInt(100 + 1);
            
            if (random >= 50) {
                for (Entity entity : damager.getNearbyEntities(4, 4, 4)) {
                    if (!(entity instanceof Player)) continue;

                    Player nearby = (Player) entity;

                    if (FactionUtils.isTeamMate(damager, nearby)) continue;

                    nearby.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 6, 5));
                }
            } else {
                EnchantUtils.takeHealth(damaged, 2.5);
            }

            sendActivateMessage(damager, getDisplayName());
        }
    }
}