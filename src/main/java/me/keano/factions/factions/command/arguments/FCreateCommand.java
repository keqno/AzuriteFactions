package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.entity.Player;

public class FCreateCommand {

    @Command(
            names = {"f create", "t create", "faction create", "team create"}
    )
    public static void execute(Player player, @Param(name = "name") String name) {
        if (Factions.getInstance().getFactionHandler().getPlayerFac(player) != null) {
            player.sendMessage(FactionLang.ALREADY_IN_FACTION);
            return;
        }

        if (Factions.getInstance().getFactionHandler().getFaction(name) != null) {
            player.sendMessage(FactionLang.FACTION_ALREADY_EXISTS);
            return;
        }

        if (name.length() > 12) {
            player.sendMessage(CC.t("&cYour faction name cannot be longer than 12 characters."));
            return;
        }

        if (!isAlpha(name) || name.contains(" ")) {
            player.sendMessage(CC.t("&cYour faction name must not contain special characters."));
            return;
        }

        PlayerFaction pf = new PlayerFaction(name, player.getUniqueId());
        pf.save();
        Factions.getInstance().getFactionHandler().getFactions().put(pf.getUuid(), pf);
        player.sendMessage(CC.t("&7To learn more, do /f help for more info."));
        CC.broadcast("&eFaction &9" + name + " &ehas been created by &c" + player.getName() + "&e.");
    }

    private static boolean isAlpha(String s) {
        return s != null && s.matches("^[a-zA-Z]*$");
    }
}