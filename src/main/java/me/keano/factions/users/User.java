package me.keano.factions.users;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.users.settings.ScoreboardType;
import org.bson.Document;

import java.util.UUID;

@Getter
@Setter
public class User {

    // Stats
    private UUID uuid;
    private int kills;
    private int deaths;
    private int balance;
    private int ceexp;
    private int level;
    private int mobCoins;

    // Settings
    private ScoreboardType scoreboardType;
    private boolean showClaimTitles;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.kills = 0;
        this.deaths = 0;
        this.balance = 0;
        this.ceexp = 0;
        this.level = 1;
        this.mobCoins = 0;

        this.scoreboardType = ScoreboardType.DEFAULT;
        this.showClaimTitles = true;
    }

    public void takeBalance(int amount) {
        if (balance - amount <= 0) {
            balance = 0;
            return;
        }
        balance = balance - amount;
    }

    public void takeCeexp(int amount) {
        if (ceexp - amount <= 0) {
            ceexp = 0;
            return;
        }
        ceexp = ceexp - amount;
    }

    public void takeMobCoins(int amount) {
        if (mobCoins - amount <= 0) {
            mobCoins = 0;
            return;
        }
        mobCoins = mobCoins - amount;
    }

    public void save() {
        Document document = new Document("_id", uuid.toString());

        // Stats
        document.put("kills", kills);
        document.put("deaths", deaths);
        document.put("ceexp", ceexp);
        document.put("level", level);
        document.put("balance", balance);
        document.put("mobCoins", mobCoins);

        // Settings
        document.put("scoreboardType", scoreboardType.toString());
        document.put("showClaimTitles", showClaimTitles);

        MongoHandler.users.replaceOne(
                Filters.eq("_id", uuid.toString()), document, new ReplaceOptions().upsert(true));
    }
}
