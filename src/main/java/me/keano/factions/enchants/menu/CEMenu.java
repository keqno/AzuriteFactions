package me.keano.factions.enchants.menu;

import me.keano.factions.Factions;
import me.keano.factions.enchants.rarity.EnchantRarity;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.CEEXPUtils;
import me.keano.factions.utils.ItemBuilder;
import me.keano.factions.utils.menu.Menu;
import me.keano.factions.utils.menu.button.Button;
import me.keano.factions.utils.menu.type.FillType;
import me.keano.factions.utils.menu.type.MenuType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class CEMenu extends Menu {

    public CEMenu() {
        super(CC.t("&a&lCustom Enchants"), 27 / 9, MenuType.DEFAULT);
        this.setFillEnabled(true);
        this.setFillType(FillType.ALL);
        this.setFillItemStack(new ItemBuilder(Material.STAINED_GLASS_PANE, 1)
                .setName("&7")
                .data((short) 15)
                .toItemStack());
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();

        buttons.add(new Button(12) {
            @Override
            public void onClick(InventoryClickEvent event) {
                event.setCancelled(true);

                if (!CEEXPUtils.hasCeexp(player, 1000)) {
                    player.sendMessage(CC.t("&eYou need &c1000 &eceexp to purchase this."));
                    return;
                }

                player.getInventory().addItem(Factions.getInstance().getEnchantHandler()
                        .getRandomEnchant(EnchantRarity.COMMON));
                CEEXPUtils.takeCeexp(player, 1000);
            }

            @Override
            public ItemStack getButtonItem() {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).setName("&a&lCommon Enchant")
                        .data((short) 5)
                        .setLore("&a* &eThis enchant costs &a1000 CE-EXP&f")
                        .toItemStack();
            }
        });

        buttons.add(new Button(14) {
            @Override
            public void onClick(InventoryClickEvent event) {
                event.setCancelled(true);

                if (!CEEXPUtils.hasCeexp(player, 1500)) {
                    player.sendMessage(CC.t("&eYou need &c1500 &eceexp to purchase this."));
                    return;
                }

                player.getInventory().addItem(Factions.getInstance().getEnchantHandler()
                        .getRandomEnchant(EnchantRarity.RARE));
                CEEXPUtils.takeCeexp(player, 1500);
            }

            @Override
            public ItemStack getButtonItem() {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).setName("&9&lRare Enchant")
                        .data((short) 11)
                        .setLore("&9* &eThis enchant costs &91500 CE-EXP")
                        .toItemStack();
            }
        });

        buttons.add(new Button(16) {
            @Override
            public void onClick(InventoryClickEvent event) {
                event.setCancelled(true);

                if (!CEEXPUtils.hasCeexp(player, 2000)) {
                    player.sendMessage(CC.t("&eYou need &c2000 &eceexp to purchase this."));
                    return;
                }

                player.getInventory().addItem(Factions.getInstance().getEnchantHandler()
                        .getRandomEnchant(EnchantRarity.LEGENDARY));
                CEEXPUtils.takeCeexp(player, 2000);
            }

            @Override
            public ItemStack getButtonItem() {
                return new ItemBuilder(Material.STAINED_GLASS_PANE).setName("&6&lLegendary Enchant")
                        .data((short) 1)
                        .setLore("&6* &eThis enchant costs &62000 CE-EXP")
                        .toItemStack();
            }
        });

        return buttons;
    }
}