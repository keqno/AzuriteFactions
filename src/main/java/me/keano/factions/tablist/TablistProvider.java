package me.keano.factions.tablist;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.Utilities;
import me.keano.factions.utils.tablist.entry.TabElement;
import me.keano.factions.utils.tablist.entry.TabElementHandler;
import me.keano.factions.utils.tablist.skin.SkinType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.concurrent.atomic.AtomicInteger;

public class TablistProvider implements TabElementHandler {

    @Override
    public TabElement getElement(Player player) {
        TabElement element = new TabElement();

        element.setHeader(CC.t("&9&lAzurite &7┃ &fFactions"));

        // 1st
        element.add(0, 0, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 1, "&9&l", -1, SkinType.LIGHT_GRAY);
        element.add(0, 2, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 3, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 4, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 5, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 6, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 7, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 8, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 9, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 10, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 11, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 12, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 13, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 14, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 15, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 16, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 17, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 18, "", -1, SkinType.LIGHT_GRAY);
        element.add(0, 19, "", -1, SkinType.LIGHT_GRAY);

        // 2nd
        element.add(1, 1, "&9&lAzurite &7┃ &fFactions", -1, SkinType.LIGHT_GRAY);
        element.add(1, 2, "&6* &eOnline&7: &f" + Bukkit.getOnlinePlayers().size() + "/" + Bukkit.getMaxPlayers(), -1, SkinType.LIGHT_GRAY);

        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        element.add(1, 4, "&9&lYour Faction", -1, SkinType.LIGHT_GRAY);

        if (pf != null) {
            element.add(1, 5, "&6* &eName&7: &f" + pf.getName(), -1, SkinType.LIGHT_GRAY);
            element.add(1, 6, "&6* &eOnline&7: &f" + pf.getOnlinePlayers().size() + "/" + pf.getPlayers().size(), -1, SkinType.LIGHT_GRAY);
            element.add(1, 7, "&6* &eRally&7: &f" + (pf.getRally() != null ? Utilities.getPretty(pf.getRally(), false) : "None"), -1, SkinType.LIGHT_GRAY);

            element.add(1, 9, "&2" + pf.getName(), -1, SkinType.LIGHT_GRAY);

            AtomicInteger atomicInteger = new AtomicInteger(10);
            pf.getOnlinePlayers().stream()
                    .limit(7)
                    .map(pf::getMember)
                    .sorted((o1, o2) -> o2.getRole().ordinal() - o1.getRole().ordinal())
                    .forEach(member -> element.add(1, atomicInteger.getAndIncrement(),
                            "&a" + member.getAsterisk() + Bukkit.getPlayer(member.getUuid()).getName(),
                            -1, SkinType.LIGHT_GRAY));
        } else {
            element.add(1, 5, "&6* &eUse &c/f create &eto", -1, SkinType.LIGHT_GRAY);
            element.add(1, 6, "&6* &ecreate one", -1, SkinType.LIGHT_GRAY);
        }

        // 3rd
        element.add(2, 0, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 1, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 2, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 3, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 4, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 5, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 6, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 7, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 8, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 9, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 10, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 11, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 12, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 13, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 14, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 15, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 16, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 17, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 18, "", -1, SkinType.LIGHT_GRAY);
        element.add(2, 19, "", -1, SkinType.LIGHT_GRAY);

        // 4th
        element.add(3, 0, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 1, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 2, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 3, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 4, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 5, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 6, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 7, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 8, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 9, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 10, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 11, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 12, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 13, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 14, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 15, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 16, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 17, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 18, "", -1, SkinType.LIGHT_GRAY);
        element.add(3, 19, "", -1, SkinType.LIGHT_GRAY);

        element.setFooter(CC.t("&eThere is currently &c" + Bukkit.getOnlinePlayers().size() + " &eplayers online."));
        return element;
    }
}