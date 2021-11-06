package me.keano.factions.factions.listener;

import me.keano.factions.Factions;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.type.SafezoneFaction;
import me.keano.factions.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SafezoneListener implements Listener {

    // Attacking a player inside
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;

        Player player = (Player) e.getEntity();
        Faction loc = Factions.getInstance().getFactionHandler().getByLocation(player.getLocation());

        if (loc instanceof SafezoneFaction) {
            e.setCancelled(true);
            e.getDamager().sendMessage(CC.t("&eYou cannot attack &c" + player.getName() + " &eas they are in a safezone."));
        }
    }

    @EventHandler
    public void onDamage2(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)) return;

        Player player = (Player) e.getDamager();
        Faction loc = Factions.getInstance().getFactionHandler().getByLocation(player.getLocation());

        if (loc instanceof SafezoneFaction) {
            e.setCancelled(true);
            player.sendMessage(CC.t("&eYou cannot attack &c" + e.getEntity().getName() + " &ewhile in a safezone."));
        }
    }
}