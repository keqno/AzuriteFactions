package me.keano.factions.kits.command;

import me.keano.factions.Factions;
import me.keano.factions.kits.KitEnchant;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;

public class KitEnchantCommand {

    @Command(
            names = "kitenchants",
            description = "Gain all the enchant books for kits.",
            permission = "azurite.kitenchants"
    )
    public static void execute(Player player) {
        for (KitEnchant kitEnchant : Factions.getInstance().getKitHandler().getKitEnchants().values())
            player.getInventory().addItem(kitEnchant.getBookItem());
    }
}