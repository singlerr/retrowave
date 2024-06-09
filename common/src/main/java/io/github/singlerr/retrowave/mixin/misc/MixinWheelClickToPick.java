package io.github.singlerr.retrowave.mixin.misc;


import mod.chiselsandbits.api.config.IClientConfiguration;
import mod.chiselsandbits.block.ChiseledBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Supplier;

@Mixin(ChiseledBlock.class)
public abstract class MixinWheelClickToPick {

    @Redirect(method = "getCloneItemStack", at = @At(value = "INVOKE", target = "Lmod/chiselsandbits/api/config/IClientConfiguration;getInvertPickBlockBehaviour()Ljava/util/function/Supplier;"), remap = false)
    private Supplier retrowave$forceClone(IClientConfiguration instance){

        return () -> true;
    }

}
