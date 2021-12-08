package com.genesis.automata.content.items.wrench.renderers;

import com.genesis.automata.content.items.wrench.WrenchItem;
import com.genesis.automata.content.items.wrench.models.WrenchItemModel;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class WrenchItemRenderer extends GeoItemRenderer<WrenchItem> {

    public WrenchItemRenderer() {
        super(new WrenchItemModel());
    }

}
