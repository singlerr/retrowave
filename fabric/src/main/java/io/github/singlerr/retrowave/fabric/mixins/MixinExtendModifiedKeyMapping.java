package io.github.singlerr.retrowave.fabric.mixins;

import com.communi.suggestu.scena.core.client.key.KeyModifier;
import io.github.singlerr.retrowave.fabric.features.ExtendedModifiedKeyMapping;
import org.spongepowered.asm.mixin.*;

@Mixin(targets = "com.communi.suggestu.scena.fabric.platform.client.keys.FabricKeyBindingManager$ModifiedKeyMapping")
public abstract class MixinExtendModifiedKeyMapping implements ExtendedModifiedKeyMapping {

    @Unique
    private boolean enabled;

    @Mutable
    @Final
    @Shadow
    private KeyModifier keyModifier;

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public KeyModifier getKeyModifier() {
        return keyModifier;
    }

    @Override
    public void setKeyModifier(KeyModifier keyModifier) {
        this.keyModifier = keyModifier;
    }
}
