package me.keano.factions.timers.ptimer;

import me.keano.factions.timers.type.PlayerTimer;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class GappleTimer extends PlayerTimer {

    public GappleTimer() {
        super("Gapple", "&6Gapple Timer");
    }

    @EventHandler
    public void onEat(PlayerItemConsumeEvent e) {
        if (e.getItem() == null) return;
        if (!e.getItem().isSimilar(new ItemBuilder(Material.GOLDEN_APPLE).data((short) 1).toItemStack())) return;

        Player player = e.getPlayer();

        if (hasCooldown(player)) {
            e.setCancelled(true);
            player.sendMessage(CC.t("&eYou need to wait &c" + getRemainingString(player.getUniqueId()) + " &ebefore using this."));
            return;
        }

        putCooldown(player, 45);
    }
}