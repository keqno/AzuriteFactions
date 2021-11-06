package me.keano.factions.listeners;

import me.keano.factions.Factions;
import me.keano.factions.listeners.type.ChatListener;
import me.keano.factions.listeners.type.EssentialsListener;
import org.bukkit.event.Listener;

import java.util.HashSet;
import java.util.Set;

public class ListenerHandler {

    private final Factions instance;
    private final Set<Listener> listeners;

    public ListenerHandler(Factions instance) {
        this.instance = instance;
        this.listeners = new HashSet<>();
        this.register();
    }

    public void register() {
        listeners.add(new ChatListener());
        listeners.add(new EssentialsListener());

        for (Listener listener : listeners)
            instance.getServer().getPluginManager().registerEvents(listener, instance);
    }
}
