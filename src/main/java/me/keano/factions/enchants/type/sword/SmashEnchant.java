package me.keano.factions.enchants.type.sword;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SmashEnchant extends Enchant {

    public SmashEnchant() {
        super(
                "Smash",
                5,
                "Sword",
                "Has a chance to knockback any enemies near you.",
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
                    knockbackPlayers(damaged, 2);
                    break;
                case 2:
                    knockbackPlayers(damaged, 3);
                    break;
                case 3:
                    knockbackPlayers(damaged, 4);
                    break;
                case 4:
                    knockbackPlayers(damaged, 5);
                    break;
                case 5:
                    knockbackPlayers(damaged, 6);
            }
            sendActivateMessage(damager, getDisplayName(item));
        }
    }
    
    public void knockbackPlayers(Player player, int radius) {
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player)) continue;

            Player knocked = (Player) entity;

            if (FactionUtils.isTeamMate(player, knocked)) return;

            EnchantUtils.pushAway(knocked, player.getLocation());
        }
    }
}
