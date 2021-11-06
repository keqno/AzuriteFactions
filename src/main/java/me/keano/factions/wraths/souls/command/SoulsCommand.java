package me.keano.factions.wraths.souls.command;

import me.keano.factions.Factions;
import me.keano.factions.utils.Utilities;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SoulsCommand {

    @Command(
            names = {"souls give", "soul give"}
    )
    public static void execute(CommandSender sender, @Param(name = "target") Player player, @Param(name = "amount") int amount) {
        Utilities.giveItem(player,
                Factions.getInstance().getWrathHandler().getSoulsHandler().getSoulItem(amount));
    }
}