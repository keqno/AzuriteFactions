package me.keano.factions;

import lombok.Getter;
import me.keano.factions.configs.ConfigHandler;
import me.keano.factions.enchants.EnchantHandler;
import me.keano.factions.factions.FactionHandler;
import me.keano.factions.kits.KitHandler;
import me.keano.factions.listeners.ListenerHandler;
import me.keano.factions.mongo.MongoHandler;
import me.keano.factions.scoreboard.ScoreboardProvider;
import me.keano.factions.spawners.SpawnerHandler;
import me.keano.factions.tablist.TablistProvider;
import me.keano.factions.timers.TimerHandler;
import me.keano.factions.users.UserHandler;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.menu.MenuManager;
import me.keano.factions.utils.scoreboard.Assemble;
import me.keano.factions.utils.scoreboard.AssembleStyle;
import me.keano.factions.utils.tablist.TabHandler;
import me.keano.factions.warps.WarpHandler;
import me.keano.factions.wraths.WrathHandler;
import net.evilblock.cubed.command.CommandHandler;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Factions extends JavaPlugin {

    @Getter
    private static Factions instance;

    private ConfigHandler configHandler;
    private MongoHandler mongoHandler;
    private KitHandler kitHandler;
    private EnchantHandler enchantHandler;
    private ListenerHandler listenerHandler;
    private WrathHandler wrathHandler;
    private SpawnerHandler spawnerHandler;
    private TimerHandler timerHandler;
    private WarpHandler warpHandler;
    private UserHandler userHandler;
    private FactionHandler factionHandler;

    @Override
    public void onEnable() {
        instance = this;

        new MenuManager(this);
        new TabHandler(new TablistProvider(), 10L);

        this.configHandler = new ConfigHandler(this);
        this.mongoHandler = new MongoHandler(this);
        this.kitHandler = new KitHandler(this);
        this.timerHandler = new TimerHandler(this);
        this.enchantHandler = new EnchantHandler(this);
        this.listenerHandler = new ListenerHandler(this);
        this.wrathHandler = new WrathHandler(this);
        this.warpHandler = new WarpHandler(this);
        this.spawnerHandler = new SpawnerHandler(this);
        this.userHandler = new UserHandler(this);
        this.factionHandler = new FactionHandler(this);

        CommandHandler.INSTANCE.registerAll(this);

        Assemble assemble = new Assemble(this, new ScoreboardProvider());
        assemble.setTicks(2L);
        assemble.setAssembleStyle(AssembleStyle.MODERN);

        this.userHandler.setLoaded(true);

        CC.state("Enabled");
    }

    @Override
    public void onDisable() {
        this.userHandler.save();
        this.factionHandler.save();
        this.timerHandler.save();
        CC.state("Disabled");
    }
}