package io.github.singlerr.retrowave.fabric.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import io.github.singlerr.retrowave.fabric.features.ExtendedModifiedKeyMapping;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyMapping.class)
public abstract class MixinApplyCustomCollision {

    @Unique
    private boolean checkCollision(KeyMapping one, KeyMapping another){
        InputConstants.Key selfKey = ((KeyMappingAccessor) one).getKey();
        InputConstants.Key otherKey = ((KeyMappingAccessor) another).getKey();
        boolean flag;
        if((this instanceof ExtendedModifiedKeyMapping self)){
            flag = !self.isEnabled() && selfKey.equals(otherKey);
        }else{
            if((another instanceof ExtendedModifiedKeyMapping other)){
                flag = !other.isEnabled() && otherKey.equals(selfKey);
            }else{
                flag = otherKey.equals(selfKey);
            }
        }
        return flag;
    }

    @Inject(method = "same", at = @At("HEAD"), cancellable = true)
    private void retrowave$applyCustomCollision(KeyMapping keyMapping, CallbackInfoReturnable<Boolean> cir){
        if(! (this instanceof ExtendedModifiedKeyMapping self) || ! (keyMapping instanceof ExtendedModifiedKeyMapping other)){
            cir.setReturnValue(checkCollision((KeyMapping) (Object)this, keyMapping));
            cir.cancel();
            return;
        }

        if(! (self.isEnabled()) || ! (other.isEnabled())){
            cir.setReturnValue(false);
            cir.cancel();
            return;
        }

        cir.setReturnValue(self.getKeyModifier().equals(other.getKeyModifier()) && (((KeyMappingAccessor) this).getKey().equals(((KeyMappingAccessor) keyMapping).getKey())));
        cir.cancel();
    }
}
