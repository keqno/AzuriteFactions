package me.keano.factions.relics;

import lombok.Getter;
import lombok.Setter;
import me.keano.factions.Factions;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ConfigYML;
import me.keano.factions.utils.ItemBuilder;
import me.keano.factions.utils.Serializer;
import me.keano.factions.utils.particles.ParticleEffect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Getter
@Setter
public class Relic implements Listener {

    private String name;
    private String displayName;
    private ItemStack item;
    private List<String> rewards;

    public Relic(String name, String displayName) {
        this.name = name;
        this.displayName = CC.t(displayName);
        this.item = new ItemBuilder(Material.WOOL, 1).setName(displayName).toItemStack();
        this.rewards = new ArrayList<>();
    }

    public void save() {
        ConfigYML relicsYML = Factions.getInstance().getConfigHandler().getRelicsYML();
        relicsYML.set("RELICS." + name.toUpperCase() + ".NAME", name);
        relicsYML.set("RELICS." + name.toUpperCase() + ".DISPLAY_NAME", displayName);
        relicsYML.set("RELICS." + name.toUpperCase() + ".REWARDS", rewards);
        relicsYML.save();
        CC.printMessage("&9[Azurite] &bSaved " + name + " relic successfully.", true);
    }

    public void reload() {
        ConfigYML relicsYML = Factions.getInstance().getConfigHandler().getRelicsYML();
        relicsYML.reload();
        name = relicsYML.getString("RELICS." + name.toUpperCase() + ".NAME");
        displayName = relicsYML.getString("RELICS." + name.toUpperCase() + ".DISPLAY_NAME");
        rewards = relicsYML.getStringList("RELICS." + name.toUpperCase() + ".REWARDS");
        CC.printMessage("&9[Azurite] &bReloaded " + name + " relic successfully.", true);
    }

    public ItemStack getRandomItem() {
        int random = new Random().nextInt(rewards.size());
        return Serializer.destringifyItem(rewards.get(random));
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getItemInHand() == null) return;
        if (!e.getItemInHand().isSimilar(this.item)) return;

        Player player = e.getPlayer();

        player.playSound(player.getLocation(), Sound.FUSE, 1, 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                ParticleEffect.EXPLOSION_NORMAL.display(0, 0, 0, 1, 1, player.getLocation(), 100);
                player.playSound(player.getLocation(), Sound.EXPLODE, 1, 1);
                player.getWorld().dropItemNaturally(e.getBlockPlaced().getLocation(), getRandomItem());
            }
        }.runTaskLater(Factions.getInstance(), 20 * 5);
    }
}