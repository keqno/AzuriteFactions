package me.keano.factions.warps;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.utils.Serializer;
import me.keano.factions.warps.listener.WarpListener;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class WarpHandler {

    private final Factions instance;

    @Getter
    private final List<Warp> warps;

    public WarpHandler(Factions instance) {
        this.instance = instance;
        this.warps = new ArrayList<>();
        this.load();
        this.register();
    }

    public void load() {
        for (Document document : MongoHandler.warps.find()) {
            Warp warp = new Warp(
                    document.getString("_id"),
                    Serializer.destringifyLocation(document.getString("location")),
                    document.getLong("cooldown"));
            warps.add(warp);
        }
    }

    public void register() {
        instance.getServer().getPluginManager().registerEvents(new WarpListener(), instance);
    }

    public Warp getByName(String name) {
        for (Warp warp : warps) {
            if (warp.getName().equalsIgnoreCase(name))
                return warp;
        }
        return null;
    }
}