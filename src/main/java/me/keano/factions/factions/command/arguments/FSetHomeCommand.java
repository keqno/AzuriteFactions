package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;

public class FSetHomeCommand {

    @Command(
            names = {"f sethome", "t sethome", "faction sethome", "team sethome"}
    )
    public static void execute(Player player) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);
        Faction loc = Factions.getInstance().getFactionHandler().getByLocation(player.getLocation());

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (!(loc instanceof PlayerFaction)) {
            player.sendMessage(CC.t("&cYour HQ needs to be in your claim!"));
            return;
        }

        PlayerFaction pfLoc = (PlayerFaction) loc;

        if (!pfLoc.getPlayers().contains(player.getUniqueId())) {
            player.sendMessage(CC.t("&cYour HQ needs to be in your claim!"));
            return;
        }

        if (!pf.isCaptainOrHigher(pf.getMember(player))) {
            player.sendMessage(FactionLang.ONLY_CAPTAINS);
            return;
        }

        pf.setHq(player.getLocation());
        pf.save();
        pf.broadcastToMembers("&9" + player.getName() + " &ehas updated the faction home.");
    }
}