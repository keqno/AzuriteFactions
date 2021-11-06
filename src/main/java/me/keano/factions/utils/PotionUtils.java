package me.keano.factions.utils;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PotionEffectExpireEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PotionUtils implements Listener {

    public static Map<UUID, PotionEffect> potionCache = new HashMap<>();

    public static void addPotionEffect(Player player, PotionEffect potionEffect) {
        if (player.hasPotionEffect(potionEffect.getType())) {
            for (PotionEffect potionEffect1 : player.getActivePotionEffects()) {
                if (potionEffect1.getType().equals(potionEffect.getType()))
                    potionCache.put(player.getUniqueId(), potionEffect1);
            }
        }
        player.addPotionEffect(potionEffect);
    }

    @EventHandler
    public void onExpire(PotionEffectExpireEvent e) {
        LivingEntity entity = e.getEntity();
        if (potionCache.containsKey(entity.getUniqueId())) {
            if (e.getEffect().getType().equals(potionCache.get(entity.getUniqueId()).getType()))
                entity.addPotionEffect(potionCache.get(entity.getUniqueId()));
        }
    }

    public static boolean isNegativeEffect(PotionEffectType potionType) {
        return potionType.equals(PotionEffectType.BLINDNESS) ||
                potionType.equals(PotionEffectType.CONFUSION) ||
                potionType.equals(PotionEffectType.POISON) ||
                potionType.equals(PotionEffectType.WITHER) ||
                potionType.equals(PotionEffectType.SLOW) ||
                potionType.equals(PotionEffectType.WEAKNESS) ||
                potionType.equals(PotionEffectType.SLOW_DIGGING);
    }
}