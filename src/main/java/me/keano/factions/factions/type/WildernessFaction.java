package me.keano.factions.factions.type;

import me.keano.factions.factions.Faction;
import me.keano.factions.factions.FactionType;
import me.keano.factions.utils.CC;
import org.bukkit.entity.Player;

public class WildernessFaction extends Faction {
    
    public WildernessFaction() {
        super("Wilderness", FactionType.WILDERNESS);
    }

    @Override
    public String getDisplayName(Player player) {
        return CC.t("&7Wilderness");
    }
}