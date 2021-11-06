package me.keano.factions.factions.type;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.ReplaceOptions;
import lombok.Getter;
import lombok.Setter;
import me.keano.factions.Factions;
import me.keano.factions.factions.Faction;
import me.keano.factions.factions.FactionType;
import me.keano.factions.factions.player.Member;
import me.keano.factions.factions.player.Role;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.users.User;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.Serializer;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
public class PlayerFaction extends Faction {

    private UUID leader;
    private int strength;
    private int balance;
    private int level;
    private int spawnerValue;
    private Location hq;
    private Location rally;
    private UUID focusedFaction;
    private List<UUID> allies;
    private List<UUID> players;
    private List<Member> members;
    private List<Chunk> claims;

    public PlayerFaction(String name, UUID leader) {
        super(name, FactionType.PLAYER);
        this.leader = leader;
        this.strength = 0;
        this.balance = 0;
        this.level = 1;
        this.spawnerValue = 0;
        this.rally = null;
        this.hq = null;
        this.focusedFaction = null;
        this.allies = new ArrayList<>();
        this.players = new ArrayList<>();
        this.members = new ArrayList<>();
        this.claims = new ArrayList<>();
        this.players.add(leader);
        this.members.add(new Member(leader, Role.LEADER));
    }

    public List<Player> getOnlinePlayers() {
        List<Player> onlinePlayers = new ArrayList<>();

        for (UUID uuid : players) {
            if (Bukkit.getOfflinePlayer(uuid).isOnline())
                onlinePlayers.add(Bukkit.getPlayer(uuid));
        }

        return onlinePlayers;
    }

    public Member getMember(Player player) {
        for (Member member : members) {
            if (member.getUuid().equals(player.getUniqueId()))
                return member;
        }
        return null;
    }

    public void takeBalance(int amount) {
        if (balance - amount < 0) {
            balance = 0;
            return;
        }

        balance = balance - amount;
    }

    public int getMaxMembers() {
        if (level == 2)
            return 8;

        if (level == 3)
            return 10;

        if (level == 4)
            return 12;

        if (level == 5)
            return 15;

        return 5;
    }

    @SuppressWarnings("ALL")
    public boolean isOfficerOrHigher(Member member) {
        return member.getRole().equals(Role.OFFICER) || member.getRole().equals(Role.CAPTAIN) || member.getRole().equals(Role.LEADER);
    }

    @SuppressWarnings("ALL")
    public boolean isCaptainOrHigher(Member member) {
        return member.getRole().equals(Role.CAPTAIN) || member.getRole().equals(Role.LEADER);
    }

    public void broadcastToMembers(String... s) {
        for (Player player : getOnlinePlayers()) {
            for (String string : s)
                player.sendMessage(CC.t(string));
        }
    }

    public boolean canClaim(Player player) {
        Faction chunk = Factions.getInstance().getFactionHandler().getByLocation(player.getLocation());

        if (chunk instanceof SafezoneFaction || chunk instanceof WarzoneFaction || chunk instanceof PlayerFaction) {
            player.sendMessage(CC.t("&cYou cannot claim at this location."));
            return false;
        }

        if (strength < 1500) {
            player.sendMessage(CC.t("&eYour faction needs &c1500 &eor more strength to claim."));
            return false;
        }

        if (level < claims.size()) {
            player.sendMessage(CC.t("&cYour faction level is not high enough!"));
            return false;
        }

        return true;
    }

    @Override
    public List<String> getFactionInfo() {
        List<String> list = new ArrayList<>();

        // Handle the colors of being online and sorting them.
        List<String> captains = members.stream()
                .filter(member -> member.getRole().equals(Role.CAPTAIN) && !member.getRole().equals(Role.LEADER))
                .map(member -> {

                    OfflinePlayer p = Bukkit.getOfflinePlayer(member.getUuid());
                    User user = Factions.getInstance().getUserHandler().getUser(member.getUuid());

                    return (p.isOnline() ? "&a" : "&7") + p.getName() + "&e[&a" + user.getKills() + "&e]";

                }).collect(Collectors.toList());

        List<String> officers = members.stream()
                .filter(member -> member.getRole().equals(Role.OFFICER) && !member.getRole().equals(Role.LEADER))
                .map(member -> {

                    OfflinePlayer p = Bukkit.getOfflinePlayer(member.getUuid());
                    User user = Factions.getInstance().getUserHandler().getUser(member.getUuid());

                    return (p.isOnline() ? "&a" : "&7") + p.getName() + "&e[&a" + user.getKills() + "&e]";

                }).collect(Collectors.toList());

        List<String> normal = members.stream()
                .filter(member -> member.getRole().equals(Role.MEMBER) && !member.getRole().equals(Role.LEADER))
                .map(member -> {

                    OfflinePlayer p = Bukkit.getOfflinePlayer(member.getUuid());
                    User user = Factions.getInstance().getUserHandler().getUser(member.getUuid());

                    return (p.isOnline() ? "&a" : "&7") + p.getName() + "&e[&a" + user.getKills() + "&e]";

                }).collect(Collectors.toList());

        List<String> allied = allies.stream().map(uuid -> {

            Faction ally = Factions.getInstance().getFactionHandler().getFacFromUUID(uuid);

            return ally.getName();

        }).collect(Collectors.toList());

        OfflinePlayer founder = Bukkit.getOfflinePlayer(leader);
        User founderUser = Factions.getInstance().getUserHandler().getUser(leader);

        // Send all the info
        list.add(CC.STRAIGHT_LINE);

        list.add("&9" + name + " &7[" + this.getOnlinePlayers().size() + "/" + this.getPlayers().size() + "] &3- &eLVL: &f" + level);
        list.add("&eLeader: &c" + (founder.isOnline() ? "&a" : "&7") + founder.getName() + "&e[&a" + founderUser.getKills() + "&e]");

        if (!captains.isEmpty())
            list.add("&eCaptains: &c" + StringUtils.join(captains, ", "));

        if (!officers.isEmpty())
            list.add("&eOfficers: &c" + StringUtils.join(officers, ", "));

        if (!normal.isEmpty())
            list.add("&eMembers: &c" + StringUtils.join(normal, ", "));

        if (!allied.isEmpty())
            list.add("&eAllies: &c" + StringUtils.join(allied, ", "));

        list.add("&eSpawner Value: &c" + spawnerValue);
        list.add("&eStrength: &c" + strength);
        list.add("&eBalance: &9$" + balance);
        list.add(CC.STRAIGHT_LINE);
        return CC.t(list);
    }

    @Override
    public String getDisplayName(Player player) {
        return CC.t(this.players.contains(player.getUniqueId()) ? "&a" + name : "&c" + name);
    }

    @Override
    public void save() {
        Document document = new Document("_id", uuid.toString());
        document.put("name", name);
        document.put("leader", leader.toString());
        document.put("factionType", factionType.toString());
        document.put("strength", strength);
        document.put("balance", balance);
        document.put("spawnerValue", spawnerValue);
        document.put("level", level);
        document.put("players", players.stream().map(UUID::toString).collect(Collectors.toList()));
        document.put("members", members.stream().map(Serializer::stringifyMember).collect(Collectors.toList()));
        document.put("claims", claims.stream().map(Serializer::stringifyChunk).collect(Collectors.toList()));
        document.put("allies", allies.stream().map(UUID::toString).collect(Collectors.toList()));

        if (focusedFaction != null) document.put("focusedFac", focusedFaction.toString());
        if (rally != null) document.put("rally", Serializer.stringifyLocation(rally));
        if (hq != null) document.put("hq", Serializer.stringifyLocation(hq));

        MongoHandler.factions.replaceOne(
                Filters.eq("_id", uuid.toString()), document, new ReplaceOptions().upsert(true));
    }
}