package me.keano.factions.wraths.souls;

import me.keano.factions.Factions;
import me.keano.factions.utils.ItemBuilder;
import me.keano.factions.wraths.souls.listener.SoulsListener;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class SoulsHandler {

    public SoulsHandler(Factions instance) {
        instance.getServer().getPluginManager().registerEvents(new SoulsListener(), instance);
    }

    public ItemStack getSoulItem(int amount) {
        return new ItemBuilder(Material.EYE_OF_ENDER, 1)
                .setName("&2&lEternal Souls")
                .setLore("&a&l&o- Amount &7" + amount)
                .toItemStack();
    }
}