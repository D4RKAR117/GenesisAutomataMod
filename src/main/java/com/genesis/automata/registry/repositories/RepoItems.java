package com.genesis.automata.registry.repositories;

import com.genesis.automata.classes.GenesisItem;
import com.genesis.automata.classes.items.WrenchItem;

import net.minecraft.item.ItemGroup;

public abstract class RepoItems {

    public static final GenesisItem WRENCH = new WrenchItem("wrench", ItemGroup.TOOLS);

}
