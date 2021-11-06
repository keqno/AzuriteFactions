package me.keano.factions.spawners;

import lombok.Getter;
import lombok.Setter;
import me.keano.factions.utils.CC;
import me.keano.factions.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
public class Spawner {

    protected String name;
    protected String displayName;
    protected EntityType type;
    protected int value;
    protected ItemStack item;

    public Spawner(String name, String displayName, EntityType type, int value) {
        this.name = name;
        this.displayName = CC.t(displayName);
        this.type = type;
        this.value = value;
        this.item = new ItemBuilder(Material.MOB_SPAWNER, 1)
                .setName(displayName)
                .addLoreLine("&eSpawner Type: &7" + type.getDisplayName())
                .addLoreLine("&cSpawner Value: &7" + value)
                .toItemStack();
    }
}