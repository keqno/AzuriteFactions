package me.keano.factions.relics;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ConfigYML;

import java.util.HashMap;

public class RelicHandler {

    private final Factions instance;

    @Getter
    private final HashMap<String, Relic> relicCache;

    public RelicHandler(Factions instance) {
        this.instance = instance;
        this.relicCache = new HashMap<>();
    }

    // this isn't done
    public void load() {
        ConfigYML relicsYML = instance.getConfigHandler().getRelicsYML();
        CC.printMessage("&9[Azurite] &dLoading relics...", true);

        for (String section : relicsYML.getConfigurationSection("RELICS").getKeys(false)) {

            String path = "RELICS." + section + ".";

            Relic relic = new Relic(relicsYML.getString(path + "NAME"), relicsYML.getString(path + "DISPLAY_NAME"));
            relic.setRewards(relicsYML.getStringList(path + "REWARDS"));
            CC.printMessage("&9[Azurite] &bLoaded " + relic.getName(), false);
        }
    }

    public Relic getRelic(String name) {
        return relicCache.get(name);
    }
}