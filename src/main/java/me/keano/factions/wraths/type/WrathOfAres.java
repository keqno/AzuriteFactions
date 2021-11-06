package me.keano.factions.wraths.type;

import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ItemBuilder;
import me.keano.factions.wraths.Wrath;
import me.keano.factions.wraths.utils.WrathUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Random;

public class WrathOfAres extends Wrath {

    public WrathOfAres() {
        super(
                "Ares",
                "&c&lWrath Of Ares",
                "Sword",
                Arrays.asList(
                        "&7Type: &aSword",
                        "",
                        "&7Has a chance to do double damage in combat.",
                        "",
                        "&c&oRequires 40 souls to activate.",
                        "&c&oDrag and drop on an item to combine."
                ),
                2,
                40
        );
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!EnchantUtils.isPlayer(e.getDamager(), e.getEntity())) return;

        Player damager = (Player) e.getDamager();
        ItemStack item = damager.getItemInHand();

        if (item == null) return;
        if (!containsWrath(item)) return;
        if (WrathUtils.getSouls(item) < getSoulsRequired()) return;

        int souls = WrathUtils.getSouls(item);
        int random = new Random().nextInt(300 + 1);

        if (random <= chance) {
            e.setDamage(e.getDamage() * 2);

            ItemBuilder ib = new ItemBuilder(item);
            ib.toItemStack().getItemMeta().getLore().removeIf(string -> string.contains("Wrath Of Ares"));
            ib.addLoreLine("&c&l&o- Wrath OF Ares &7" + (souls - soulsRequired));
            ib.setLore(EnchantUtils.rearrangeLore(ib.toItemStack().getItemMeta().getLore()));

            damager.sendMessage(CC.t("&e&lYOUR &c&lWRATH OF ARES &e&lHAS ACTIVATED!"));
        }
    }
}