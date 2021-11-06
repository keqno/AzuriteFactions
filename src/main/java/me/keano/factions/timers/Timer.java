package me.keano.factions.timers;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.event.Listener;

@Getter
@Setter
public class Timer implements Listener {

    protected String name;
    protected String displayName;
    protected TimerType timerType;

    public Timer(String name, String displayName, TimerType timerType) {
        this.name = name;
        this.displayName = displayName;
        this.timerType = timerType;
    }
}