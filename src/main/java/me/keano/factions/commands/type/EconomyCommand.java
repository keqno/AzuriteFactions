package me.keano.factions.commands.type;

import me.keano.factions.Factions;
import me.keano.factions.users.User;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.entity.Player;

public class EconomyCommand {

    @Command(
            names = {"bal", "balance", "eco"}
    )
    public static void execute(Player player, @Param(name = "player", defaultValue = "self") Player target) {
        User targetUser = Factions.getInstance().getUserHandler().getUser(target);
        User playerUser = Factions.getInstance().getUserHandler().getUser(player);

        if (player == target) {
            player.sendMessage(CC.t("&eYour Economy: &c" + playerUser.getBalance()));
            return;
        }

        player.sendMessage(CC.t("&e" + target.getName() + "'s Economy: &c" + targetUser.getBalance()));
    }

    @Command(
            names = {"economy set"},
            description = "Set the economy of a player",
            permission = "azurite.economy.admin"
    )
    public static void executeSet(Player player, @Param(name = "player") Player target, @Param(name = "amount") int amount) {
        User user = Factions.getInstance().getUserHandler().getUser(target);
        user.setBalance(amount);
        player.sendMessage(CC.t("&eYou have set &c" + target.getName() + "'s &ebalance to &9$" + amount + "&e."));
    }

    @Command(
            names = {"economy add"},
            description = "Add economy to a player",
            permission = "azurite.economy.admin"
    )
    public static void executeAdd(Player player, @Param(name = "player") Player target, @Param(name = "amount") int amount) {
        User user = Factions.getInstance().getUserHandler().getUser(target);
        user.setBalance(user.getBalance() + amount);
        player.sendMessage(CC.t("&eYou have added &9$" + amount + " &eto &c" + target.getName() + "&e."));
    }

    @Command(
            names = {"economy take"},
            description = "Take economy from a player",
            permission = "azurite.economy.admin"
    )
    public static void executeTake(Player player, @Param(name = "player") Player target, @Param(name = "amount") int amount) {
        User user = Factions.getInstance().getUserHandler().getUser(target);
        user.takeBalance(amount);
        player.sendMessage(CC.t("&eYou have taken &9$" + amount + " &efrom &c" + target.getName() + "&e."));
    }
}