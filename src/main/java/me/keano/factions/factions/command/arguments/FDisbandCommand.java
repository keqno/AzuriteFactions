package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.player.Role;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;

public class FDisbandCommand {

    @Command(
            names = {"f disband", "t disband", "faction disband", "team disband"}
    )
    public static void execute(Player player) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (!pf.getMember(player).getRole().equals(Role.LEADER)) {
            player.sendMessage(FactionLang.ONLY_LEADER);
            return;
        }

        player.sendMessage(CC.t("&c&oDisbanded faction successfully!"));
        pf.broadcastToMembers("&c" + player.getName() + " &ehas disbanded the faction.");
        pf.delete();
    }
}