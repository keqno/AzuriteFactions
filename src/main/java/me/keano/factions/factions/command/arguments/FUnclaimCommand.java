package me.keano.factions.factions.command.arguments;

import me.keano.factions.Factions;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.utils.FactionLang;
import me.keano.factions.spawners.Spawner;
import me.keano.factions.utils.CC;
import net.evilblock.cubed.command.Command;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;

public class FUnclaimCommand {

    @Command(
            names = {
                    "f unclaim", "t unclaim", "faction unclaim", "team unclaim",
            }
    )
    public static void execute(Player player) {
        PlayerFaction pf = Factions.getInstance().getFactionHandler().getPlayerFac(player);
        Faction loc = Factions.getInstance().getFactionHandler().getByLocation(player.getLocation());

        if (pf == null) {
            player.sendMessage(FactionLang.NOT_IN_FACTION);
            return;
        }

        if (!pf.isCaptainOrHigher(pf.getMember(player))) {
            player.sendMessage(FactionLang.ONLY_CAPTAINS);
            return;
        }

        if (!(loc instanceof PlayerFaction)) {
            player.sendMessage(CC.t("&cThis claim is not yours!"));
            return;
        }

        PlayerFaction locPf = (PlayerFaction) loc;

        if (!locPf.getPlayers().contains(player.getUniqueId())) {
            player.sendMessage(CC.t("&cThis claim is not yours!"));
            return;
        }

        pf.getClaims().remove(player.getLocation().getChunk());
        pf.save();

        for (BlockState state : player.getLocation().getChunk().getTileEntities()) {
            if (!(state instanceof CreatureSpawner)) continue;

            CreatureSpawner cs = (CreatureSpawner) state;

            Spawner spawner = Factions.getInstance().getSpawnerHandler().getByEntity(cs.getSpawnedType());
            pf.setSpawnerValue(pf.getSpawnerValue() - spawner.getValue());
        }

        player.sendMessage(CC.t("&aYou have unclaimed the chunk you are in."));
    }
}