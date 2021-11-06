package me.keano.factions.scoreboard;

import me.keano.factions.Factions;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.timers.TimerHandler;
import me.keano.factions.users.User;
import me.keano.factions.users.settings.ScoreboardType;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.Utilities;
import me.keano.factions.utils.scoreboard.AssembleAdapter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ScoreboardProvider implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        User user = Factions.getInstance().getUserHandler().getUser(player);

        if (user.getScoreboardType().equals(ScoreboardType.PVP)) {
            return CC.t("&4&lAzurite &7┃ &fPvP Board");
        }

        return CC.t("&9&lAzurite &7┃ &fFactions");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> lines = new ArrayList<>();
        User user = Factions.getInstance().getUserHandler().getUser(player);
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);

        lines.add(CC.STRAIGHT_LINE);

        switch (user.getScoreboardType()) {
            case DEFAULT:
                lines.add("&9&lYour Stats");
                lines.add("&6* &eBalance&7: &f" + user.getBalance());
                lines.add("&6* &eLevel&7: &f" + user.getLevel());
                lines.add("&6* &eCE-EXP&7: &f" + user.getCeexp());
                lines.add("");
                lines.add("&9&lYour Faction");

                if (pf != null) {
                    PlayerFaction focus = (PlayerFaction) Factions.getInstance().getFactionHandler().getFacFromUUID(pf.getFocusedFaction());
                    lines.add("&6* &eOnline&7: &f" + pf.getOnlinePlayers().size() + "/" + pf.getPlayers().size());
                    lines.add("&6* &eRally&7: &f" + (pf.getRally() != null ? Utilities.getPretty(pf.getRally(), false) : "None"));
                    lines.add("&6* &eFocus&7: &f" + (focus != null ? focus.getName() : "None"));
                } else {
                    lines.add("&6* &eNone");
                }
                break;

            case PVP:
                TimerHandler th = Factions.getInstance().getTimerHandler();
                ItemStack helmet = player.getInventory().getHelmet();
                ItemStack chest = player.getInventory().getChestplate();
                ItemStack leggings = player.getInventory().getLeggings();
                ItemStack boots = player.getInventory().getBoots();

                lines.add("&4&lTimers");
                lines.add("&c* " + th.getCombatTimer().getDisplayName() + "&7: &f" + th.getCombatTimer().getRemainingString(player.getUniqueId()));
                lines.add("&c* " + th.getEnderpearlTimer().getDisplayName() + "&7: &f" + th.getEnderpearlTimer().getRemainingString(player.getUniqueId()));
                lines.add("&c* " + th.getGappleTimer().getDisplayName() + "&7: &f" + th.getGappleTimer().getRemainingString(player.getUniqueId()));
                lines.add("");
                lines.add("&4&lArmor");
                lines.add("&c* &6Helmet&7: &f" + (helmet != null ? getDurability(helmet) : "None"));
                lines.add("&c* &6Chestplate&7: &f" + (chest != null ? getDurability(chest) : "None"));
                lines.add("&c* &6Leggings&7: &f" + (leggings != null ? getDurability(leggings) : "None"));
                lines.add("&c* &6Boots&7: &f" + (boots != null ? getDurability(boots) : "None"));
        }

        lines.add(CC.STRAIGHT_LINE);
        return lines;
    }

    public int getDurability(ItemStack itemStack) {
        return itemStack.getType().getMaxDurability() + 1 - itemStack.getDurability();
    }
}