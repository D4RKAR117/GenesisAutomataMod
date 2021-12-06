package com.genesis.automata.registry.repositories;

import com.genesis.automata.classes.GenesisSound;
import com.genesis.automata.classes.sounds.WrenchInteractionSound;

public abstract class RepoSounds {
    public final static GenesisSound WRENCH_INTERACTION_SOUND = new WrenchInteractionSound("item", "wrench",
            "wrench_interaction");
}
