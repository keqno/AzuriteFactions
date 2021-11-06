package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.player.Role;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;

public class FLeaveCommand {

    @Command(
            names = {"f leave", "t leave", "faction leave", "team leave"}
    )
    public static void execute(Player player) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (pf.getMember(player).getRole().equals(Role.LEADER)) {
            player.sendMessage(CC.t("&cYou cannot leave the faction as a leader."));
            return;
        }

        player.sendMessage(CC.t("&eYou left the faction &c" + pf.getName() + "&e."));
        pf.getMembers().remove(pf.getMember(player));
        pf.getPlayers().remove(player.getUniqueId());
        pf.save();
        pf.broadcastToMembers("&c" + player.getName() + " &ehas left the faction.");
    }
}