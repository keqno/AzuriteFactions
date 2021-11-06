package me.keano.factions.warps.listener;

import me.keano.factions.utils.CC;
import me.keano.factions.warps.task.WarpTask;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class WarpListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo() == e.getFrom()) return;
        if (!WarpTask.getCache().containsKey(e.getPlayer().getUniqueId())) return;

        Player p = e.getPlayer();

        p.sendMessage(CC.t("&eWarping to &c" + WarpTask.getCache().get(p.getUniqueId()).getName() + " &ecancelled."));
        WarpTask.getCache().remove(p.getUniqueId());
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (e.isCancelled()) return;

        Player entity = (Player) e.getEntity();

        if (!WarpTask.getCache().containsKey(entity.getUniqueId())) return;

        entity.sendMessage(CC.t("&eWarping to &c" + WarpTask.getCache().get(entity.getUniqueId()).getName() + " &ecancelled."));
        WarpTask.getCache().remove(entity.getUniqueId());
    }
}