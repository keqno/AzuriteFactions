package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.player.Role;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;

public class FLevelUpCommand {

    @Command(
           names = {"f levelup", "t levelup", "faction levelup", "team levelup"}
    )
    public static void execute(Player player) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (pf.getLevel() == 15) {
            player.sendMessage(CC.t("&cYour faction is fully leveled up."));
            return;
        }

        if (!pf.getMember(player).getRole().equals(Role.LEADER)) {
            player.sendMessage(CC.t("&cOnly the leader can level up your faction."));
            return;
        }

        if (pf.getLevel() * 10000 < pf.getBalance()) {
            player.sendMessage(CC.t("&eYou need &c$" + (pf.getLevel() * 10000) + " &eof balance to level up."));

            if (pf.getLevel() * 10 < pf.getStrength())
                player.sendMessage(CC.t("&eYou need &c" + (pf.getLevel() * 10) + " &eof strength to level up."));

            return;
        }

        pf.setLevel(pf.getLevel() + 1);
        pf.takeBalance(pf.getLevel() * 10000);
    }
}