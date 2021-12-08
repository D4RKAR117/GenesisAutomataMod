package com.genesis.automata.registry;

import com.genesis.automata.GenesisAutomata;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class ModGroups {

    public static final ItemGroup ITEM_GROUP = newGroup(ModItems.WRENCH, "items_group");

    public static ItemGroup newGroup(Item displayItem, String identifier) {
        return FabricItemGroupBuilder.build(
                new Identifier(GenesisAutomata.MOD_ID, identifier),
                () -> new ItemStack(displayItem));
    }

}
