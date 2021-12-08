package com.genesis.automata.content.items;

import net.minecraft.item.Item;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.network.ISyncable;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public abstract class BaseItem extends Item implements IAnimatable, ISyncable {

    public String identifier;
    public GeoItemRenderer<? extends BaseItem> renderer;

    public BaseItem(String identifier, GeoItemRenderer<? extends BaseItem> renderer, Settings settings) {
        super(settings);
        this.identifier = identifier;
        this.renderer = renderer;
    }

}
