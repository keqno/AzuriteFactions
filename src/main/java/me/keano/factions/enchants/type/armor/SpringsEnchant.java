package me.keano.factions.enchants.type.armor;

import me.keano.factions.Factions;
import me.keano.factions.enchants.Enchant;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.utils.event.ArmorEquipEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class SpringsEnchant extends Enchant {

    public SpringsEnchant() {
        super(
                "Springs",
                5,
                "Armor",
                "Gain permanent jump boost dependent on the level."
        );
    }

    @EventHandler
    public void onArmor(ArmorEquipEvent e) {

        ItemStack oldArmor = e.getOldArmorPiece();
        ItemStack newArmor = e.getNewArmorPiece();
        Player player = e.getPlayer();

        new BukkitRunnable() {
            @Override
            public void run() {

                if (newArmor != null) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!EnchantUtils.verifyLore(newArmor)) return;

                            for (String string : newArmor.getItemMeta().getLore()) {
                                if (!string.contains(getName())) continue;

                                player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, getLevel(string) - 1));
                            }
                        }
                    }.runTask(Factions.getInstance());
                }

                if (oldArmor != null) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (!EnchantUtils.verifyLore(oldArmor)) return;

                            for (String string : oldArmor.getItemMeta().getLore()) {
                                if (!string.contains(getName())) continue;

                                player.removePotionEffect(PotionEffectType.JUMP);
                            }
                        }
                    }.runTask(Factions.getInstance());
                }
            }
        }.runTaskAsynchronously(Factions.getInstance());
    }
}