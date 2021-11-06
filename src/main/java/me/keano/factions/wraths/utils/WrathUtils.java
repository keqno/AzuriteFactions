package me.keano.factions.wraths.utils;

import me.keano.factions.Factions;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.wraths.Wrath;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WrathUtils {

    public static int getSouls(ItemStack itemStack) {
        int i = 0;

        if (!EnchantUtils.verifyLore(itemStack)) return 0;

        for (String string : itemStack.getItemMeta().getLore()) {
            if (!string.contains("Wrath Of")) continue;

            String[] split = string.split(" ");

            try {
                i = Integer.parseInt(split[4].replaceAll("§7", ""));
            } catch (NumberFormatException e) {
                // Empty Catch
            }
        }

        return i;
    }

    public static void setSouls(ItemStack itemStack, int amount) {
        if (!EnchantUtils.verifyLore(itemStack)) return;

        Wrath wrath = getWrathOn(itemStack);

        if (wrath == null) return;

        ItemMeta meta = itemStack.getItemMeta();

        meta.getLore().removeIf(string -> string.contains(wrath.getDisplayName()));
        meta.getLore().add("&c&l&o- Wrath Of " + wrath.getName() + " &7" + amount);

        itemStack.setItemMeta(meta);
    }

    public static Wrath getWrathOn(ItemStack itemStack) {
        Wrath wrath = null;

        if (!EnchantUtils.verifyLore(itemStack)) return null;

        for (String string : itemStack.getItemMeta().getLore()) {
            if (!string.contains("Wrath Of")) continue;

            String[] split = string.split(" ");

            wrath = Factions.getInstance().getWrathHandler().getByName(split[3]);
        }

        return wrath;
    }
}