package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;

public class FInfoCommand {

    @Command(
            names = {
                    "f info", "t info", "faction info", "team info",
                    "f who", "t who", "faction who", "team who",
                    "f show", "t show", "faction show", "team show"
            },
            async = true
    )
    public static void execute(CommandSender sender, @Param(name = "faction") String faction) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(faction);

        if (pf == null) {
            sender.sendMessage(FactionLang.FACTION_NOT_FOUND);
            return;
        }

        for (String string : pf.getFactionInfo())
            sender.sendMessage(string);
    }
}