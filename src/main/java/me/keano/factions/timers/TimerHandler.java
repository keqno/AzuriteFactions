package me.keano.factions.timers;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.timers.ptimer.CombatTimer;
import me.keano.factions.timers.ptimer.EnderpearlTimer;
import me.keano.factions.timers.ptimer.GappleTimer;
import me.keano.factions.timers.type.PlayerTimer;
import org.bson.Document;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.UUID;

public class TimerHandler {

    private final Factions instance;

    @Getter
    private final CombatTimer combatTimer;
    @Getter
    private final EnderpearlTimer enderpearlTimer;
    @Getter
    private final GappleTimer gappleTimer;

    @Getter
    private final HashSet<Timer> timers;

    public TimerHandler(Factions instance) {
        this.instance = instance;
        this.timers = new HashSet<>();
        this.combatTimer = new CombatTimer();
        this.enderpearlTimer = new EnderpearlTimer();
        this.gappleTimer = new GappleTimer();
        this.register();
        this.load();
    }

    public void load() {
        for (Document document : MongoHandler.timers.find()) {
            TimerType type = TimerType.valueOf(document.getString("timerType"));
            String name = document.getString("name");

            switch (type) {
                case PLAYER:
                    PlayerTimer timer = (PlayerTimer) getByName(name);
                    timer.getTimerCache().put(UUID.fromString(document.getString("_id")), document.getLong("remaining"));
                    break;

                case SERVER:

            }
        }
    }

    public void register() {
        timers.add(combatTimer);
        timers.add(enderpearlTimer);
        timers.add(gappleTimer);

        for (Listener listener : timers)
            instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public Timer getByName(String name) {
        for (Timer timer : timers) {
            if (timer.getName().equalsIgnoreCase(name))
                return timer;
        }
        return null;
    }

    public void save() {
        for (Timer timer : Factions.getInstance().getTimerHandler().getTimers()) {
            if (!(timer instanceof PlayerTimer)) continue;

            PlayerTimer pt = (PlayerTimer) timer;
            pt.clean();
        }

        timers.stream().filter(PlayerTimer.class::isInstance)
                .map(PlayerTimer.class::cast)
                .forEach(PlayerTimer::save);
    }
}