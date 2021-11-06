package me.keano.factions.users.listener;

import me.keano.factions.Factions;
import me.keano.factions.users.User;
import me.keano.factions.utils.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class UserListener implements Listener {

    @EventHandler
    public void onJoin(AsyncPlayerPreLoginEvent e) {
        if (!Factions.getInstance().getUserHandler().isLoaded()) {
            e.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_OTHER);
            e.setKickMessage(CC.t("&cServer is loading!"));
            return;
        }

        HashMap<UUID, User> userCache = Factions.getInstance().getUserHandler().getUserCache();

        if (userCache.containsKey(e.getUniqueId()))
            return;

        User user = new User(e.getUniqueId());
        userCache.put(e.getUniqueId(), user);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Factions.getInstance().getUserHandler().getUserCache().get(e.getPlayer().getUniqueId()).save();
    }
}