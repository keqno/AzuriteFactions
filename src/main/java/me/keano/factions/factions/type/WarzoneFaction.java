package me.keano.factions.factions.type;

import lombok.Getter;
import lombok.Setter;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.FactionType;
import me.keano.factions.utils.CC;
import org.bukkit.entity.Player;

@Getter
@Setter
public class WarzoneFaction extends Faction {

    public WarzoneFaction() {
        super("Warzone", FactionType.WARZONE);
    }

    @Override
    public String getDisplayName(Player player) {
        return CC.t("&cWarzone");
    }
}