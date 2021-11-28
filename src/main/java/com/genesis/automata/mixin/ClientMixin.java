package com.genesis.automata.mixin;

import com.genesis.automata.GenesisAutomata;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.gui.screen.TitleScreen;

@Mixin(TitleScreen.class)
public class ClientMixin {
    @Inject(at = @At("HEAD"), method = "init()V")
    private void init(CallbackInfo info) {
        GenesisAutomata.LOGGER.info("Genesis Client Mixin Loaded");
    }
}
