package me.keano.factions.enchants.type.armor;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.utils.PotionUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

public class ImmunityEnchant extends Enchant {

    public ImmunityEnchant() {
        super(
                "Immunity",
                5,
                "Armor",
                "Has a chance to remove all negative effects",
                2,
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

                for (PotionEffect potionEffect : damaged.getActivePotionEffects()) {
                    if (PotionUtils.isNegativeEffect(potionEffect.getType()))
                        damaged.removePotionEffect(potionEffect.getType());
                }
                sendActivateMessage(damaged, getDisplayName(itemStack));
            }
        }
    }
}
