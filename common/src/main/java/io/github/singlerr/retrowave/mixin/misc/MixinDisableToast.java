package io.github.singlerr.retrowave.mixin.misc;

import mod.chiselsandbits.api.util.IWithColor;
import mod.chiselsandbits.api.util.IWithIcon;
import mod.chiselsandbits.api.util.IWithText;
import mod.chiselsandbits.client.screens.components.toasts.ChiselsAndBitsNotificationToast;
import mod.chiselsandbits.logic.RightClickInteractionHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChiselsAndBitsNotificationToast.class)
public abstract class MixinDisableToast {

    @Inject(method = "notifyOf", at = @At("HEAD"), remap = false, cancellable = true)
    private static <G extends IWithColor & IWithIcon & IWithText> void retrowave$disableToast(G contents, CallbackInfo ci){
        ci.cancel();
    }

}
