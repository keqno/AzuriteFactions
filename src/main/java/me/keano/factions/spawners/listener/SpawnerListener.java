package me.keano.factions.spawners.listener;

import me.keano.factions.Factions;
import me.keano.factions.spawners.Spawner;
import me.keano.factions.utils.CC;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class SpawnerListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.isCancelled()) return;

        if (e.getItemInHand() == null) return;

        Spawner spawner = Factions.getInstance().getSpawnerHandler().getByItem(e.getItemInHand());

        if (spawner == null) return;

        CreatureSpawner creatureSpawner = (CreatureSpawner) e.getBlockPlaced().getState();
        creatureSpawner.setSpawnedType(spawner.getType());
        creatureSpawner.setDelay(20 * 5);
        creatureSpawner.update();
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();
        ItemStack item = player.getItemInHand();

        if (e.isCancelled()) return;

        if (!(e.getBlock().getState() instanceof CreatureSpawner)) return;

        if (item == null) {
            player.sendMessage(CC.t("&cYou need a silk touch pickaxe to mine spawners."));
            e.getBlock().getDrops().clear();
            return;
        }

        if (player.getItemInHand().getItemMeta() != null && !item.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
            player.sendMessage(CC.t("&cYou need a silk touch pickaxe to mine spawners."));
            e.getBlock().getDrops().clear();
            return;
        }

        CreatureSpawner cs = (CreatureSpawner) e.getBlock().getState();
        Spawner spawner = Factions.getInstance().getSpawnerHandler().getByEntity(cs.getSpawnedType());
        e.getBlock().getDrops().clear();
        player.getWorld().dropItemNaturally(e.getBlock().getLocation(), spawner.getItem());
    }
}