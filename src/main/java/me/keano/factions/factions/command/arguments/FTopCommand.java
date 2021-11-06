package me.keano.factions.factions.command.arguments;

import lombok.Setter;
import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import java.util.List;
import java.util.stream.Collectors;

public class FTopCommand {

    @Setter
    private static StringBuilder balanceSorted = new StringBuilder();
    @Setter
    private static StringBuilder strengthSorted = new StringBuilder();

    @Command(
            names = {"f top", "t top", "faction top", "team top"}
    )
    public static void execute(CommandSender sender, @Param(name = "balance/strength") String param, @Param(name = "page", defaultValue = "1") int page) {

        sort();

        switch (param.toUpperCase()) {
            case "BALANCE":

                int b = 1;

                ChatPaginator.ChatPage balancePage =
                        ChatPaginator.paginate(balanceSorted.toString(), page, 40, 10);

                sender.sendMessage(CC.STRAIGHT_LINE);
                sender.sendMessage(CC.t("&9&lTop Factions &7(Page: " + balancePage.getPageNumber() + "/" + balancePage.getTotalPages() + ")"));

                for (String string : balancePage.getLines()) {
                    String pfName = string
                            .replaceAll("§c", "")
                            .replaceAll("§a", "");

                    PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(pfName);

                    if (pf == null) continue;

                    String name = (sender instanceof Player ? pf.getDisplayName((Player) sender) : pf.getName());

                    sender.sendMessage(CC.t("&7" + b + ". &e" + name + " &f$" + pf.getBalance()));
                    b++;
                }

                sender.sendMessage(CC.STRAIGHT_LINE);
                break;

            case "STRENGTH":
                int s = 1;

                ChatPaginator.ChatPage strengthPage =
                        ChatPaginator.paginate(strengthSorted.toString(), page, 40, 10);

                sender.sendMessage(CC.STRAIGHT_LINE);
                sender.sendMessage(CC.t("&9&lTop Factions &7(Page: " + strengthPage.getPageNumber() + "/" + strengthPage.getTotalPages() + ")"));

                for (String string : strengthPage.getLines()) {
                    String pfName = string
                            .replaceAll("§c", "")
                            .replaceAll("§a", "");

                    PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(pfName);

                    if (pf == null) continue;

                    String name = (sender instanceof Player ? pf.getDisplayName((Player) sender) : pf.getName());

                    sender.sendMessage(CC.t("&7" + s + ". &e" + name + " &f" + pf.getStrength()));
                    s++;
                }

                sender.sendMessage(CC.STRAIGHT_LINE);
        }
    }

    public static void sort() {
        List<PlayerFaction> strength = Factions.getInstance().getFactionHandler().getFactions().values().stream()
                .filter(PlayerFaction.class::isInstance)
                .map(PlayerFaction.class::cast)
                .sorted((o1, o2) -> o2.getStrength() - o1.getStrength())
                .collect(Collectors.toList());

        List<PlayerFaction> balance = Factions.getInstance().getFactionHandler().getFactions().values().stream()
                .filter(PlayerFaction.class::isInstance)
                .map(PlayerFaction.class::cast)
                .sorted((o1, o2) -> o2.getStrength() - o1.getStrength())
                .collect(Collectors.toList());

        StringBuilder builderStrength = new StringBuilder();
        StringBuilder builderBalance = new StringBuilder();

        for (PlayerFaction string : strength)
            builderStrength.append(string.getName());

        for (PlayerFaction string : balance)
            builderBalance.append(string.getName());

        setBalanceSorted(builderBalance);
        setStrengthSorted(builderStrength);
    }
}