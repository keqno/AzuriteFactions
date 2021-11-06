package me.keano.factions.timers.ptimer;

import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.timers.type.PlayerTimer;
import me.keano.factions.utils.CC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CombatTimer extends PlayerTimer {

    public CombatTimer() {
        super("Combat", "&cCombat Timer");
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!EnchantUtils.isPlayer(e.getEntity(), e.getDamager())) return;
        if (e.isCancelled()) return;

        Player damager = (Player) e.getDamager();
        Player damaged = (Player) e.getEntity();

        if (FactionUtils.isTeamMate(damager, damaged)) return;

        if (!hasCooldown(damager))
            damager.sendMessage(CC.t("&eYou have been spawn tagged for &c45 &eseconds."));

        if (!hasCooldown(damaged))
            damaged.sendMessage(CC.t("&eYou have been spawn tagged for &c45 &eseconds."));

        putCooldown(damager, 45);
        putCooldown(damaged, 45);
    }
}