package me.keano.factions.utils.tablist.thread;

import me.keano.factions.utils.tablist.TabHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@SuppressWarnings("ALL")
public class TabThread extends Thread{

    private final TabHandler tabHandler;

    public TabThread(TabHandler tabHandler) {
        super("Factions - TabThread");
        this.tabHandler = tabHandler;
        this.setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {

                for (Player player : Bukkit.getOnlinePlayers())
                    tabHandler.sendUpdate(player);

            } catch (Exception e) {
                // Empty Catch
            }

            try {
                Thread.sleep(tabHandler.getTicks());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}