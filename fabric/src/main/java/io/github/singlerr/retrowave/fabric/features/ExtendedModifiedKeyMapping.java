package io.github.singlerr.retrowave.fabric.features;

import com.communi.suggestu.scena.core.client.key.KeyModifier;
import com.mojang.blaze3d.platform.InputConstants;

public interface ExtendedModifiedKeyMapping {

    boolean isEnabled();

    void setEnabled(boolean enabled);

    KeyModifier getKeyModifier();

    void setKeyModifier(KeyModifier modifier);

    InputConstants.Key getKey();
}
