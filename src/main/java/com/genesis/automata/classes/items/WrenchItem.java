package com.genesis.automata.classes.items;

import com.genesis.automata.classes.GenesisItem;
import com.genesis.automata.classes.models.renderers.WrenchItemRenderer;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.network.GeckoLibNetwork;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class WrenchItem extends GenesisItem {

    public AnimationFactory factory = new AnimationFactory(this);
    public static final int ANIM_OPEN = 0;

    private static Settings buildItemSettings(ItemGroup group) {
        Settings itemSettings = new Settings();
        itemSettings.group(group);
        itemSettings.maxCount(1);
        itemSettings.fireproof();
        return itemSettings;
    }

    public WrenchItem(String identifier, ItemGroup group) {
        super(buildItemSettings(group), identifier, new WrenchItemRenderer());
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if (hand == Hand.MAIN_HAND)
            user.sendMessage(new LiteralText("Used wrench"), true);
        if (!world.isClient) {
            ItemStack stack = user.getStackInHand(hand);
            // Gets the item that the player is holding, should be this item.
            final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) world);
            GeckoLibNetwork.syncAnimation(user, this, id, ANIM_OPEN);
            // Tell all nearby clients to trigger this item to animate
            for (PlayerEntity otherPlayer : PlayerLookup.tracking(user)) {
                GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_OPEN);
            }
        }
        return super.use(world, user, hand);
    }

    private <P extends GenesisItem & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        event.getController().setAnimation(new AnimationBuilder().addAnimation("animation.wrench.expand", false));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(
                new AnimationController<WrenchItem>(this, "interaction_controller", 20, this::predicate));

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_OPEN) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id,
                    "interaction_controller");
            if (controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation("animation.wrench.expand", false));
            }
        }
    }

}
