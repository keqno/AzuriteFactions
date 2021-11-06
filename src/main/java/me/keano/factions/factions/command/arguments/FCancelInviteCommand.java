package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class FCancelInviteCommand {

    @Command(
            names = {"f cancelinvite", "t cancelinvite", "faction cancelinvite", "team cancelinvite"}
    )
    public static void execute(Player player, @Param(name = "player") OfflinePlayer target) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (!pf.isOfficerOrHigher(pf.getMember(player))) {
            player.sendMessage(FactionLang.ONLY_OFFICERS);
            return;
        }

        if (!FInviteCommand.inviteCache.containsKey(target.getUniqueId())) {
            player.sendMessage(CC.t("&cYou have not invited that player."));
            return;
        }

        if (target.isOnline()) target.getPlayer().sendMessage(CC.t("&c" + player.getName() + " " +
                "&ehas cancelled their invite from &9" + FInviteCommand.inviteCache.get(player.getUniqueId()).getName()));
        player.sendMessage(CC.t("&aCancelled invitation successfully."));
        FInviteCommand.inviteCache.remove(target.getUniqueId());
    }
}