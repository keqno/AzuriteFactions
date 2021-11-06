package me.keano.factions.utils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.concurrent.TimeUnit;

public class Utilities {

    public static void takeItemInHand(Player player, int amount) {
        if (player.getItemInHand() == null)
            return;

        if (player.getItemInHand().getAmount() < 1) {
            player.setItemInHand(null);
        } else {
            ItemStack itemHand = player.getItemInHand();
            itemHand.setAmount(itemHand.getAmount() - amount);
            player.setItemInHand(itemHand);
        }
    }

    public static void giveItem(Player player, ItemStack itemStack) {
        if (player.getInventory().firstEmpty() == -1) {
            player.getWorld().dropItem(player.getLocation(), itemStack);
        } else {
            player.getInventory().addItem(itemStack);
        }
    }

    public static String getPretty(Location location, boolean withWorld) {
        return location.getBlockX() + ", " + location.getBlockZ() + ", " + location.getBlockY() +
                (withWorld ? " (" + location.getWorld().getName().toUpperCase() + ")" : "");
    }
}