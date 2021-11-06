package me.keano.factions.configs;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.utils.ConfigYML;

public class ConfigHandler {

    @Getter
    private final ConfigYML kitsYML;
    @Getter
    private final ConfigYML relicsYML;
    @Getter
    private final ConfigYML factionLangYML;

    public ConfigHandler(Factions instance) {
        this.kitsYML = new ConfigYML(instance, "kits", instance.getDataFolder());
        this.relicsYML = new ConfigYML(instance, "relics", instance.getDataFolder());
        this.factionLangYML = new ConfigYML(instance, "faction-lang", instance.getDataFolder());

        instance.getConfig().options().copyDefaults(true);
        instance.saveConfig();
    }
}