package me.keano.factions.wraths;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.wraths.listener.WrathListener;
import me.keano.factions.wraths.souls.SoulsHandler;
import me.keano.factions.wraths.type.WrathOfAres;
import org.bukkit.event.Listener;

import java.util.HashSet;

public class WrathHandler {

    private final Factions instance;

    @Getter
    private final SoulsHandler soulsHandler;

    @Getter
    private final HashSet<Wrath> wraths;

    public WrathHandler(Factions instance) {
        this.instance = instance;
        this.wraths = new HashSet<>();
        this.soulsHandler = new SoulsHandler(instance);
        this.register();
    }

    public void register() {
        wraths.add(new WrathOfAres());

        for (Listener listener : wraths)
            instance.getServer().getPluginManager().registerEvents(listener, instance);

        instance.getServer().getPluginManager().registerEvents(new WrathListener(), instance);
    }

    public Wrath getByName(String string) {
        for (Wrath wrath : wraths) {
            if (wrath.getName().equalsIgnoreCase(string))
                return wrath;
        }
        return null;
    }
}