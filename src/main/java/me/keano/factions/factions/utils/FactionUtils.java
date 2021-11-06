package me.keano.factions.factions.utils;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import org.bukkit.entity.Player;

public class FactionUtils {

    public static boolean isTeamMate(Player attacker, Player damaged) {
        PlayerFaction attackerPF = Factions.getInstance().getFactionHandler().getPlayerFac(attacker);
        PlayerFaction damagedPF = Factions.getInstance().getFactionHandler().getPlayerFac(damaged);
        return damagedPF != null && damagedPF.equals(attackerPF);
    }
}