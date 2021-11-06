package me.keano.factions.factions.type;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.FactionType;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.util.bukkit.cuboid.Cuboid;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SafezoneFaction extends Faction {

    private List<Cuboid> claims;

    public SafezoneFaction(String name) {
        super(name, FactionType.SAFEZONE);
        this.claims = new ArrayList<>();
    }

    @Override
    public String getDisplayName(Player player) {
        return CC.t("&a" + name);
    }

    @Override
    public void save() {
        Document document = new Document("_id", uuid.toString());
        document.put("name", name);
        document.put("factionType", factionType.toString());

        MongoHandler.factions.replaceOne(
                Filters.eq("_id", uuid.toString()), document, new ReplaceOptions().upsert(true));
    }
}