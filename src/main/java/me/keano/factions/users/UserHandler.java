package me.keano.factions.users;

import lombok.Getter;
import lombok.Setter;
import me.keano.factions.Factions;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.users.listener.UserListener;
import me.keano.factions.users.settings.ScoreboardType;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class UserHandler {

    private final Factions instance;

    @Getter
    private final HashMap<UUID, User> userCache;

    @Setter @Getter private boolean loaded;

    public UserHandler(Factions instance) {
        this.instance = instance;
        this.userCache = new HashMap<>();
        this.load();
        this.register();
        this.loaded = false;
    }

    public void register() {
        instance.getServer().getPluginManager().registerEvents(new UserListener(), instance);
    }

    public User getUser(Player player) {
        return userCache.get(player.getUniqueId());
    }

    public User getUser(UUID uuid) {
        return userCache.get(uuid);
    }

    // Need to load them all so kills are accessible in /f who even for offline players
    public void load() {
        for (Document document : MongoHandler.users.find()) {

            User user = new User(UUID.fromString(document.getString("_id")));

            // Stats
            user.setLevel(document.getInteger("level"));
            user.setBalance(document.getInteger("balance"));
            user.setKills(document.getInteger("kills"));
            user.setDeaths(document.getInteger("deaths"));
            user.setCeexp(document.getInteger("ceexp"));
            user.setMobCoins(document.getInteger("mobCoins"));

            // Settings
            user.setShowClaimTitles(document.getBoolean("showClaimTitles"));
            user.setScoreboardType(ScoreboardType.valueOf(document.getString("scoreboardType")));

            userCache.put(user.getUuid(), user);
        }
    }

    public void save() {
        for (User user : userCache.values())
            user.save();
    }
}
