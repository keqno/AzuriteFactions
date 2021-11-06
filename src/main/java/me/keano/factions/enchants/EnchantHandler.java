package me.keano.factions.enchants;

import lombok.Getter;
import me.keano.factions.Factions;
import me.keano.factions.enchants.listener.EnchantListener;
import me.keano.factions.enchants.rarity.EnchantRarity;
import me.keano.factions.enchants.type.armor.*;
import me.keano.factions.enchants.type.sword.*;
import me.keano.factions.enchants.utils.EnchantUtils;
import me.keano.factions.utils.ItemBuilder;
import me.keano.factions.utils.PotionUtils;
import me.keano.factions.utils.event.ArmorListener;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class EnchantHandler {

    private final Factions instance;

    @Getter
    private final HashMap<String, Enchant> customEnchants;

    public EnchantHandler(Factions instance) {
        this.instance = instance;
        this.customEnchants = new HashMap<>();
        this.register();
        instance.getServer().getPluginManager().registerEvents(new ArmorListener(), instance);
        instance.getServer().getPluginManager().registerEvents(new PotionUtils(), instance);
        instance.getServer().getPluginManager().registerEvents(new EnchantListener(), instance);
    }

    public void register() {
        // SWORDS
        customEnchants.put("Vampire", new VampireEnchant());
        customEnchants.put("FullMoon", new FullMoonEnchant());
        customEnchants.put("LifeSteal", new LifeStealEnchant());
        customEnchants.put("Lightning", new LightningEnchant());
        customEnchants.put("Passive", new PassiveEnchant());
        customEnchants.put("Smash", new SmashEnchant());
        customEnchants.put("Toxic", new ToxicEnchant());
        customEnchants.put("Rage", new RageEnchant());
        customEnchants.put("Royal", new RoyalEnchant());
        customEnchants.put("Unstable", new UnstableEnchant());
        customEnchants.put("Ingress", new IngressEnchant());
        customEnchants.put("Demonic", new DemonicEnchant());
        customEnchants.put("Berserker", new BerserkerEnchant());
        customEnchants.put("Haze", new HazeEnchant());
        customEnchants.put("DragonBall", new DragonBallEnchant());
        customEnchants.put("Bleeding", new BleedingEnchant());
        customEnchants.put("IronArmy", new IronArmyEnchant());
        customEnchants.put("Lust", new LustEnchant());

        // ARMOR
        customEnchants.put("Gears", new GearsEnchant());
        customEnchants.put("Springs", new SpringsEnchant());
        customEnchants.put("Bloom", new BloomEnchant());
        customEnchants.put("SaberHeart", new SaberHeartEnchant());
        customEnchants.put("IronSoul", new IronSoulEnchant());
        customEnchants.put("Cursed", new CursedEnchant());
        customEnchants.put("Karma", new KarmaEnchant());
        customEnchants.put("Immunity", new ImmunityEnchant());
        customEnchants.put("Shield", new ShieldEnchant());
        customEnchants.put("SteelBones", new SteelBonesEnchant());

        for (Listener listener : customEnchants.values())
            instance.getServer().getPluginManager().registerEvents(listener, instance);
    }

    public ItemStack getRandomEnchant(EnchantRarity enchantRarity) {
        List<Enchant> enchants = new ArrayList<>(customEnchants.values());

        int random = new Random().nextInt(customEnchants.size());
        Enchant enchant = enchants.get(random);
        ItemBuilder ib = new ItemBuilder(enchant.getBookItem().clone());

        switch (enchantRarity) {
            case COMMON:

                int commonLevel;

                commonLevel = 1;

                if (commonLevel > enchant.getMaxLevel()) {
                    ib.setName(EnchantUtils.getColor(enchant.getMaxLevel()) + enchant.getName() + " " + EnchantUtils.convertRomanNumeral(enchant.getMaxLevel()));
                } else
                    ib.setName(EnchantUtils.getColor(commonLevel) + enchant.getName() + " " + EnchantUtils.convertRomanNumeral(commonLevel));
                break;

            case RARE:

                int rareRandom = new Random().nextInt(100 + 1);
                int rareLevel;

                if (rareRandom < 60) {
                    rareLevel = 2;
                } else
                    rareLevel = 3;

                if (rareLevel > enchant.getMaxLevel()) {
                    ib.setName(EnchantUtils.getColor(enchant.getMaxLevel()) + enchant.getName() + " " + EnchantUtils.convertRomanNumeral(enchant.getMaxLevel()));
                } else
                    ib.setName(EnchantUtils.getColor(rareLevel) + enchant.getName() + " " + EnchantUtils.convertRomanNumeral(rareLevel));
                break;

            case LEGENDARY:

                int legRandom = new Random().nextInt(100 + 1);
                int legLevel;

                if (legRandom < 60) {
                    legLevel = 4;
                } else
                    legLevel = 5;

                if (legLevel > enchant.getMaxLevel()) {
                    ib.setName(EnchantUtils.getColor(enchant.getMaxLevel()) + enchant.getName() + " " + EnchantUtils.convertRomanNumeral(enchant.getMaxLevel()));
                } else
                    ib.setName(EnchantUtils.getColor(legLevel) + enchant.getName() + " " + EnchantUtils.convertRomanNumeral(legLevel));
                break;
        }
        return ib.toItemStack();
    }
}