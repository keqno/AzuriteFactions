package me.keano.factions.utils;

import me.keano.factions.factions.player.Member;
import me.keano.factions.factions.player.Role;
import net.evilblock.cubed.util.bukkit.cuboid.Cuboid;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.UUID;

public class Serializer {

    @SuppressWarnings("ALL")
    public static String stringifyItem(ItemStack is) {
        String string = "";

        string = string + is.getType().toString() + ", " + is.getAmount() + ", " + is.getData().getData()
                + ", " + is.getDurability();

        if (is.getItemMeta().hasDisplayName()) {
            string = string + ", true:" + is.getItemMeta().getDisplayName();
        } else {
            string = string + ", false";
        }

        if (is.getItemMeta().hasEnchants()) {
            string = string + ", true:";
            for (Enchantment enchantment : is.getItemMeta().getEnchants().keySet())
                string = string + enchantment.getName() + "~" + is.getEnchantmentLevel(enchantment) + ";";
        } else {
            string = string + ", false";
        }

        if (is.getItemMeta().hasLore()) {
            string = string + ", true:";
            for (int i = 0; i < is.getItemMeta().getLore().size(); i++) {
                string = string + is.getItemMeta().getLore().get(i) + ";";
            }
        } else {
            string = string + ", false";
        }

        return string;
    }

    public static String stringifyLocation(Location location) {
        return location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY()
                + ", " + location.getBlockZ() + ", " + location.getYaw() + ", " + location.getPitch();
    }

    public static String stringifyCuboid(Cuboid cuboid) {
        return cuboid.getWorld() + ", " + cuboid.getLowerX() + ", " + cuboid.getLowerY() + ", " + cuboid.getLowerZ() +
                ", " + cuboid.getUpperX() + ", " + cuboid.getUpperY() + ", " + cuboid.getUpperZ();
    }

    public static Cuboid destringifyCuboid(String string ) {
        String[] array = string.split(", ");
        return new Cuboid(
                Bukkit.getWorld(array[0]),
                parseInt(array[1]),
                parseInt(array[2]),
                parseInt(array[3]),
                parseInt(array[4]),
                parseInt(array[5]),
                parseInt(array[6])
        );
    }

    public static Location destringifyLocation(String string) {
        String[] array = string.split(", ");
        return new Location(
                Bukkit.getWorld(array[0]),
                Integer.parseInt(array[1]),
                Integer.parseInt(array[2]),
                Integer.parseInt(array[3]),
                Float.parseFloat(array[4]),
                Float.parseFloat(array[5])
        );
    }

    public static String stringifyMember(Member member) {
        return member.getUuid() + ", " + member.getRole().toString();
    }

    public static Member destringifyMember(String string) {
        String[] array = string.split(", ");
        return new Member(UUID.fromString(array[0]), Role.valueOf(array[1]));
    }

    public static Chunk destringifyChunk(String string) {
        String[] array = string.split(", ");
        return Bukkit.getWorld(array[0]).getChunkAt(parseInt(array[1]), parseInt(array[2]));
    }

    public static String stringifyChunk(Chunk chunk) {
        return chunk.getWorld().getName() + ", " + chunk.getX() + ", " + chunk.getZ();
    }

    public static ItemStack destringifyItem(String string) {
        String[] array = string.split(", ");
        String[] displayName = array[4].split(":");
        String[] enchantsArray = array[5].split(":");
        String[] loreArray = array[6].split(":");

        ItemBuilder ib = new ItemBuilder(Material.valueOf(array[0]), parseInt(array[1]))
                .data((short) parseInt(array[2]))
                .setDurability((short) parseInt(array[3]));

        if (parseBoolean(displayName[0]))
            ib.setName(displayName[1]);

        if (parseBoolean(enchantsArray[0])) {
            for (String strings : enchantsArray) {
                if (strings.equalsIgnoreCase("TRUE")) continue;

                String[] e = strings.split(";");

                for (String enchants : e) {
                    String[] level = enchants.split("~");

                    for (int i = 0; i < level.length - 1; i++)
                        ib.addUnsafeEnchantment(Enchantment.getByName(level[i]), parseInt(level[i + 1]));
                }
            }
        }

        if (parseBoolean(loreArray[0])) {
            for (String lore : loreArray) {
                if (lore.equalsIgnoreCase("TRUE")) continue;
                String[] newArray = lore.split(";");

                for (String lore2 : newArray)
                    ib.addLoreLine(lore2);
            }
        }

        return ib.toItemStack();
    }

    public static PotionEffect destringifyPotion(String string) {
        String[] array = string.split(", ");
        return new PotionEffect(PotionEffectType.getByName(array[0]),
                20 * parseInt(array[2]), parseInt(array[1]));
    }

    public static int parseInt(String x) {
        int parsed = 0;

        try {
            parsed = Integer.parseInt(x);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return parsed;
    }

    public static boolean parseBoolean(String x) {
        return Boolean.parseBoolean(x);
    }
}