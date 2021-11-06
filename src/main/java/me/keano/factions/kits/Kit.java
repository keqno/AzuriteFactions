package me.keano.factions.kits;

import lombok.Getter;
import lombok.Setter;
import me.keano.factions.Factions;
import me.keano.factions.utils.*;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Kit implements Listener {

    private String name;
    private String displayName;
    private List<String> items;
    private List<String> effects;
    private ItemStack chestItem;
    private ItemStack slimeBallItem;

    public Kit(String name, String displayName) {
        this.name = name;
        this.displayName = CC.t(displayName);
        this.items = new ArrayList<>();
        this.effects = new ArrayList<>();
        this.chestItem = new ItemBuilder(Material.CHEST, 1).setName(this.displayName).setLore("&c&oPlace in the wild!").toItemStack();
        this.slimeBallItem = new ItemBuilder(Material.SLIME_BALL, 1).setName(this.displayName).addUnsafeEnchantment(Enchantment.DURABILITY, 10).toItemStack();
    }

    public void save() {
        ConfigYML kitsYML = Factions.getInstance().getConfigHandler().getKitsYML();
        kitsYML.set("KITS." + name.toUpperCase() + ".NAME", name);
        kitsYML.set("KITS." + name.toUpperCase() + ".DISPLAY_NAME", displayName);
        kitsYML.set("KITS." + name.toUpperCase() + ".ITEMS", items);
        kitsYML.set("KITS." + name.toUpperCase() + ".EFFECTS", effects);
        kitsYML.save();
        CC.printMessage("- &eSaved " + name + " kit.", true);
    }

    public void reload() {
        ConfigYML kitsYML = Factions.getInstance().getConfigHandler().getKitsYML();
        kitsYML.reload();
        name = kitsYML.getString("KITS." + name.toUpperCase() + ".NAME");
        displayName = kitsYML.getString("KITS." + name.toUpperCase() + ".DISPLAY_NAME");
        items = kitsYML.getStringList("KITS." + name.toUpperCase() + ".ITEMS");
        CC.printMessage("- &eReloaded " + name + " kit.", true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getItemInHand() == null) return;

        if (!e.getItemInHand().isSimilar(chestItem)) return;

        if (!(e.getBlockPlaced().getState() instanceof Chest)) return;

        Chest chest = (Chest) e.getBlockPlaced().getState();
        for (String itemStack : items)
            chest.getInventory().addItem(Serializer.destringifyItem(itemStack));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (player.getItemInHand() == null) return;
        if (!player.getItemInHand().isSimilar(slimeBallItem)) return;

        for (String string : effects)
            player.addPotionEffect(Serializer.destringifyPotion(string));

        Utilities.takeItemInHand(player, 1);
    }
}