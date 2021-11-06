package me.keano.factions.wraths.souls.utils;

import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.utils.CC;
import org.bukkit.inventory.ItemStack;

public class SoulsUtils {

    public static int getSouls(ItemStack itemStack) {
        int souls = 0;

        for (String string : itemStack.getItemMeta().getLore()) {
            if (string.contains("&a&l&o- Amount ")) {
                String[] split = string.split(" ");

                try {
                    souls = Integer.parseInt(split[2].replaceAll("§7", ""));
                } catch (ArrayIndexOutOfBoundsException e) {
                    // Empty Catch
                }
            }
        }

        return souls;
    }

    public static boolean isSoulItem(ItemStack itemStack) {
        if (!EnchantUtils.verifyLore(itemStack)) return false;
        if (!itemStack.getItemMeta().hasDisplayName()) return false;

        for (String string : itemStack.getItemMeta().getLore()) {
            if (string.contains(CC.t("&a&l&o- Amount")) && itemStack.getItemMeta().getDisplayName().equalsIgnoreCase(CC.t("&2&lEternal Souls")))
                return true;
        }

        return false;
    }
}