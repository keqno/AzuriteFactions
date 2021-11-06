package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;

public class FClaimCommand {

    @Command(
            names = {"f claim", "t claim", "faction claim", "team claim"}
    )
    public static void execute(Player player) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (!pf.isCaptainOrHigher(pf.getMember(player))) {
            player.sendMessage(FactionLang.ONLY_CAPTAINS);
            return;
        }

        if (!pf.canClaim(player))
            return;

        if (pf.getBalance() < (pf.getClaims().size() + 1) * 100000) {
            player.sendMessage(CC.t("&eYou need &c$" + (pf.getClaims().size() + 1) * 100000 + " &eto claim a chunk."));
            return;
        }

        pf.takeBalance((pf.getClaims().size() + 1)  * 100000);
        pf.getClaims().add(player.getLocation().getChunk());
        pf.save();
    }
}