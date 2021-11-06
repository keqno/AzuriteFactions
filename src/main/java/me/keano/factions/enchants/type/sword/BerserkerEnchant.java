package me.keano.factions.enchants.type.sword;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.utils.PotionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BerserkerEnchant extends Enchant {

    public BerserkerEnchant() {
        super("Berserker",
                5,
                "Sword",
                "Has a chance to heal a certain amount.",
                5
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
                    EnchantUtils.addHealth(damager, 1.5);
                    break;
                case 2:
                    EnchantUtils.addHealth(damager, 2.0);
                    break;
                case 3:
                    EnchantUtils.addHealth(damager, 2.5);
                    break;
                case 4:
                    EnchantUtils.addHealth(damager, 2.5);
                    PotionUtils.addPotionEffect(damager, new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 20 * 5, 2));
                    break;
                case 5:
                    PotionUtils.addPotionEffect(damager, new PotionEffect(PotionEffectType.REGENERATION, 20 * 6, 2));
            }
            sendActivateMessage(damager, getDisplayName(item));
        }
    }
}
