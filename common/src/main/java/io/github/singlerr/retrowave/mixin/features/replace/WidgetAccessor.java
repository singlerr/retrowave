package io.github.singlerr.retrowave.mixin.features.replace;

import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(AbstractWidget.class)
public interface WidgetAccessor {

    @Invoker("setMessage")
    void setMessage(Component component);
}
