package com.genesis.automata.registry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.genesis.automata.registry.repositories.RepoItems;
import com.genesis.automata.registry.repositories.RepoSounds;
import com.genesis.automata.GenesisAutomata;
import com.genesis.automata.classes.GenesisItem;
import com.genesis.automata.classes.GenesisSound;
import com.genesis.automata.classes.utils.GenesisUtils;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ContentRegistry {

    public static Map<String, GenesisItem> MOD_ITEMS = new HashMap<>();
    public static Map<String, GenesisSound> MOD_SOUNDS = new HashMap<>();

    private static void initializeItemRegistry() throws IllegalArgumentException, IllegalAccessException {
        Field[] repo = RepoItems.class.getDeclaredFields();
        for (Field field : repo) {
            int fieldModifiers = field.getModifiers();
            boolean isFinalStatic = Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers);
            boolean isGenesisItem = field.getType().equals(GenesisItem.class);
            boolean hasRightName = GenesisUtils.isRightRegistryName(field.getName());
            if (isFinalStatic && hasRightName && isGenesisItem) {
                GenesisItem item = (GenesisItem) field.get(null);
                MOD_ITEMS.put(item.identifier, item);
            }
        }
    }

    public static void registerModItems() throws IllegalArgumentException, IllegalAccessException {
        initializeItemRegistry();
        GenesisAutomata.LOGGER.info("Located {} Items to register", MOD_ITEMS.size());
        MOD_ITEMS.forEach((identifier, item) -> {
            GenesisAutomata.LOGGER.info("Registering Item {}", identifier);
            MOD_ITEMS.put(identifier,
                    Registry.register(Registry.ITEM, new Identifier(GenesisAutomata.MOD_ID, identifier), item));
            GenesisAutomata.LOGGER.info("Registering Item {} Renderer", identifier);
            GeoItemRenderer.registerItemRenderer(item, item.renderer);
        });
    }

    private static void initializeSoundRegistry() throws IllegalArgumentException, IllegalAccessException {
        Field[] repo = RepoSounds.class.getDeclaredFields();
        for (Field field : repo) {
            int fieldModifiers = field.getModifiers();
            boolean isFinalStatic = Modifier.isStatic(fieldModifiers) && Modifier.isFinal(fieldModifiers);
            boolean isGenesisSound = field.getType().equals(GenesisSound.class);
            boolean hasRightName = GenesisUtils.isRightRegistryName(field.getName());
            if (isFinalStatic && isGenesisSound && hasRightName) {
                GenesisSound sound = (GenesisSound) field.get(null);
                MOD_SOUNDS.put(String.format("%s:%s.%s.%s", GenesisAutomata.MOD_ID, sound.category, sound.domain,
                        sound.identifier), sound);
            }
        }
    }

    public static void registerModSounds() throws IllegalArgumentException, IllegalAccessException {
        initializeSoundRegistry();
        GenesisAutomata.LOGGER.info("Located {} Sound Events to register", MOD_SOUNDS.size());
        MOD_SOUNDS.forEach((identifier, sound) -> {
            GenesisAutomata.LOGGER.info("Registering Sound {}", identifier);
            MOD_SOUNDS.put(identifier,
                    Registry.register(Registry.SOUND_EVENT, new Identifier(GenesisAutomata.MOD_ID, sound.identifier),
                            sound));
        });
    }

    public static void initialize() throws IllegalArgumentException, IllegalAccessException {
        GenesisAutomata.LOGGER.info("Initializing content registry");
        registerModItems();
        registerModSounds();
    }

}