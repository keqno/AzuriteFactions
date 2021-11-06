package me.keano.factions.users.command;

import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.command.CommandSender;

public class UserCommand {

    @Command(
            names = "user",
            description = "Manage the registered users."
    )
    public void execute(CommandSender sender) {
        sender.sendMessage(CC.t("&7&oSoon!"));
    }
}