package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class FListCommand {

    @Command(
            names = {"f list", "t list", "faction list", "team list"}
    )
    public static void execute(CommandSender sender, @Param(name = "page", defaultValue = "1") int page) {
        List<Faction> factions = Factions.getInstance().getFactionHandler().getFactions()
                .values().stream()
                .filter(PlayerFaction.class::isInstance)
                .map(PlayerFaction.class::cast)
                .filter(playerFaction -> playerFaction.getOnlinePlayers().size() > 0)
                .sorted(Comparator.comparingInt(o -> o.getOnlinePlayers().size()))
                .collect(Collectors.toList());

        if (factions.isEmpty()) {
            sender.sendMessage(CC.t("&cThere is no factions online."));
            return;
        }

        StringBuilder builder = new StringBuilder();

        for (Faction faction : factions)
            builder.append((sender instanceof Player ? faction.getDisplayName((Player) sender) : faction.getName()));

        ChatPaginator.ChatPage chatPage = ChatPaginator.paginate(builder.toString(), page, 40, 10);

        if (page > chatPage.getTotalPages()) {
            sender.sendMessage(CC.t("&cThat page does not exist."));
            return;
        }

        sender.sendMessage(CC.STRAIGHT_LINE);
        sender.sendMessage(CC.t("&9&lFaction List &7(Page: " + page + "/" + chatPage.getTotalPages() + ")"));

        int i = 1;

        for (String string : chatPage.getLines()) {
            String name = string
                    .replaceAll("§c", "")
                    .replaceAll("§a", "");

            PlayerFaction faction = (PlayerFaction) Factions.getInstance().getFactionHandler().getFaction(name);

            if (faction == null) continue;

            sender.sendMessage(CC.t("&7" + i + ". &e" + string + " &7[" +
                    faction.getOnlinePlayers().size() + "/" + faction.getPlayers().size() + "]"));
            i++;
        }

        sender.sendMessage(CC.t("&eTotal factions: &c" + factions.size()));
        sender.sendMessage(CC.STRAIGHT_LINE);
    }
}