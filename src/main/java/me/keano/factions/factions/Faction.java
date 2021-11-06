package me.keano.factions.factions;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.keano.factions.Factions;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.utils.CC;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class Faction {

    protected String name;
    protected UUID uuid;
    protected FactionType factionType;

    public Faction(String name, FactionType factionType) {
        this.name = name;
        this.uuid = UUID.randomUUID();
        this.factionType = factionType;
    }

    public String getDisplayName(Player player) {
        return name;
    }

    public List<String> getFactionInfo() {
        List<String> list = new ArrayList<>();
        list.add(CC.STRAIGHT_LINE);
        list.add("&e" + name);
        list.add("&c&oThis is a system faction.");
        list.add(CC.STRAIGHT_LINE);
        return CC.t(list);
    }

    public void save() {
        Document document = new Document("_id", uuid.toString());
        document.put("name", name);
        document.put("factionType", factionType.toString());

        MongoHandler.factions.replaceOne(
                Filters.eq("_id", uuid.toString()), document, new ReplaceOptions().upsert(true));
    }

    public void delete() {
        Factions.getInstance().getFactionHandler().getFactions().remove(this.getUuid());
        MongoHandler.factions.deleteOne(Filters.eq("_id", uuid.toString()));
    }
}