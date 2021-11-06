package me.keano.factions.enchants.utils;

import me.keano.factions.Factions;
import me.keano.factions.enchants.Enchant;
import me.keano.factions.utils.CC;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantUtils {

    public static List<String> rearrangeLore(List<String> list) {
        List<String> sorted = new ArrayList<>();
        int enchantAmount = 0;

        for (String string : list) {
            if (string.contains("DeathTag"))
                sorted.add(string);
        }

        for (String string : list) {
            String[] array = string.split(" ");
            try {
                if (array[1].equalsIgnoreCase("I")) {
                    sorted.add("&a&o" + string);
                    enchantAmount++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Empty Catch
            }
        }

        for (String string : list) {
            String[] array = string.split(" ");
            try {
                if (array[1].equalsIgnoreCase("II")) {
                    sorted.add("&9&o" + string);
                    enchantAmount++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Empty Catch
            }
        }

        for (String string : list) {
            String[] array = string.split(" ");
            try {
                if (array[1].equalsIgnoreCase("III")) {
                    sorted.add("&d&o" + string);
                    enchantAmount++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Empty Catch
            }
        }

        for (String string : list) {
            String[] array = string.split(" ");
            try {
                if (array[1].equalsIgnoreCase("IV")) {
                    sorted.add("&6&o" + string);
                    enchantAmount++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Empty Catch
            }
        }

        for (String string : list) {
            String[] array = string.split(" ");
            try {
                if (array[1].equalsIgnoreCase("V")) {
                    sorted.add("&c&o" + string);
                    enchantAmount++;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                // Empty Catch
            }
        }

        if (enchantAmount > 0)
            sorted.add("&a&l&o- Enchants &7" + enchantAmount + "/8");

        for (String string : list) {
            if (string.contains("Fixes"))
                sorted.add(string);
        }

        for (String string : list) {
            if (string.contains("Wrath Of"))
                sorted.add(string);
        }

        return CC.t(sorted);
    }

    public static List<String> rearrangeLore(List<String> list, int fixes) {
        list.removeIf(string -> string.contains("Fixes"));
        list.add("&e&l&o- Fixes &7" + fixes);
        list.removeIf(string -> string.contains("Kekw"));
        return CC.t(rearrangeLore(list));
    }

    public static void pushAway(LivingEntity willBePushed, Location pushFrom) {
        willBePushed.setVelocity(pushFrom.toVector().subtract(willBePushed.getLocation().toVector()).normalize().multiply(-1));
    }

    public static String convertRomanNumeral(int x) {
        switch (x) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
        }
        return "Unknown";
    }

    public static boolean verifyLore(ItemStack itemStack) {
        return itemStack.hasItemMeta() && itemStack.getItemMeta() != null && itemStack.getItemMeta().hasLore() && itemStack.getItemMeta().getLore() != null;
    }

    public static int convertRomanNumeral(String x) {
        switch (x) {
            case "I":
                return 1;
            case "II":
                return 2;
            case "III":
                return 3;
            case "IV":
                return 4;
            case "V":
                return 5;
            case "VI":
                return 6;
        }
        return 0;
    }

    public static void addHealth(Player player, double amount) {
        player.setHealth(Math.min(player.getHealth() + amount * 2, player.getMaxHealth()));
    }

    public static void takeHealth(Player player, double amount) {
        player.damage(amount * 2);
    }

    @SuppressWarnings("ALL")
    public static boolean isPlayer(Entity damager, Entity damaged) {
        return (damager instanceof Player && damaged instanceof Player);
    }

    public static int getEnchantsOn(ItemStack itemStack) {
        int level = 0;

        try {
            if (verifyLore(itemStack)) {
                for (String string : itemStack.getItemMeta().getLore()) {
                    if (string.contains("Enchants")) {
                        String[] split1 = string.split(" ");
                        String[] split2 = split1[2].split("/");
                        level = Integer.parseInt(split2[0]
                                .replace("§7", ""));
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Empty Catch
        }

        return level;
    }

    public static int getMaxEnchants(ItemStack itemStack) {
        int level = 0;

        try {
            if (verifyLore(itemStack)) {
                for (String string : itemStack.getItemMeta().getLore()) {
                    if (string.contains("Enchants")) {
                        String[] split1 = string.split(" ");
                        String[] split2 = split1[2].split("/");
                        level = Integer.parseInt(split2[1]
                                .replace("§7", ""));
                    }
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Empty Catch
        }

        return level;
    }

    public static boolean hasEnchants(ItemStack itemStack) {
        if (!verifyLore(itemStack))
            return false;

        for (Enchant enchant : Factions.getInstance().getEnchantHandler()
                .getCustomEnchants().values()) {
            for (String string : itemStack.getItemMeta().getLore()) {
                if (string.contains(enchant.getName()))
                    return true;
            }
        }
        return false;
    }

    public static String getColor(int x) {
        switch (x) {
            case 1:
                return "&a";
            case 2:
                return "&9";
            case 3:
                return "&d";
            case 4:
                return "&6";
            case 5:
                return "&c";
        }
        return "";
    }

    public static int getFixes(ItemStack itemStack) {
        int level = 0;

        if (!verifyLore(itemStack))
            return level;

        try {
            for (String string : itemStack.getItemMeta().getLore()) {
                if (!string.contains("Fixes")) continue;

                String[] split = string.split(" ");

                level = Integer.parseInt(split[2].replaceAll("§7", ""));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            // Empty Catch
        }

        return level;
    }

    public static boolean isEnchantable(ItemStack itemStack, String type) {
        String name = itemStack.getType().name();

        if (type.equalsIgnoreCase("ARMOR"))
            return name.contains("HELMET") || name.contains("CHESTPLATE") || name.contains("LEGGINGS") || name.contains("BOOTS");

        if (type.equalsIgnoreCase("SWORD"))
            return name.contains("SWORD");

        return false;
    }

    public static boolean isFixable(ItemStack itemStack) {
        String name = itemStack.getType().name().toUpperCase();

        if (name.contains("HELMET") || name.contains("CHESTPLATE") || name.contains("LEGGINGS") || name.contains("BOOTS"))
            return true;

        if (name.contains("PICKAXE") || name.contains("AXE") || name.contains("SHOVEL") || name.contains("HOE"))
            return true;

        return name.contains("SWORD");
    }
}