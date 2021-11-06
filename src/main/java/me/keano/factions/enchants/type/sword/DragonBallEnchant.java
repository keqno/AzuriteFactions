package me.keano.factions.enchants.type.sword;

import me.keano.factions.Factions;
import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.utils.particles.ParticleEffect;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LargeFireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class DragonBallEnchant extends Enchant {

    public DragonBallEnchant() {
        super("DragonBall",
                5,
                "Sword",
                "Has a chance to shoot a fireball at the enemy.",
                2,
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

            LargeFireball fireball = (LargeFireball)
                    damager.getWorld().spawnEntity(damager.getEyeLocation(), EntityType.FIREBALL);
            fireball.setMetadata("dragonball", new FixedMetadataValue(Factions.getInstance(), fireball));
            sendActivateMessage(damager, getDisplayName(item));
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        if (!e.getEntity().hasMetadata("dragonball")) return;

        e.getLocation().getWorld().playSound(e.getLocation(), Sound.EXPLODE, 1, 1);
        ParticleEffect.EXPLOSION_LARGE.display(0, 0, 0, 1, 1, e.getLocation(), 100);
        e.setCancelled(true);
    }
}