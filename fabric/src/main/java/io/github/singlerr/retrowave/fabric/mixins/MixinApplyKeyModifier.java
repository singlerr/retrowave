package io.github.singlerr.retrowave.fabric.mixins;

import com.communi.suggestu.scena.core.client.key.IKeyConflictContext;
import io.github.singlerr.retrowave.fabric.features.ExtendedModifiedKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "com.communi.suggestu.scena.fabric.platform.client.keys.FabricKeyBindingManager$ModifiedKeyMapping")
public abstract class MixinApplyKeyModifier {

    @Inject(method = "isKeyModifierActive", at = @At("HEAD"), cancellable = true, remap = false)
    private void retrowave$applyKeyModifierSettings(CallbackInfoReturnable<Boolean> cir){
        ExtendedModifiedKeyMapping keyMapping = (ExtendedModifiedKeyMapping) this;
        if(! keyMapping.isEnabled()){
            cir.setReturnValue(true);
            cir.cancel();
        }
    }

}
