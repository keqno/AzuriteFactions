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

public class DemonicEnchant extends Enchant {

    public DemonicEnchant() {
        super("Demonic",
                5,
                "Sword",
                "Has a chance to inflict the enemy with wither.",
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

            if (getLevel(string) == 2) {
                damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 5, 1));
            } else {
                damaged.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 20 * 5, 0));
            }
            sendActivateMessage(damager, getDisplayName(item));
        }
    }
}