package com.genesis.automata.content.items.wrench.models;

import com.genesis.automata.GenesisAutomata;
import com.genesis.automata.content.items.wrench.WrenchItem;

import net.minecraft.util.Identifier;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class WrenchItemModel extends AnimatedGeoModel<WrenchItem> {

    @Override
    public Identifier getAnimationFileLocation(WrenchItem animatable) {
        return new Identifier(GenesisAutomata.MOD_ID,
                String.format("animations/items/%s.animation.json", animatable.identifier));
    }

    @Override
    public Identifier getModelLocation(WrenchItem object) {
        return new Identifier(GenesisAutomata.MOD_ID, String.format("geo/items/%s.geo.json", object.identifier));
    }

    @Override
    public Identifier getTextureLocation(WrenchItem object) {
        return new Identifier(GenesisAutomata.MOD_ID, String.format("textures/items/%s.png", object.identifier));
    }

}
