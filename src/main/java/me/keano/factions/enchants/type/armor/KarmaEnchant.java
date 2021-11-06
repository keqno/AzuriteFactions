package me.keano.factions.enchants.type.armor;

import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class KarmaEnchant extends Enchant {

    public KarmaEnchant() {
        super(
                "Karma",
                5,
                "Armor",
                "Has a chance to spawn tnt on death.",
                3
        );
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        Player damaged = e.getEntity();
        Player killer = damaged.getKiller();
        ItemStack[] itemStacks = damaged.getInventory().getArmorContents();

        if (killer == null) return;

        for (ItemStack itemStack : itemStacks) {
            if (itemStack == null) continue;
            if (!EnchantUtils.verifyLore(itemStack)) continue;

            for (String string : itemStack.getItemMeta().getLore()) {
                if (!string.contains(getName())) continue;
                if (!chanceSuccessful(getLevel(string))) continue;

                switch (getLevel(string)) {
                    case 1:
                        spawnTnt(damaged, 3);
                    case 2:
                        spawnTnt(damaged, 4);
                    case 3:
                        spawnTnt(damaged, 5);
                    case 4:
                        spawnTnt(damaged, 6);
                    case 5:
                        spawnTnt(damaged, 8);
                }
                sendActivateMessage(damaged, getDisplayName(itemStack));
            }
        }
    }

    public void spawnTnt(Player player, int radius) {
        for (Entity entity : player.getNearbyEntities(radius, radius, radius)) {
            if (!(entity instanceof Player)) return;

            Player nearby = (Player) entity;

            if (FactionUtils.isTeamMate(player, nearby)) return;

            TNTPrimed tnt = (TNTPrimed) nearby.getWorld().spawnEntity(nearby.getLocation(), EntityType.PRIMED_TNT);
            tnt.setFuseTicks(0);
            EnchantUtils.takeHealth(nearby, 2.0);
        }
    }
}
