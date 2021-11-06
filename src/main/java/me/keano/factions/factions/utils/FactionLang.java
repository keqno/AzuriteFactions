package me.keano.factions.factions.utils;

import me.keano.factions.Factions;
import me.keano.factions.utils.CC;

public class FactionLang {

    public static String NOT_IN_FACTION;
    public static String ALREADY_IN_FACTION;
    public static String FACTION_ALREADY_EXISTS;
    public static String FACTION_NOT_FOUND;
    public static String ONLY_OFFICERS;
    public static String ONLY_LEADER;
    public static String ONLY_CAPTAINS;

    static {
        NOT_IN_FACTION = CC.t("&7You are not in a faction.");
        FACTION_ALREADY_EXISTS = CC.t("&cThat faction already exists!");
        ALREADY_IN_FACTION = CC.t("&7You are already in a faction.");
        FACTION_NOT_FOUND = CC.t("&cThat faction could not be found.");
        ONLY_OFFICERS = CC.t("&eYou need to be the &cofficer &erole or higher to use this.");
        ONLY_CAPTAINS = CC.t("&eYou need to be the &ccaptain &erole or higher to use this.");
        ONLY_LEADER = CC.t("&eYou need to be the &cleader &ein order to use this.");
    }

    // Soon
    public String getString(String path) {
        return Factions.getInstance().getConfigHandler().getFactionLangYML().getString(path);
    }
}