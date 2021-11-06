package me.keano.factions.kits;

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
public class KitEnchant implements Listener {

    private String name;
    private String description;
    private ItemStack bookItem;
    private int baseChance;
    private int chanceIncrease;

    public KitEnchant(String name, String description) {
        this.name = name;
        this.bookItem = new ItemBuilder(Material.BOOK).setName("&e&o" + name + " I").setLore(description, "", "&c&oDrag and drop on an item to combine.").toItemStack();
        this.baseChance = 100;
        this.chanceIncrease = 0;
    }

    public KitEnchant(String name, String description, int baseChance) {
        this.name = name;
        this.bookItem = new ItemBuilder(Material.BOOK).setName("&e&o" + name + " I").setLore(description, "", "&c&oDrag and drop on an item to combine.").toItemStack();
        this.baseChance = baseChance;
        this.chanceIncrease = 0;
    }

    public KitEnchant(String name, String description, int baseChance, int chanceIncrease) {
        this.name = name;
        this.bookItem = new ItemBuilder(Material.BOOK).setName("&e&o" + name + " I").setLore(description, "", "&c&oDrag and drop on an item to combine.").toItemStack();
        this.baseChance = baseChance;
        this.chanceIncrease = chanceIncrease;
    }

    public String getDisplayName() {
        return CC.t("&e&o" + name + " I");
    }

    public int getLevel(String string) {
        String[] array = string.split(" ");
        int level = 0;
        try {
            level = EnchantUtils.convertRomanNumeral(array[1]);
        } catch (ArrayIndexOutOfBoundsException e) {
            // Empty Catch
        }
        return level;
    }

    public boolean chanceSuccessful(int level) {
        int random = new Random().nextInt(300 + 1);
        int chance = (level == 1 ? baseChance : (baseChance + chanceIncrease * level - 1));
        return random <= chance;
    }

    public void sendActivateMessage(Player player, String displayName) {
        player.sendMessage(CC.t(displayName.replaceAll("§o", "") + " &7has activated"));
    }
}