package com.genesis.automata.registry.repositories;

import com.genesis.automata.GenesisAutomata;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public abstract class RepoGroups {

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder.build(
            new Identifier(GenesisAutomata.MOD_ID, "items_group"),
            () -> new ItemStack(RepoItems.WRENCH));

}
