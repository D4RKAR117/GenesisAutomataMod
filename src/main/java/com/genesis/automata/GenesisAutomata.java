package com.genesis.automata;

import com.genesis.automata.registry.ModGroups;
import com.genesis.automata.registry.ModItems;
import com.genesis.automata.registry.ModSounds;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import software.bernie.geckolib3.GeckoLib;

public class GenesisAutomata implements ModInitializer {

    public static final String MOD_ID = "genesis_automata";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    public static ModGroups GROUPS;
    public static ModItems ITEMS;
    public static ModSounds SOUNDS;

    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        LOGGER.info("Genesis starts now!. Hello World!");

        // Initialize Required Dependencies
        GeckoLib.initialize();

        // Start Registries
        ITEMS = new ModItems();
        SOUNDS = new ModSounds();

    }

}