package com.genesis.automata.registry;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

import com.genesis.automata.registry.repositories.RepoItems;
import com.genesis.automata.GenesisAutomata;
import com.genesis.automata.classes.GenesisItem;
import com.genesis.automata.classes.utils.GenesisUtils;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ContentRegistry {

    public static Map<String, GenesisItem> MOD_ITEMS = new HashMap<>();

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
            Registry.register(Registry.ITEM, new Identifier(GenesisAutomata.MOD_ID, identifier), item);
        });
    }

    public static void initialize() throws IllegalArgumentException, IllegalAccessException {
        GenesisAutomata.LOGGER.info("Initializing content registry");
        registerModItems();
    }

}