package me.keano.factions.kits.command;

import me.keano.factions.Factions;
import me.keano.factions.kits.Kit;
import me.keano.factions.kits.KitHandler;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.Serializer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;

public class KitCommand extends BukkitCommand {

    public KitCommand() {
        super("kit");
        this.setPermission("azurite.kits");
        this.setAliases(Collections.singletonList("kits"));
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] args) {
        KitHandler kitHandler = Factions.getInstance().getKitHandler();

        if (!(sender instanceof Player)) {
            sender.sendMessage(CC.t("&cYou are not a player."));
            return true;
        }

        if (!sender.hasPermission("azurite.kits")) {
            sender.sendMessage(CC.t("&cInsufficient Perms."));
            return true;
        }
        
        Player player = (Player) sender;

        if (args.length == 0) {
            printUsage(sender);
            return true;
        }

        switch (args[0].toUpperCase()) {
            case "APPLY":
                if (args.length == 1) {
                    sender.sendMessage(CC.t("&cUsage: /kits apply <kit>"));
                    return true;
                }

                Kit apply = kitHandler.getKit(args[1]);

                if (apply == null) {
                    sender.sendMessage(CC.t("&cUnknown Kit"));
                    return true;
                }

                kitHandler.giveKit(player, apply.getName());
                sender.sendMessage(CC.t("&aYou gained a kit!"));
                return true;

            case "SETITEMS":
                if (args.length == 1) {
                    sender.sendMessage(CC.t("&cUsage: /kits setitems <kit>"));
                    return true;
                }

                Kit setting = kitHandler.getKit(args[1]);

                if (setting == null) {
                    sender.sendMessage(CC.t("&cUnknown Kit"));
                    return true;
                }

                setting.getItems().clear();
                for (ItemStack itemStack : player.getInventory().getContents()) {
                    if (itemStack == null) continue;
                    setting.getItems().add(Serializer.stringifyItem(itemStack));
                }
                setting.save();
                setting.reload();
                sender.sendMessage(CC.t("&aYou set the contents to your inventory."));
                return true;

            case "CREATE":
                if (args.length == 1 || args.length == 2) {
                    sender.sendMessage(CC.t("&cUsage: /kits create <kit> <displayName>"));
                    return true;
                }

                if (kitHandler.getKit(args[1]) != null) {
                    sender.sendMessage(CC.t("&cThat kit already exists!"));
                    return true;
                }

                kitHandler.createKit(args[1], args[2].replaceAll("_", " "));
                sender.sendMessage(CC.t("&aYou created a kit!"));
                return true;

            case "LIST":
                sender.sendMessage(CC.STRAIGHT_LINE);
                sender.sendMessage(CC.t("&9Kits List"));
                for (Kit kit : kitHandler.getKitCache().values())
                    sender.sendMessage(CC.t(kit.getName() + ","));
                sender.sendMessage(CC.STRAIGHT_LINE);
                return true;

            case "SLIMEBALL":
                if (args.length == 1) {
                    sender.sendMessage(CC.t("&cUsage: /kits slimeball <kit>"));
                    return true;
                }

                Kit slimeball = kitHandler.getKit(args[1]);

                if (slimeball == null) {
                    sender.sendMessage(CC.t("&cUnknown Kit"));
                    return true;
                }

                player.getInventory().addItem(slimeball.getSlimeBallItem());
                player.updateInventory();
        }

        printUsage(sender);
        return true;
    }

    public void printUsage(CommandSender sender) {
        sender.sendMessage(CC.STRAIGHT_LINE);
        sender.sendMessage(CC.t("&9&lKits Help"));
        sender.sendMessage(CC.t("&7/kit apply <kit>"));
        sender.sendMessage(CC.t("&7/kit setitems <kit>"));
        sender.sendMessage(CC.t("&7/kit create <kit> <displayName>"));
        sender.sendMessage(CC.t("&7/kit slimeball <kit>"));
        sender.sendMessage(CC.t("&7/kit list"));
        sender.sendMessage(CC.STRAIGHT_LINE);
    }
}