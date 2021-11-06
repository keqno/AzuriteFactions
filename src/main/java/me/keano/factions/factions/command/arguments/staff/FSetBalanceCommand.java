package me.keano.factions.factions.command.arguments.staff;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;

public class FSetBalanceCommand {

    @Command(
            names = {"f setbalance", "t setbalance", "faction setbalance", "team setbalance"},
            permission = "op"
    )
    public static void execute(CommandSender sender, @Param(name = "faction") String faction, @Param(name = "balance") int amount) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(faction);

        if (pf == null) {
            sender.sendMessage(FactionLang.FACTION_NOT_FOUND);
            return;
        }

        pf.setBalance(amount);
    }
}