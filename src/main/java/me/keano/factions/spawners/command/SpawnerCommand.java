package me.keano.factions.spawners.command;

import me.keano.factions.Factions;
import me.keano.factions.spawners.Spawner;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpawnerCommand {

    @Command(
            names = {"spawner give"},
            permission = "azurite.spawner.admin"
    )
    public static void execute(CommandSender sender, @Param(name = "player") Player target, @Param(name = "spawner") String spawner,
                               @Param(name = "amount") int amount) {

        Spawner sp = Factions.getInstance().getSpawnerHandler().getByName(spawner);

        if (sp == null) {
            sender.sendMessage(CC.t("&cThat spawner does not exist!"));
            return;
        }

        ItemStack itemStack = sp.getItem().clone();
        itemStack.setAmount(amount);
        target.getInventory().addItem(itemStack);
        sender.sendMessage(CC.t("&eGave &c" + target.getName() + " &e" + amount + "x of &9" + sp.getName() + " Spawner&e."));
    }

    @Command(
            names = {"spawner list"},
            permission = "azurite.spawner.admin"
    )
    public static void execute(CommandSender sender) {
        sender.sendMessage(CC.STRAIGHT_LINE);
        sender.sendMessage(CC.t("&9&lSpawners"));

        for (Spawner spawner : Factions.getInstance().getSpawnerHandler().getSpawners())
            sender.sendMessage(CC.t(" &6* &e" + spawner.getName()));

        sender.sendMessage(CC.STRAIGHT_LINE);
    }
}