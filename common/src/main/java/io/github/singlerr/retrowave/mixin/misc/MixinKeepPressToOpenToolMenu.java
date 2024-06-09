package io.github.singlerr.retrowave.mixin.misc;

import mod.chiselsandbits.client.screens.ToolModeSelectionScreen;
import mod.chiselsandbits.keys.KeyBindingManager;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(KeyBindingManager.class)
public abstract class MixinKeepPressToOpenToolMenu {

    @Shadow private boolean toolMenuKeyWasDown;

    @Shadow public abstract boolean isOpenToolMenuKeyPressed();

    @Redirect(method = "handleKeyPresses", at = @At(value = "FIELD", target = "Lmod/chiselsandbits/keys/KeyBindingManager;toolMenuKeyWasDown:Z", ordinal = 2), remap = false)
    private void retrowve$checkKeyPressed(KeyBindingManager instance, boolean value){
        if(Minecraft.getInstance().screen instanceof ToolModeSelectionScreen<?,?>){
            if(toolMenuKeyWasDown && ! isOpenToolMenuKeyPressed()){
                //Close screen
                Minecraft.getInstance().setScreen(null);
                return;
            }
        }
        toolMenuKeyWasDown = value;
    }
}
