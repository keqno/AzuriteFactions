package me.keano.factions.factions.listener;

import me.keano.factions.Factions;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionUtils;
import me.keano.factions.spawners.Spawner;
import me.keano.factions.utils.CC;
import org.bukkit.GameMode;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerFactionListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void onBreak(BlockBreakEvent e) {
        Faction broken = Factions.getInstance().getFactionHandler().getByLocation(e.getBlock().getLocation());

        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (broken instanceof PlayerFaction) {
            PlayerFaction pf = (PlayerFaction) broken;

            if (pf.getPlayers().contains(e.getPlayer().getUniqueId())) return;

            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.t("&eYou cannot do this in the territory of &c" + broken.getName() + "&e."));
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInteract(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;
        
        Faction broken = Factions.getInstance().getFactionHandler().getByLocation(e.getClickedBlock().getLocation());

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (broken instanceof PlayerFaction) {
            PlayerFaction pf = (PlayerFaction) broken;

            if (pf.getPlayers().contains(e.getPlayer().getUniqueId())) return;

            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.t("&eYou cannot do this in the territory of &c" + broken.getName() + "&e."));
        }
    }
    
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        if (e.getDamager() instanceof Player &&
                !FactionUtils.isTeamMate((Player) e.getEntity(), (Player) e.getDamager())) return;

        Player player = (Player) e.getEntity();
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac((Player) e.getEntity());
        
        e.setCancelled(true);
        e.getDamager().sendMessage(CC.t("&eYou cannot hurt &a" + pf.getMember(player).getAsterisk() + player.getName() + "&e."));
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlace(BlockPlaceEvent e) {
        Faction broken = Factions.getInstance().getFactionHandler().getByLocation(e.getBlockPlaced().getLocation());

        if (e.getPlayer().getGameMode().equals(GameMode.CREATIVE)) return;

        if (broken instanceof PlayerFaction) {
            PlayerFaction pf = (PlayerFaction) broken;

            if (pf.getPlayers().contains(e.getPlayer().getUniqueId())) return;

            e.setCancelled(true);
            e.getPlayer().sendMessage(CC.t("&eYou cannot do this in the territory of &c" + broken.getName() + "&e."));
        }
    }

    @EventHandler
    public void onSpawnerPlace(BlockPlaceEvent e) {
        if (e.getItemInHand() == null) return;

        Faction faction = Factions.getInstance().getFactionHandler().getByLocation(e.getBlockPlaced().getLocation());
        Spawner spawner = Factions.getInstance().getSpawnerHandler().getByItem(e.getItemInHand());

        if (spawner == null) return;
        if (!(faction instanceof PlayerFaction)) return;

        PlayerFaction pf = (PlayerFaction) faction;

        if (!pf.getPlayers().contains(e.getPlayer().getUniqueId())) return;
        if (!pf.getClaims().contains(e.getBlockPlaced().getLocation().getChunk())) return;

        pf.setSpawnerValue(pf.getSpawnerValue() + spawner.getValue());
        pf.save();
        e.getPlayer().sendMessage(CC.t("&eUpdated Spawner Value: &c" + pf.getSpawnerValue()));
    }

    @EventHandler
    public void onSpawnerBreak(BlockBreakEvent e) {
        Faction faction = Factions.getInstance().getFactionHandler().getByLocation(e.getBlock().getLocation());

        if (!(faction instanceof PlayerFaction)) return;
        if (!(e.getBlock().getState() instanceof CreatureSpawner)) return;

        PlayerFaction pf = (PlayerFaction) faction;

        if (!pf.getPlayers().contains(e.getPlayer().getUniqueId())) return;
        if (!pf.getClaims().contains(e.getBlock().getLocation().getChunk())) return;

        CreatureSpawner spawner = (CreatureSpawner) e.getBlock().getState();
        Spawner sp = Factions.getInstance().getSpawnerHandler().getByEntity(spawner.getSpawnedType());
        pf.setSpawnerValue(pf.getSpawnerValue() - sp.getValue());
    }
}