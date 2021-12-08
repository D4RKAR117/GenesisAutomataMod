package com.genesis.automata.registry;

import com.genesis.automata.GenesisAutomata;
import com.genesis.automata.content.items.BaseItem;
import com.genesis.automata.content.items.wrench.WrenchItem;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import software.bernie.geckolib3.renderers.geo.GeoItemRenderer;

public class ModItems {

    public final static BaseItem WRENCH = newItem(new WrenchItem("wrench", ModGroups.ITEM_GROUP));

    public static BaseItem newItem(BaseItem item) {
        GenesisAutomata.LOGGER.info("Registering new Item, identifier: {}", item.identifier);
        BaseItem registry = Registry.register(Registry.ITEM, new Identifier(GenesisAutomata.MOD_ID, item.identifier),
                item);
        GenesisAutomata.LOGGER.info("Registering new Item Renderer, identifier: {}_renderer", item.identifier);
        GeoItemRenderer.registerItemRenderer(registry, registry.renderer);
        return registry;
    }
}
