package me.keano.factions.factions.command.arguments.staff;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;

public class FSetStrengthCommand {

    @Command(
            names = {"f setstrength", "t setstrength", "faction setstrength", "team setstrength"},
            permission = "op"
    )
    public static void execute(CommandSender sender, @Param(name = "faction") String faction, @Param(name = "strength") int amount) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(faction);

        if (pf == null) {
            sender.sendMessage(FactionLang.FACTION_NOT_FOUND);
            return;
        }

        pf.setStrength(amount);
    }
}