package me.keano.factions.timers.type;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.timers.Timer;
import me.keano.factions.timers.TimerType;
import me.keano.factions.utils.Formatter;
import org.bson.Document;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class PlayerTimer extends Timer {

    private HashMap<UUID, Long> timerCache;

    public PlayerTimer(String name, String displayName) {
        super(name, displayName, TimerType.PLAYER);
        this.timerCache = new HashMap<>();
    }

    public void putCooldown(Player player, long seconds) {
        timerCache.remove(player.getUniqueId());
        timerCache.put(player.getUniqueId(), System.currentTimeMillis() + (seconds * 1000));
    }

    public void removeCooldown(Player player) {
        timerCache.remove(player.getUniqueId());
    }

    public boolean hasCooldown(Player player) {
        if (timerCache.containsKey(player.getUniqueId()) && timerCache.get(player.getUniqueId()) < System.currentTimeMillis())
            removeCooldown(player);

        return timerCache.containsKey(player.getUniqueId()) && timerCache.get(player.getUniqueId()) >= System.currentTimeMillis();
    }

    public String getRemainingString(UUID uuid) {
        if (timerCache.containsKey(uuid) && timerCache.get(uuid) < System.currentTimeMillis())
            timerCache.remove(uuid);

        if (timerCache.containsKey(uuid))
            return Formatter.getRemaining(timerCache.get(uuid) - System.currentTimeMillis(), true);

        return "0:00";
    }

    public long getRemaining(UUID uuid) {
        if (timerCache.containsKey(uuid))
            return timerCache.get(uuid) - System.currentTimeMillis();

        return 0L;
    }

    public void clean() {
        for (UUID uuid : timerCache.keySet()) {
            if (getRemaining(uuid) <= 0L)
                timerCache.remove(uuid);
        }
    }

    public void save() {
        for (UUID uuid : timerCache.keySet()) {

            Document document = new Document("_id", uuid.toString());
            document.put("name", this.name);
            document.put("remaining", this.getRemaining(uuid));
            document.put("timerType", this.getTimerType().toString());

            MongoHandler.timers.replaceOne(Filters.eq("_id", uuid.toString()), document, new ReplaceOptions().upsert(true));
        }
    }
}