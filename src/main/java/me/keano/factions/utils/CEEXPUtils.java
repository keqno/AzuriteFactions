package me.keano.factions.utils;

import me.keano.factions.Factions;
import me.keano.factions.users.User;
import org.bukkit.entity.Player;

@SuppressWarnings("ALL")
public class CEEXPUtils {

    public static void takeCeexp(Player player, int amount) {
        User user = Factions.getInstance().getUserHandler().getUser(player);
        user.takeCeexp(amount);
    }

    public static boolean hasCeexp(Player player, int amount) {
        User user = Factions.getInstance().getUserHandler().getUser(player);
        return (user.getCeexp() >= amount);
    }

    public static int getCeexp(Player player) {
        User user = Factions.getInstance().getUserHandler().getUser(player);
        return user.getCeexp();
    }

    public static void addCeexp(Player player, int amount) {
        User user = Factions.getInstance().getUserHandler().getUser(player);
        user.setCeexp(user.getCeexp() + amount);
    }

    public static void setCeexp(Player player, int amount) {
        User user = Factions.getInstance().getUserHandler().getUser(player);
        user.setCeexp(amount);
    }
}
