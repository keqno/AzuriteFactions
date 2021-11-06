package me.keano.factions.warps;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.utils.Serializer;
import org.bson.Document;
import org.bukkit.Location;

@Getter
@Setter
public class Warp {

    private String name;
    private Location location;
    private long cooldown;

    public Warp(String name, Location location, long cooldown) {
        this.name = name;
        this.location = location;
        this.cooldown = cooldown;
    }

    public void save() {
        Document document = new Document("_id", name);
        document.put("location", Serializer.stringifyLocation(location));
        document.put("cooldown", cooldown);

        MongoHandler.warps.replaceOne(Filters.eq("_id", name), document, new ReplaceOptions().upsert(true));
    }

    public void delete() {
        MongoHandler.warps.deleteOne(Filters.eq("_id", name));
    }
}