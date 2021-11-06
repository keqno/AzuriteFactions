package me.keano.factions.wraths;

import lombok.Getter;
import lombok.Setter;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@Setter
public class Wrath implements Listener {

    protected String name;
    protected String displayName;
    protected String type;
    protected int chance;
    protected int soulsRequired;
    protected ItemStack bookItem;

    public Wrath(String name, String displayName, String type, List<String> description, int chance, int soulsRequired) {
        this.name = name;
        this.displayName = CC.t(displayName);
        this.type = type;
        this.chance = chance;
        this.soulsRequired = soulsRequired;
        this.bookItem = new ItemBuilder(Material.BOOK, 1)
                .setName(displayName)
                .setLore(description)
                .toItemStack();
    }

    public boolean containsWrath(ItemStack itemStack) {
        if (!EnchantUtils.verifyLore(itemStack))
            return false;

        for (String string : itemStack.getItemMeta().getLore()) {
            if (string.contains(this.getDisplayName()))
                return true;
        }

        return false;
    }
}