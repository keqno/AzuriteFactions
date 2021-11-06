package me.keano.factions.spawners;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.spawners.listener.SpawnerListener;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class SpawnerHandler {

    private final Factions instance;

    @Getter
    private final HashSet<Spawner> spawners;

    public SpawnerHandler(Factions instance) {
        this.instance = instance;
        this.spawners = new HashSet<>();
        this.register();
    }

    public Spawner getByEntity(EntityType type) {
        for (Spawner spawner : spawners) {
            if (spawner.getType().equals(type))
                return spawner;
        }

        return null;
    }

    public Spawner getByItem(ItemStack itemStack) {
        for (Spawner spawner : spawners) {
            if (spawner.getItem().isSimilar(itemStack))
                return spawner;
        }

        return null;
    }

    public Spawner getByName(String name) {
        for (Spawner spawner : spawners) {
            if (spawner.getName().equalsIgnoreCase(name))
                return spawner;
        }

        return null;
    }

    public void register() {
        spawners.add(new Spawner("Rabbit", "&d&lRabbit Spawner", EntityType.RABBIT, 3));
        spawners.add(new Spawner("Zombie", "&d&lZombie Spawner", EntityType.ZOMBIE, 3));
        spawners.add(new Spawner("Spider", "&d&lSpider Spawner", EntityType.SPIDER, 3));
        spawners.add(new Spawner("MushroomCow", "&d&lMooshroom Spawner", EntityType.MUSHROOM_COW, 3));
        spawners.add(new Spawner("Cow", "&d&lCow Spawner", EntityType.COW, 3));
        spawners.add(new Spawner("SnowMan", "&d&lSnow Man Spawner", EntityType.SNOWMAN, 3));

        instance.getServer().getPluginManager().registerEvents(new SpawnerListener(), instance);
    }
}