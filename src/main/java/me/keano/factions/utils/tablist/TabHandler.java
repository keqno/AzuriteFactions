package me.keano.factions.utils.tablist;

import lombok.Getter;
import lombok.SneakyThrows;
import me.keano.factions.utils.tablist.entry.TabElement;
import me.keano.factions.utils.tablist.entry.TabElementHandler;
import me.keano.factions.utils.tablist.thread.TabThread;
import org.bukkit.entity.Player;

@Getter
public class TabHandler {

    private final TabAdapter adapter;
    private final TabElementHandler handler;

    private final long ticks;

    /**
     * Constructor to make a new tab handler
     *
     * @param handler the handler to get the elements from
     * @param ticks   the amount it should update
     */
    @SneakyThrows
    public TabHandler(TabElementHandler handler, long ticks) {
        this.adapter = new v1_8_R3TabAdapter();
        this.handler = handler;
        this.ticks = ticks;
        new TabThread(this).start();
    }

    /**
     * Update the tablist for a player
     *
     * @param player the player to update it for
     */
    public void sendUpdate(Player player) {
        final TabElement tabElement = this.handler.getElement(player);

        this.adapter.setupProfiles(player)
                .showRealPlayers(player).addFakePlayers(player)
                .hideRealPlayers(player).handleElement(player, tabElement)
                .sendHeaderFooter(player, tabElement.getHeader(), tabElement.getFooter());
    }
}