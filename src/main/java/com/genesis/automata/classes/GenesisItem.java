package com.genesis.automata.classes;

import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public abstract class GenesisItem extends Item implements IAnimatable, ISyncable {

    public String identifier;
    public GeoItemRenderer<? extends GenesisItem> renderer;

    public GenesisItem(Settings settings, String identifier, GeoItemRenderer<? extends GenesisItem> renderer) {
        super(settings);
        this.identifier = identifier;
        this.renderer = renderer;
    }
}
