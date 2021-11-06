package me.keano.factions.factions;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.factions.listener.FactionListener;
import me.keano.factions.factions.listener.PlayerFactionListener;
import me.keano.factions.factions.listener.SafezoneListener;
import me.keano.factions.factions.type.PlayerFaction;
import me.keano.factions.factions.type.SafezoneFaction;
import me.keano.factions.factions.type.WarzoneFaction;
import me.keano.factions.factions.type.WildernessFaction;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.Serializer;
import org.bson.Document;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class FactionHandler {

    private final Factions instance;

    @Getter
    protected WildernessFaction wildernessFaction;
    @Getter
    protected WarzoneFaction warzoneFaction;
    @Getter
    protected Map<UUID, Faction> factions;

    public FactionHandler(Factions instance) {
        this.instance = instance;
        this.factions = new ConcurrentHashMap<>();
        this.wildernessFaction = new WildernessFaction();
        this.warzoneFaction = new WarzoneFaction();
        this.load();
        this.registerListener();
    }

    private void load() {
        int pfs = 0;
        int sfs = 0;

        CC.printMessage("&4===&c====================&c===");
        CC.printMessage("- &eLoading factions...");

        for (Document document : MongoHandler.factions.find()) {
            FactionType type = FactionType.valueOf(document.getString("factionType"));

            switch (type) {
                case PLAYER:
                    PlayerFaction pf = new PlayerFaction(document.getString("name"),
                            UUID.fromString(document.getString("leader")));

                    pf.setUuid(UUID.fromString(document.getString("_id")));
                    pf.setStrength(document.getInteger("strength"));
                    pf.setBalance(document.getInteger("balance"));
                    pf.setLevel(document.getInteger("level"));
                    pf.setSpawnerValue(document.getInteger("spawnerValue"));

                    if (document.getString("focusedFac") != null)
                        pf.setFocusedFaction(UUID.fromString(document.getString("focusedFac")));

                    if (document.getString("rally") != null)
                        pf.setRally(Serializer.destringifyLocation(document.getString("rally")));

                    if (document.getString("hq") != null)
                        pf.setHq(Serializer.destringifyLocation(document.getString("hq")));

                    pf.setAllies(document.getList("allies", String.class)
                            .stream().map(UUID::fromString).collect(Collectors.toList()));

                    pf.setMembers(document.getList("members", String.class)
                            .stream().map(Serializer::destringifyMember).collect(Collectors.toList()));

                    pf.setPlayers(document.getList("players", String.class)
                            .stream().map(UUID::fromString).collect(Collectors.toList()));

                    pf.setClaims(document.getList("claims", String.class)
                            .stream().map(Serializer::destringifyChunk).collect(Collectors.toList()));

                    pfs++;
                    factions.put(pf.getUuid(), pf);
                    break;

                case SAFEZONE:
                    SafezoneFaction sf = new SafezoneFaction(document.getString("name"));
                    sfs++;
                    factions.put(sf.getUuid(), sf);
                    break;
            }
        }

        CC.printMessage("- &ePlayer factions&7: " + pfs);
        CC.printMessage("- &eSafezone factions&7: " + sfs);
        CC.printMessage("- &eTotal loaded&7: " + (sfs + pfs));
        CC.printMessage("&4===&c====================&c===");
    }

    private void registerListener() {
        instance.getServer().getPluginManager().registerEvents(new FactionListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new PlayerFactionListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new SafezoneListener(), instance);
    }

    public Faction getFacFromUUID(UUID uuid) {
        for (Faction faction : factions.values()) {
            if (faction.getUuid().equals(uuid))
                return faction;
        }
        return null;
    }

    public PlayerFaction getPlayerFac(Player player) {
        for (Faction faction : factions.values()) {
            if (!(faction instanceof PlayerFaction)) continue;

            PlayerFaction pf = (PlayerFaction) faction;

            if (pf.getPlayers().contains(player.getUniqueId()))
                return pf;
        }
        return null;
    }

    public Faction getByLocation(Location location) {
        for (Faction faction : factions.values()) {
            if (!(faction instanceof PlayerFaction)) continue;

            PlayerFaction pf = (PlayerFaction) faction;

            if (pf.getClaims().contains(location.getChunk()))
                return faction;
        }

        int x = location.getBlockX();
        int z = location.getBlockZ();

        if (x < 300 && x > -300 && z < 300 && z > -300)
            return warzoneFaction;

        return wildernessFaction;
    }

    public Faction getFaction(String name) {
        for (Faction faction : factions.values()) {
            if (faction.getName().equalsIgnoreCase(name))
                return faction;
        }
        return null;
    }

    public PlayerFaction getPlayerFac(String name) {
        for (Faction faction : factions.values()) {

            if (!(faction instanceof PlayerFaction)) continue;

            if (faction.getName().equalsIgnoreCase(name))
                return (PlayerFaction) faction;
        }
        return null;
    }

    public void save() {
        for (Faction faction : factions.values())
            faction.save();
    }
}