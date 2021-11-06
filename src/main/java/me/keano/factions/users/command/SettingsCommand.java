package me.keano.factions.users.command;

import me.keano.factions.users.menu.SettingsMenu;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;

public class SettingsCommand {

    @Command(
            names = {"setting", "settings"}
    )
    public static void execute(Player player) {
        new SettingsMenu().openMenu(player);
    }
}