package com.genesis.automata.classes.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.genesis.automata.classes.GenesisItem;
import com.genesis.automata.classes.models.renderers.WrenchItemRenderer;
import com.genesis.automata.registry.repositories.RepoSounds;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
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
    public static final Map<String, Integer> ANIM_STATES = new HashMap<>() {
        {
            put("expand", 0);
            put("adjust", 1);
        }
    };
    public static final String ANIM_CONTROLLER = "interaction_controller";
    public static final Map<String, String> ANIM_LIST = new HashMap<>() {
        {
            put("expand", "animation.wrench.expand");
            put("adjust", "animation.wrench.adjust");
        }
    };

    private static Settings buildItemSettings(ItemGroup group) {
        Settings itemSettings = new Settings();
        itemSettings.group(group);
        itemSettings.maxCount(1);
        itemSettings.fireproof();
        itemSettings.rarity(Rarity.UNCOMMON);
        return itemSettings;
    }

    public WrenchItem(String identifier, ItemGroup group) {
        super(buildItemSettings(group), identifier, new WrenchItemRenderer());
        GeckoLibNetwork.registerSyncable(this);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.playSound(RepoSounds.WRENCH_INTERACTION_SOUND, 1.0F, 1.0F);
        if (!world.isClient) {
            // Gets the item that the player is holding, should be this item.
            ItemStack stack = user.getStackInHand(hand);
            final int id = GeckoLibUtil.guaranteeIDForStack(stack, (ServerWorld) world);
            if (hand == Hand.MAIN_HAND) {

                if (!user.isSneaking()) {
                    GeckoLibNetwork.syncAnimation(user, this, id, ANIM_STATES.get("expand"));
                    // Tell all nearby clients to trigger this item to animate
                    for (PlayerEntity otherPlayer : PlayerLookup.tracking(user)) {
                        GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_STATES.get("expand"));
                    }
                } else if (user.isSneaking()) {
                    GeckoLibNetwork.syncAnimation(user, this, id, ANIM_STATES.get("adjust"));
                    // Tell all nearby clients to trigger this item to animate
                    for (PlayerEntity otherPlayer : PlayerLookup.tracking(user)) {
                        GeckoLibNetwork.syncAnimation(otherPlayer, this, id, ANIM_STATES.get("adjust"));
                    }
                }
            }

        }
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("item.genesis_automata.wrench.tooltip"));
        super.appendTooltip(stack, world, tooltip, context);
    }

    private <P extends GenesisItem & IAnimatable> PlayState predicate(AnimationEvent<P> event) {
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        AnimationController<WrenchItem> mainController = new AnimationController<WrenchItem>(this, ANIM_CONTROLLER, 20,
                this::predicate);
        data.addAnimationController(mainController);

    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public void onAnimationSync(int id, int state) {
        if (state == ANIM_STATES.get("expand")) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id,
                    ANIM_CONTROLLER);
            if (controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation(ANIM_LIST.get("expand"), false));
            }
        }

        if (state == ANIM_STATES.get("adjust")) {
            final AnimationController<?> controller = GeckoLibUtil.getControllerForID(this.factory, id,
                    ANIM_CONTROLLER);
            if (controller.getAnimationState() == AnimationState.Stopped) {
                controller.markNeedsReload();
                controller.setAnimation(new AnimationBuilder().addAnimation(ANIM_LIST.get("adjust"), false));
            }
        }

    }

}
