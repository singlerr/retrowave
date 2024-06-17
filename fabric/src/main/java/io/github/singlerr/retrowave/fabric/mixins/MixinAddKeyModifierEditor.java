package io.github.singlerr.retrowave.fabric.mixins;

import com.communi.suggestu.scena.core.client.key.KeyModifier;
import com.google.gson.internal.reflect.ReflectionHelper;
import io.github.singlerr.retrowave.fabric.features.ExtendedModifiedKeyMapping;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.controls.KeyBindsList;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBindsScreen.class)
public abstract class MixinAddKeyModifierEditor {

    @Unique
    private static KeyModifier activeKeyModifier = null;

    @Shadow @Nullable
    public KeyMapping selectedKey;


    @Shadow public long lastKeySelection;

    @Shadow private KeyBindsList keyBindsList;

    @Inject(method = "keyPressed", at = @At("HEAD"), cancellable = true)
    private void retrowave$setKeyModifier(int keyCode, int j, int k, CallbackInfoReturnable<Boolean> cir){
        if(! (selectedKey instanceof ExtendedModifiedKeyMapping extKeyMapping))
            return;

        KeyModifier newKeyModifier = null;

        if(Screen.hasShiftDown()){
            newKeyModifier = KeyModifier.SHIFT;
        }else if(Screen.hasAltDown()){
            newKeyModifier = KeyModifier.ALT;
        }else if(Screen.hasControlDown()){
            newKeyModifier = KeyModifier.CONTROL;
        }
        //No key modifier
        if(newKeyModifier == null){
            if(activeKeyModifier == null){
                extKeyMapping.setEnabled(false);
            }else{
                extKeyMapping.setEnabled(true);
                extKeyMapping.setKeyModifier(activeKeyModifier);
                activeKeyModifier = null;
            }
        }else{
            activeKeyModifier = newKeyModifier;
            extKeyMapping.setKeyModifier(activeKeyModifier);
            extKeyMapping.setEnabled(true);
            this.lastKeySelection = Util.getMillis();
            keyBindsList.refreshEntries();
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
