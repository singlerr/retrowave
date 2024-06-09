package io.github.singlerr.retrowave.mixin.misc;

import mod.chiselsandbits.keys.KeyBindingManager;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(KeyBindingManager.class)
public abstract class MixinChangeOpenToolKeyCode {

    @ModifyArg(method = "onModInitialization", at = @At(value = "INVOKE", target = "Lcom/communi/suggestu/scena/core/client/key/IKeyBindingManager;createNew(Ljava/lang/String;Lcom/communi/suggestu/scena/core/client/key/IKeyConflictContext;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)Lnet/minecraft/client/KeyMapping;", ordinal = 0),index = 3, remap = false)
    private int retrowave$changeOpenToolKey(int i){

        return GLFW.GLFW_KEY_LEFT_ALT;
    }
}
