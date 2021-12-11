package com.genesis.automata.registry;

import java.util.HashMap;
import java.util.Map;

import com.genesis.automata.GenesisAutomata;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModSounds {

    public static Map<String, SoundEvent> LIST = new HashMap<>();

    public static final SoundEvent WRENCH_INTERACTION_SOUND = newSound("sounds.item.wrench.wrench_interaction");

    static SoundEvent newSound(String identifier) {
        GenesisAutomata.LOGGER.info("Registering new Sound Event, identifier: {}", identifier);
        SoundEvent sound = new SoundEvent(new Identifier(GenesisAutomata.MOD_ID, identifier));
        sound = Registry.register(Registry.SOUND_EVENT, new Identifier(GenesisAutomata.MOD_ID, identifier), sound);
        LIST.put(identifier, sound);
        return sound;
    }

}
