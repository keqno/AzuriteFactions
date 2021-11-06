package me.keano.factions.enchants.type.sword;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class IronArmyEnchant extends Enchant {

    public IronArmyEnchant() {
        super(
                "IronArmy",
                5,
                "Sword",
                "Has a chance to spawn an iron golem attacking the enemy.",
                1,
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

            IronGolem ig = (IronGolem) damager.getWorld().spawnEntity(damager.getLocation(), EntityType.IRON_GOLEM);
            ig.setTarget(damaged);
            sendActivateMessage(damager, getDisplayName(item));
        }
    }
}