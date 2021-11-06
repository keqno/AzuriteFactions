package me.keano.factions.timers.ptimer;

import me.keano.factions.timers.type.PlayerTimer;
import me.keano.factions.utils.CC;
import org.bukkit.Material;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

public class EnderpearlTimer extends PlayerTimer {

    public EnderpearlTimer() {
        super("Enderpearl", "&eEnderpearl");
    }

    @EventHandler
    public void onLaunch(ProjectileLaunchEvent e) {
        if (e.isCancelled()) return;
        if (!(e.getEntity() instanceof EnderPearl)) return;
        if (!(e.getEntity().getShooter() instanceof Player)) return;

        Player player = (Player) e.getEntity().getShooter();

        if (hasCooldown(player)) {
            player.getInventory().addItem(new ItemStack(Material.ENDER_PEARL));
            player.updateInventory();
            e.setCancelled(true);
            player.sendMessage(CC.t("&eYou need to wait &c" + getRemainingString(player.getUniqueId()) + " &ebefore using this."));
            return;
        }

        putCooldown(player, 16);
    }
}