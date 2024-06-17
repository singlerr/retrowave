package io.github.singlerr.retrowave.fabric.mixins;

import com.communi.suggestu.scena.fabric.platform.client.keys.FabricKeyBindingManager;
import io.github.singlerr.retrowave.fabric.features.ExtendedModifiedKeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.communi.suggestu.scena.fabric.platform.client.keys.FabricKeyBindingManager$ModifiedKeyMapping")
public abstract class MixinAllowSingleKey {

    @Inject(method = "getKeyModifierMessage", at = @At("HEAD"), cancellable = true)
    private void retrowave$editMessage(CallbackInfoReturnable<MutableComponent> cir){
        ExtendedModifiedKeyMapping keyMapping = (ExtendedModifiedKeyMapping) this;

        if(! keyMapping.isEnabled()){
            cir.setReturnValue(Component.empty());
            cir.cancel();
        }
    }
}
