package me.keano.factions.enchants.command;

import me.keano.factions.enchants.menu.CEMenu;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;

public class CECommand {

    @Command(
            names = {"ce", "customenchants"}
    )
    public static void execute(Player player) {
        new CEMenu().openMenu(player);
    }
}