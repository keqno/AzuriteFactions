package me.keano.factions.utils;

import me.keano.factions.Factions;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class FreezeUtils {

    public static void freezePlayer(Player player, long seconds) {
        player.setWalkSpeed(0.0F);
        player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 128));

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setWalkSpeed(0.2F);
            }
        }.runTaskLater(Factions.getInstance(), 20 * seconds);
    }
}
