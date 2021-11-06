package me.keano.factions.warps.command;

import me.keano.factions.Factions;
import me.keano.factions.utils.CC;
import me.keano.factions.warps.Warp;
import me.keano.factions.warps.task.WarpTask;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand {

    @Command(
            names = {"warps create", "warp create"},
            permission = "azurite.warps.admin"
    )
    public static void executeCreate(Player player, @Param(name = "name") String name, @Param(name = "cooldown") int seconds) {
        if (Factions.getInstance().getWarpHandler().getByName(name) != null) {
            player.sendMessage(CC.t("&eThe warp &c" + name + " &ealready exists!"));
            return;
        }

        Warp warp = new Warp(name, player.getLocation(), seconds);
        warp.save();
        Factions.getInstance().getWarpHandler().getWarps().add(warp);
        player.sendMessage(CC.t("&eCreated warp &c" + name + " &ewith cooldown of &9" + seconds + "&e."));
    }

    @Command(
            names = {"warps delete", "warp delete"},
            permission = "azurite.warps.admin"
    )
    public static void executeDelete(CommandSender sender, @Param(name = "name") String name) {
        Warp warp = Factions.getInstance().getWarpHandler().getByName(name);

        if (warp == null) {
            sender.sendMessage(CC.t("&eThe warp &c" + name + " &edoes not exist."));
            return;
        }

        Factions.getInstance().getWarpHandler().getWarps().remove(warp);
        warp.delete();
        sender.sendMessage(CC.t("&eDeleted warp &c" + name + "&e."));
    }

    @Command(
            names = {"warp"}
    )
    public static void execute(Player player, @Param(name = "name") String name) {
        Warp warp = Factions.getInstance().getWarpHandler().getByName(name);

        if (warp == null) {
            player.sendMessage(CC.t("&eThe warp &c" + name + " &edoes not exist."));
            return;
        }

        if (!player.hasPermission("azurite.warp." + warp.getName())) {
            player.sendMessage(CC.t("&cYou do not have access to this warp!"));
            return;
        }

        new WarpTask(player, warp);
    }

    @Command(
            names = {"warps"}
    )
    public static void executeWarps(CommandSender sender) {
        sender.sendMessage(CC.STRAIGHT_LINE);
        sender.sendMessage(CC.t("&9&lWarps"));

        for (Warp warp : Factions.getInstance().getWarpHandler().getWarps())
            sender.sendMessage(CC.t(" &6* &e" + warp.getName()));

        sender.sendMessage(CC.STRAIGHT_LINE);
    }
}