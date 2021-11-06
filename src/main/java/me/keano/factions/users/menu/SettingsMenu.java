package me.keano.factions.users.menu;

import me.keano.factions.Factions;
import me.keano.factions.users.User;
import me.keano.factions.users.settings.ScoreboardType;
import me.keano.factions.utils.ItemBuilder;
import me.keano.factions.utils.menu.Menu;
import me.keano.factions.utils.menu.button.Button;
import me.keano.factions.utils.menu.type.MenuType;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;
import java.util.Set;

public class SettingsMenu extends Menu {

    public SettingsMenu() {
        super(
                "&9&lYour Settings",
                27 / 9,
                MenuType.DEFAULT
        );
    }

    @Override
    public Set<Button> getButtons(Player player) {
        Set<Button> buttons = new HashSet<>();
        User user = Factions.getInstance().getUserHandler().getUser(player);

        buttons.add(new Button(3) {
            @Override
            public void onClick(InventoryClickEvent event) {

                event.setCancelled(true);
                event.getWhoClicked().closeInventory();
                openMenu((Player) event.getWhoClicked());

                switch (user.getScoreboardType()) {
                    case PVP:
                        ItemBuilder ibPvP = new ItemBuilder(event.getCurrentItem());
                        user.setScoreboardType(ScoreboardType.DEFAULT);
                        user.save();
                        ibPvP.setLore(" &eDefault", " &6▶ &ePvP");
                        event.setCurrentItem(ibPvP.toItemStack());
                        break;

                    case DEFAULT:
                        ItemBuilder ibDefault = new ItemBuilder(event.getCurrentItem());
                        user.setScoreboardType(ScoreboardType.PVP);
                        user.save();
                        ibDefault.setLore(" &6▶ &eDefault", " &ePvP");
                        event.setCurrentItem(ibDefault.toItemStack());
                }
            }

            @Override
            public ItemStack getButtonItem() {
                ItemBuilder ib = new ItemBuilder(Material.GLASS, 1);

                ib.setName("&9&lScoreboard Type");

                switch (user.getScoreboardType()) {
                    case DEFAULT:
                        ib.setLore(" &6▶ &eDefault", " &ePvP");
                        break;

                    case PVP:
                        ib.setLore(" &eDefault", " &6▶ &ePvP");
                        break;
                }

                return ib.toItemStack();
            }
        });

        return buttons;
    }
}