package io.github.singlerr.retrowave.mixin.features.replace;

import io.github.singlerr.retrowave.features.ChiselingOperationExtension;
import mod.chiselsandbits.api.chiseling.ChiselingOperation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ChiselingOperation.class)
public abstract class MixinApplyChiselingOpExtension implements ChiselingOperationExtension {


    @Unique
    private boolean replacing = false;

    @Override
    public void setReplacing(boolean replacing) {
        this.replacing = replacing;
    }

    @Override
    public boolean isReplacing() {
        return replacing;
    }
}
