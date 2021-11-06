package me.keano.factions.warps.task;

import lombok.Getter;
import lombok.Setter;
import me.keano.factions.Factions;
import me.keano.factions.utils.CC;
import me.keano.factions.warps.Warp;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

@Getter
@Setter
public class WarpTask extends BukkitRunnable {

    @Getter
    private static HashMap<UUID, Warp> cache;

    private final Player player;
    private final Warp warp;

    public WarpTask(Player player, Warp warp) {
        this.warp = warp;
        this.player = player;

        this.runTaskLater(Factions.getInstance(), (20 * warp.getCooldown()));

        cache = new HashMap<>();
        cache.put(player.getUniqueId(), warp);

        player.sendMessage(CC.t("&eWarping to &c" + warp.getName() + "&e... Please wait &9" + warp.getCooldown() + "s&e."));
    }

    @Override
    public void run() {
        if (!cache.containsKey(player.getUniqueId())) return;
        
        player.teleport(warp.getLocation());
        cache.remove(player.getUniqueId());
        player.sendMessage(CC.t("&eWarped to &c" + warp.getName() + "&e."));
    }
}