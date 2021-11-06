package me.keano.factions.enchants.command;

import me.keano.factions.utils.CC;
import me.keano.factions.utils.CEEXPUtils;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.entity.Player;

public class CEEXPCommand {

    @Command(
            names = "ceexp set",
            description = "Set the ceexp of a player",
            permission = "azurite.ceexp.admin"
    )
    public static void executeSet(Player player, @Param(name = "player") Player target, @Param(name = "amount") int amount) {
        CEEXPUtils.setCeexp(target, amount);
        player.sendMessage(CC.t("&aSet the ceexp of " + target.getName() + " to " + amount));
    }

    @Command(
            names = "ceexp add",
            description = "Add ceexp to a player",
            permission = "azurite.ceexp.admin"
    )
    public static void executeAdd(Player player, @Param(name = "player") Player target, @Param(name = "amount") int amount) {
        CEEXPUtils.addCeexp(target, amount);
        player.sendMessage(CC.t("&aSet the ceexp of " + target.getName() + " to " + amount));
    }

    @Command(
            names = "ceexp take",
            description = "Add ceexp to a player",
            permission = "azurite.ceexp.admin"
    )
    public static void executeTake(Player player, @Param(name = "player") Player target, @Param(name = "amount") int amount) {
        CEEXPUtils.takeCeexp(target, amount);
        player.sendMessage(CC.t("&aSet the ceexp of " + target.getName() + " to " + amount));
    }
}