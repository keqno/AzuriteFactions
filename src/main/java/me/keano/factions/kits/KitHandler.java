package me.keano.factions.kits;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.kits.command.KitCommand;
import me.keano.factions.kits.enchants.sword.*;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ConfigYML;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class KitHandler {

    private final Factions instance;

    @Getter
    private final HashMap<String, Kit> kitCache;
    @Getter
    private final HashMap<String, KitEnchant> kitEnchants;

    public KitHandler(Factions instance) {
        this.instance = instance;
        this.kitCache = new HashMap<>();
        this.kitEnchants = new HashMap<>();
        this.cache();
        this.registerListeners();
        this.registerEnchants();
        instance.getServer().getCommandMap().register("factions", new KitCommand());
    }

    public void cache() {
        // reload config
        ConfigYML kitsYML = instance.getConfigHandler().getKitsYML();
        kitsYML.reload();

        // clear in case of reload
        kitCache.clear();

        int kits = 0;

        CC.printMessage("&4===&c====================&c===");
        CC.printMessage("- &eLoading kits...");

        for (String p : kitsYML.getConfigurationSection("KITS").getKeys(false)) {
            String path = "KITS." + p;
            Kit kit = new Kit(kitsYML.getString(path + ".NAME"), kitsYML.getString(path + ".DISPLAY_NAME"));

            for (String string : kitsYML.getStringList(path + ".ITEMS"))
                kit.getItems().add(string);

            for (String string : kitsYML.getStringList(path + ".EFFECTS"))
                kit.getEffects().add(string);

            kitCache.put(kit.getName(), kit);
            CC.printMessage("- &eLoaded " + kit.getName());
            kits++;
        }

        CC.printMessage("- &eTotal kits&7: " + kits);
        CC.printMessage("&4===&c====================&c===");
    }

    public Kit getKit(String name) {
        for (Kit kit : kitCache.values()) {
            if (kit.getName().equalsIgnoreCase(name))
                return kit;
        }
        return null;
    }

    public void giveKit(Player player, String name) {
        player.getInventory().addItem(kitCache.get(name).getChestItem());
        player.updateInventory();
    }

    public void createKit(String name, String displayName) {
        Kit kit = new Kit(name, displayName);
        kitCache.put(name, kit);
    }

    public void registerEnchants() {
        kitEnchants.put("Fame", new FameKitEnchant());
        kitEnchants.put("Harvest", new HarvestKitEnchant());
        kitEnchants.put("Metallic", new MetallicKitEnchant());
        kitEnchants.put("TimeStrike", new TimeStrikeKitEnchant());
        kitEnchants.put("Trident", new TridentKitEnchant());
        kitEnchants.put("ZeusBlood", new ZeusBloodKitEnchant());

        for (Listener listener : kitEnchants.values())
            instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public void registerListeners() {
        for (Listener listener : kitCache.values()) {
            instance.getServer().getPluginManager().registerEvents(listener, instance);
        }
    }
}