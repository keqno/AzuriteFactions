package me.keano.factions.kits.enchants.sword;

import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.kits.KitEnchant;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class MetallicKitEnchant extends KitEnchant {
    
    public MetallicKitEnchant() {
        super(
                "Metallic",
                "Has a chance to spawn a blaze.",
                2
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

            Blaze blaze = (Blaze) damager.getWorld().spawnEntity(damager.getLocation(), EntityType.BLAZE);
            blaze.setTarget(damaged);
            sendActivateMessage(damager, getDisplayName());
        }
    }
}
