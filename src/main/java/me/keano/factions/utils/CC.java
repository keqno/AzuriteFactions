package me.keano.factions.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CC {

    public static String STRAIGHT_LINE = t("&7&m--------------------------");

    public static String t(String t) {
        return ChatColor.translateAlternateColorCodes('&', t);
    }

    public static List<String> t(List<String> t) {
        List<String> translated = new ArrayList<>();

        for (String string : t)
            translated.add(ChatColor.translateAlternateColorCodes('&', string));

        return translated;
    }

    public static void sendStaff(String... message) {
        for (String string : message)
            Bukkit.broadcast(t(string), "azurite.staff");
    }

    public static void state(String state) {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(t("&4===&c====================&4==="));
        sender.sendMessage(t("- &eName&7: AzuriteFactions"));
        sender.sendMessage(t("- &eVersion&7: 1.0-BETA"));
        sender.sendMessage(t("- &eAuthor&7: Keano"));
        sender.sendMessage(t("- &eState&7: " + state));
        sender.sendMessage(t("&4===&c====================&4==="));
    }

    public static void broadcast(String message) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(t(message));
    }

    public static void printMessage(String message, boolean surroundMessage) {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        if (surroundMessage) sender.sendMessage(t("&4===&c====================&4==="));

        sender.sendMessage(t(message));

        if (surroundMessage) sender.sendMessage(t("&4===&c====================&4==="));
    }

    public static void printMessage(String message) {
        ConsoleCommandSender sender = Bukkit.getConsoleSender();
        sender.sendMessage(t(message));
    }
}
