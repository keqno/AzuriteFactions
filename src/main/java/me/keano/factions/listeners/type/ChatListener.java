package me.keano.factions.listeners.type;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.users.User;
import me.keano.factions.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        User user = Factions.getInstance().getUserHandler().getUser(e.getPlayer());
        Player player = e.getPlayer();
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);
//        Profile coreProfile = Profile.getByUuid(player.getUniqueId());

        if (e.isCancelled()) return;

        for (Player receiver : e.getRecipients()) {
            e.setCancelled(true);
            receiver.sendMessage(CC.t(
                    (pf != null ? pf.getDisplayName(receiver) : "&c*")
                            + " &7LVL:&f" + user.getLevel()
                            + " "
                            + "&7&lAddOwnRank"
                            + " &f" + player.getName()
                            + " &7") + e.getMessage());
        }
    }
}