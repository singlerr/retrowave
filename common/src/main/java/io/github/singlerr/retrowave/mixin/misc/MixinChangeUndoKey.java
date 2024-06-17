package io.github.singlerr.retrowave.mixin.misc;

import com.communi.suggestu.scena.core.client.key.IKeyBindingManager;
import com.communi.suggestu.scena.core.client.key.IKeyConflictContext;
import com.communi.suggestu.scena.core.client.key.KeyModifier;
import com.mojang.blaze3d.platform.InputConstants;
import mod.chiselsandbits.keys.KeyBindingManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = KeyBindingManager.class, priority = 1)
public abstract class MixinChangeUndoKey {

    @Inject(method = "onModInitialization", at = @At(value = "INVOKE", target = "Lcom/communi/suggestu/scena/core/client/key/IKeyBindingManager;createNew(Ljava/lang/String;Lcom/communi/suggestu/scena/core/client/key/IKeyConflictContext;Lcom/communi/suggestu/scena/core/client/key/KeyModifier;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)Lnet/minecraft/client/KeyMapping;", ordinal = 1, remap = false), remap = false)
    private void retrowave$changeUndoKey(CallbackInfo ci){
    }

}
