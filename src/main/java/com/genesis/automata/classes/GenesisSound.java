package com.genesis.automata.classes;

import com.genesis.automata.GenesisAutomata;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public abstract class GenesisSound extends SoundEvent {

    public String category;
    public String domain;
    public String identifier;

    public GenesisSound(String category, String domain, String identifier) {
        super(new Identifier(GenesisAutomata.MOD_ID, identifier));
        this.category = category;
        this.domain = domain;
        this.identifier = identifier;
    }

}
