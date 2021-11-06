package me.keano.factions.commands.type;

import me.keano.factions.Factions;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.users.User;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ItemBuilder;
import net.evilblock.cubed.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class FixCommand {

    private static final HashMap<UUID, ItemStack> fixCache = new HashMap<>();

    @Command(
            names = {"fix", "repair"},
            description = "Fix the item in your hand."
    )
    public static void execute(Player player) {
        ItemStack item = player.getItemInHand();
        User user = Factions.getInstance().getUserHandler().getUser(player);
        int amount = (EnchantUtils.getFixes(item) + 1) * 10000;

        if (fixCache.containsKey(player.getUniqueId())) {
            if (item == null || !item.isSimilar(fixCache.get(player.getUniqueId()))) {
                fixCache.remove(player.getUniqueId());
                player.sendMessage(CC.t("&cYou are not holding the same item."));
                return;
            }

            ItemBuilder ib = new ItemBuilder(item);
            ib.addLoreLine("Fix-Test");
            ib.setLore(EnchantUtils.rearrangeLore(ib.toItemStack().getItemMeta().getLore(), EnchantUtils.getFixes(item) + 1));
            fixCache.remove(player.getUniqueId());
            user.takeBalance(amount);
            player.sendMessage(CC.t("&aRepaired your item!"));
            return;
        }

        if (item == null || !EnchantUtils.isFixable(item)) {
            player.sendMessage(CC.t("&cYou cannot fix this item."));
            return;
        }

        if (user.getBalance() < amount) {
            player.sendMessage(CC.t("&eYou need &c$" + amount + " &eto fix this item."));
            return;
        }

        fixCache.put(player.getUniqueId(), item);
        player.sendMessage(CC.t("&eThis item will cost &c$" + amount + " &eto be repaired."));
        player.sendMessage(CC.t("&9&oRun this command again to continue!"));
    }
}