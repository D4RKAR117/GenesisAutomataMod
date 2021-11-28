package com.genesis.automata.classes.items;

import com.genesis.automata.classes.GenesisItem;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class WrenchItem extends GenesisItem {

    private static Settings buildItemSettings(ItemGroup group) {
        Settings itemSettings = new Settings();
        itemSettings.group(group);
        itemSettings.maxCount(1);
        itemSettings.fireproof();
        return itemSettings;
    }

    public WrenchItem(String identifier, ItemGroup group) {
        super(buildItemSettings(group), identifier);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.MAIN_HAND)
            user.sendMessage(new LiteralText("Used wrench"), true);
        return super.use(world, user, hand);
    }

}
