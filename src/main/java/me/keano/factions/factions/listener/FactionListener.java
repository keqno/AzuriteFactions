package me.keano.factions.factions.listener;

import me.keano.factions.Factions;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.type.SafezoneFaction;
import me.keano.factions.factions.type.WarzoneFaction;
import me.keano.factions.utils.CC;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.github.paperspigot.Title;

public class FactionListener implements Listener {

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getFrom() == e.getTo()) return;

        Player player = e.getPlayer();
        Faction to = Factions.getInstance().getFactionHandler().getByLocation(e.getTo());
        Faction from = Factions.getInstance().getFactionHandler().getByLocation(e.getFrom());

        if (to == from) return;

        player.sendMessage(CC.t("&eEntering: " + to.getDisplayName(player) + "&e, Leaving: " + from.getDisplayName(player)));

        if (!Factions.getInstance().getUserHandler().getUser(player).isShowClaimTitles()) return;

        switch (to.getFactionType()) {
            case SAFEZONE:
                player.sendTitle(new Title(to.getDisplayName(player), CC.t("&e&oYou are safe here!")));
                break;

            case WARZONE:
                player.sendTitle(new Title(CC.t("&cWarzone"), CC.t("&e&oWatch your back!")));
                break;

            case WILDERNESS:
                player.sendTitle(new Title(CC.t("&7Wilderness"), CC.t("&e&oStart claiming here!")));
                break;

            case PLAYER:
                player.sendTitle(new Title(to.getDisplayName(player)));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                player.hideTitle();
            }
        }.runTaskLater(Factions.getInstance(), 20 * 3);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        Faction broken = Factions.getInstance().getFactionHandler().getByLocation(e.getBlock().getLocation());

        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (broken instanceof SafezoneFaction || broken instanceof WarzoneFaction) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.t("&eYou cannot do this in the territory of &c" + broken.getDisplayName(e.getPlayer())+ "&e."));
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;

        Faction interact = Factions.getInstance().getFactionHandler().getByLocation(e.getClickedBlock().getLocation());

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (interact instanceof SafezoneFaction || interact instanceof WarzoneFaction) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.t("&eYou cannot do this in the territory of &c" + interact.getDisplayName(e.getPlayer()) + "&e."));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent e) {
        Faction broken = Factions.getInstance().getFactionHandler().getByLocation(e.getBlockPlaced().getLocation());

        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (broken instanceof SafezoneFaction || broken instanceof WarzoneFaction) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.t("&eYou cannot do this in the territory of &c" + broken.getDisplayName(e.getPlayer()) + "&e."));
        }
    }
}