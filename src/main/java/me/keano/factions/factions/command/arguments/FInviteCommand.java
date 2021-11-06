package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import mkremins.fanciful.FancyMessage;
import net.evilblock.cubed.command.Command;
import net.evilblock.cubed.command.data.parameter.Param;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class FInviteCommand {

    public static HashMap<UUID, Faction> inviteCache = new HashMap<>();

    @Command(
            names = {"f invite", "t invite", "faction invite", "team invite"}
    )
    public static void execute(Player player, @Param(name = "player") Player target) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (!pf.isOfficerOrHigher(pf.getMember(player))) {
            player.sendMessage(FactionLang.ONLY_OFFICERS);
            return;
        }

        if (Factions.getInstance().getFactionHandler().getPlayerFac(target) != null) {
            player.sendMessage(CC.t("&cThat player is in a faction."));
            return;
        }

        if (pf.getMaxMembers() == pf.getPlayers().size()) {
            player.sendMessage(CC.t("&cYou have reached the maximum member count. Upgrade your faction!"));
            return;
        }

        if (target.getUniqueId().equals(player.getUniqueId())) {
            player.sendMessage(CC.t("&cYou cannot invite yourself!"));
            return;
        }

        if (inviteCache.containsKey(player.getUniqueId())) {
            player.sendMessage(CC.t("&cYou have already invited that player."));
            return;
        }

        FancyMessage targetMessage = new FancyMessage(CC.t("&c" + player.getName() + " &ehas invited you to &9" + pf.getName() + "&e."));
        targetMessage.tooltip(CC.t("&aClick to accept invitation."));
        targetMessage.command("/f join " + pf.getName());
        targetMessage.send(target);

        player.sendMessage(CC.t("&eYou have invited &c" + target.getName() + " &eto the faction."));

        FancyMessage playerMessage = new FancyMessage(
                CC.t("&7Click this message to cancel the invite."));
        playerMessage.command("/f cancelinvite " + target.getName());
        playerMessage.send(player);

        pf.broadcastToMembers(CC.t("&c" + player.getName() + " &ehas invited &9" + target.getName() +
                " &eto the faction."));

        inviteCache.put(target.getUniqueId(), pf);
        new BukkitRunnable() {
            @Override
            public void run() {
                inviteCache.remove(target.getUniqueId());
            }
        }.runTaskLater(Factions.getInstance(), (20 * 60 * 5));
    }
}