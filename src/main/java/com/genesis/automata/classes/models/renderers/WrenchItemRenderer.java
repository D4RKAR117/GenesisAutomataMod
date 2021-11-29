package com.genesis.automata.classes.models.renderers;

import com.genesis.automata.classes.items.WrenchItem;
import com.genesis.automata.classes.models.WrenchItemModel;

import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class WrenchItemRenderer extends GeoItemRenderer<WrenchItem> {

    public WrenchItemRenderer() {
        super(new WrenchItemModel());
    }

}
