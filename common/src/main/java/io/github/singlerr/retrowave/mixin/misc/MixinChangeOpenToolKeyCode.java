package io.github.singlerr.retrowave.mixin.misc;

import com.communi.suggestu.scena.core.client.key.IKeyBindingManager;
import com.communi.suggestu.scena.core.client.key.IKeyConflictContext;
import com.communi.suggestu.scena.core.client.key.KeyModifier;
import com.mojang.blaze3d.platform.InputConstants;
import mod.chiselsandbits.keys.KeyBindingManager;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KeyBindingManager.class)
public abstract class MixinChangeOpenToolKeyCode {

    @ModifyArg(method = "onModInitialization", at = @At(value = "INVOKE", target = "Lcom/communi/suggestu/scena/core/client/key/IKeyBindingManager;createNew(Ljava/lang/String;Lcom/communi/suggestu/scena/core/client/key/IKeyConflictContext;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)Lnet/minecraft/client/KeyMapping;", ordinal = 0),index = 3)
    private int retrowave$changeOpenToolKey(int i){
        return GLFW.GLFW_KEY_LEFT_ALT;
    }

}
