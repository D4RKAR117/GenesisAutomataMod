package com.genesis.automata.classes;

import net.minecraft.item.Item;

public class GenesisItem extends Item {

    public String identifier;

    public GenesisItem(Settings settings, String identifier) {
        super(settings);
        this.identifier = identifier;
    }

}
