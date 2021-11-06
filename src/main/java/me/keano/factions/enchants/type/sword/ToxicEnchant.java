package me.keano.factions.enchants.type.sword;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ToxicEnchant extends Enchant {

    public ToxicEnchant() {
        super(
                "Toxic",
                5,
                "Sword",
                "Has a chance to inflict the enemy with poison.",
                3,
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

            switch (getLevel(string)) {
                case 1:
                case 2:
                case 3:
                    damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 7, 0));
                    break;
                case 4:
                    damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 7, 1));
                    break;
                case 5:
                    damaged.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 20 * 7, 2));
            }
            sendActivateMessage(damager, getDisplayName(item));
        }
    }
}
