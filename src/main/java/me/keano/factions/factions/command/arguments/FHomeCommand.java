package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

import java.util.HashMap;
import java.util.UUID;

public class FHomeCommand {

    private static final HashMap<UUID, Integer> cache = new HashMap<>();

    @Command(
            names = {"f home", "t home", "faction home", "team home"}
    )
    public static void execute(Player player) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (pf.getHq() == null) {
            player.sendMessage(CC.t("&cYour faction does not have a HQ!"));
            return;
        }

        cache.put(player.getUniqueId(), 10);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (cache.get(player.getUniqueId()) == null)
                    cancel();

                if (cache.get(player.getUniqueId()) == 1) {
                    cache.remove(player.getUniqueId());
                    player.sendTitle(new Title(CC.t("&aTeleported!")));
                    return;
                }

                if (cache.containsKey(player.getUniqueId()))
                    player.sendTitle(new Title(CC.t("&eTeleporting to HQ in"), CC.t("&c" + cache.get(player.getUniqueId()) + "s")));
                cache.put(player.getUniqueId(), cache.get(player.getUniqueId()) - 1);
            }
        }.runTaskTimer(Factions.getInstance(), 0L, 20L);
    }
}