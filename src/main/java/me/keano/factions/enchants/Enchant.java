package me.keano.factions.enchants;

import lombok.Getter;
import lombok.Setter;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

@Getter
@Setter
public class Enchant implements Listener {

    private String name;
    private String type;
    private ItemStack bookItem;
    private int maxLevel;
    private int baseChance;
    private int chanceIncrease;

    public Enchant(String name, int maxLevel, String type, String description) {
        this.name = name;
        this.type = type;
        this.bookItem = new ItemBuilder(Material.BOOK).setName("&a" + name + " I").setLore("&7Type: &a" + type, "", "&7" + description, "", "&c&oDrag and drop on an item to combine.").toItemStack();
        this.maxLevel = maxLevel;
        this.baseChance = 100;
        this.chanceIncrease = 0;
    }

    public Enchant(String name, int maxLevel, String type, String description, int baseChance) {
        this.name = name;
        this.type = type;
        this.bookItem = new ItemBuilder(Material.BOOK).setName("&a" + name + " I").setLore("&7Type: &a" + type, "", "&7" + description, "", "&c&oDrag and drop on an item to combine.").toItemStack();
        this.maxLevel = maxLevel;
        this.baseChance = baseChance;
        this.chanceIncrease = 0;
    }

    public Enchant(String name, int maxLevel, String type, String description, int baseChance, int chanceIncrease) {
        this.name = name;
        this.type = type;
        this.bookItem = new ItemBuilder(Material.BOOK).setName("&a" + name + " I").setLore("&7Type: &a" + type, "", "&7" + description, "", "&c&oDrag and drop on an item to combine.").toItemStack();
        this.maxLevel = maxLevel;
        this.baseChance = baseChance;
        this.chanceIncrease = chanceIncrease;
    }

    public int getLevel(String string) {
        String[] array = string.split(" ");
        int level = 0;
        try {
            level = EnchantUtils.convertRomanNumeral(array[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Empty Catch
        }
        return (level > maxLevel ? 0 : level);
    }

    public boolean chanceSuccessful(int level) {
        int random = new Random().nextInt(300 + 1);
        int chance = (level == 1 ? baseChance : (baseChance + chanceIncrease * level - 1));
        return random <= chance;
    }

    public String getDisplayName(ItemStack item) {
        for (String string : item.getItemMeta().getLore()) {
            if (string.contains(getName()))
                return string;
        }
        return "";
    }

    public void sendActivateMessage(Player player, String displayName) {
        player.sendMessage(CC.t(displayName.replaceAll("§o", "")  + " &7has activated"));
    }
}