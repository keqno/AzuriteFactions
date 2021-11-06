package me.keano.factions.enchants.type.sword;

import me.keano.factions.Factions;
import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.utils.particles.ParticleEffect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class BleedingEnchant extends Enchant {

    private final Set<UUID> bleedingCache = new HashSet<>();

    public BleedingEnchant() {
        super("Bleeding",
                2,
                "Sword",
                "Has a chance to do damage over 4 seconds.",
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

            bleedingCache.add(damaged.getUniqueId());
            callBleeding(damaged, getLevel(string));
            sendActivateMessage(damager, getDisplayName(item));
        }
    }

    public void callBleeding(Player player, int level) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (bleedingCache.contains(player.getUniqueId())) {
                    ParticleEffect.REDSTONE.display(0, 0, 0, 10, 10, player.getLocation(), 100);
                    EnchantUtils.takeHealth(player, (level == 1 ? 0.5 : 1.0));
                }
            }
        }.runTaskTimer(Factions.getInstance(), 0L, 20L);
        new BukkitRunnable() {
            @Override
            public void run() {
                bleedingCache.remove(player.getUniqueId());
            }
        }.runTaskLater(Factions.getInstance(), 20 * 6L);
    }
}
