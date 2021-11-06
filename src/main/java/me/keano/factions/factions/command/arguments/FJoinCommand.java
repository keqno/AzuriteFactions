package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.player.Member;
import me.keano.factions.factions.player.Role;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.entity.Player;

public class FJoinCommand {

    @Command(
            names = {"f join", "t join", "faction join", "team join"}
    )
    public static void execute(Player player, @Param(name = "faction") String faction) {
        PlayerFaction targetPf = Factions.getInstance().getFactionHandler().getPlayerFac(faction);

        if (Factions.getInstance().getFactionHandler().getPlayerFac(player) != null) {
            player.sendMessage(FactionLang.ALREADY_IN_FACTION);
            return;
        }

        if (targetPf == null) {
            player.sendMessage(FactionLang.FACTION_NOT_FOUND);
            return;
        }

        if (FInviteCommand.inviteCache.get(player.getUniqueId()) == null) {
            player.sendMessage(CC.t("&cThat faction has not invited you!"));
            return;
        }

        if (!FInviteCommand.inviteCache.containsKey(player.getUniqueId()) &&
                !FInviteCommand.inviteCache.get(player.getUniqueId()).getUuid().equals(targetPf.getUuid())) {
            player.sendMessage(CC.t("&cThat faction has not invited you!"));
            return;
        }

        player.sendMessage(CC.t("&eYou have joined &9" + targetPf.getName() + "&e."));
        FInviteCommand.inviteCache.remove(player.getUniqueId());
        targetPf.getPlayers().add(player.getUniqueId());
        targetPf.getMembers().add(new Member(player.getUniqueId(), Role.MEMBER));
        targetPf.save();
        targetPf.broadcastToMembers("&c" + player.getName() + " &ehas joined the faction.");
    }
}
