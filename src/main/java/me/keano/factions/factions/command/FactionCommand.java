package me.keano.factions.factions.command;

import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.command.CommandSender;

public class FactionCommand {

    @Command(
            names = {"f", "faction", "team", "t"}
    )
    public static void execute(CommandSender sender) {
        sender.sendMessage(CC.STRAIGHT_LINE + CC.t("&7&m----------"));
        sender.sendMessage(CC.t("&9&lMember Commands"));
        sender.sendMessage(CC.t(" &6* &e/f create <name> &7- &fCreate a faction."));
        sender.sendMessage(CC.t(" &6* &e/f list <page> &7- &fList all the factions."));
        sender.sendMessage(CC.t(" &6* &e/f info <faction> &7- &fFind the info of a faction."));
        sender.sendMessage(CC.t(" &6* &e/f top <page> &7- &fView the top factions."));
        sender.sendMessage(CC.t(" &6* &e/f leave &7- &fLeave your faction."));
        sender.sendMessage(CC.t(" &6* &e/f join <faction> &7- &fJoin a faction that invited you."));
        sender.sendMessage(CC.t(""));

        sender.sendMessage(CC.t("&9&lOfficer Commands"));
        sender.sendMessage(CC.t(" &6* &e/f invite <player> &7- &fInvite a player to your faction."));
        sender.sendMessage(CC.t(" &6* &e/f cancelinvite <player> &7- &fCancel a invite sent."));
        sender.sendMessage(CC.t(""));

        sender.sendMessage(CC.t("&9&lCaptain Commands"));
        sender.sendMessage(CC.t(" &6* &e/f claim &7- &fClaim the chunk you are in."));
        sender.sendMessage(CC.t(""));

        sender.sendMessage(CC.t("&9&lLeader Commands"));
        sender.sendMessage(CC.t(" &6* &e/f levelup &7- &fLevel up your faction."));
        sender.sendMessage(CC.t(" &6* &e/f disband &7- &fDisband your faction."));
        sender.sendMessage(CC.STRAIGHT_LINE + CC.t("&7&m----------"));
    }
}