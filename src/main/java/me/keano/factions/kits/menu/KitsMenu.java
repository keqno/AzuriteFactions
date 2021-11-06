package me.keano.factions.kits.menu;

import me.keano.factions.utils.menu.Menu;
import me.keano.factions.utils.menu.button.Button;
import me.keano.factions.utils.menu.type.MenuType;
import org.bukkit.entity.Player;

import java.util.Set;

public class KitsMenu extends Menu {

    public KitsMenu() {
        super("&9&lKits", 27 / 3, MenuType.DEFAULT);
    }

    @Override
    public Set<Button> getButtons(Player player) {
        return null;
    }
}
