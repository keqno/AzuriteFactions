package me.keano.factions.wraths.command;

import me.keano.factions.Factions;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.Utilities;
import me.keano.factions.wraths.Wrath;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WrathCommand {

    @Command(
            names = {"wrath give"},
            permission = "azurite.wrath.admin"
    )
    public static void executeGive(CommandSender sender, @Param(name = "player") Player player, @Param(name = "wrath") String name) {
        Wrath wrath = Factions.getInstance().getWrathHandler().getByName(name);

        if (wrath == null) {
            sender.sendMessage(CC.t("&eThe wrath &c" + name + " &edoes not exist."));
            return;
        }

        Utilities.giveItem(player, wrath.getBookItem());
        sender.sendMessage(CC.t("&eYou have given &9" + player.getName() + " &ewrath of &c" + wrath.getName() + "&e."));
    }

    @Command(
            names = {"wrath list"},
            permission = "azurite.wrath.admin"
    )
    public static void executeList(CommandSender sender) {
        sender.sendMessage(CC.STRAIGHT_LINE);
        sender.sendMessage(CC.t("&9&lWrath List"));

        for (Wrath wrath : Factions.getInstance().getWrathHandler().getWraths())
            sender.sendMessage(CC.t("&6* &e" + wrath.getName()));

        sender.sendMessage(CC.STRAIGHT_LINE);
    }
}