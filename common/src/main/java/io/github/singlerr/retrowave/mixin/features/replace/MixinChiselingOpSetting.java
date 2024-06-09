package io.github.singlerr.retrowave.mixin.features.replace;

import io.github.singlerr.retrowave.Retrowave;
import io.github.singlerr.retrowave.config.RetrowaveConfig;
import io.github.singlerr.retrowave.features.ChiselingOperationExtension;
import io.github.singlerr.retrowave.gui.widgets.ChiselingOperationModeWidget;
import io.netty.util.internal.SuppressJava6Requirement;
import mod.chiselsandbits.api.chiseling.ChiselingOperation;
import mod.chiselsandbits.api.client.screen.AbstractChiselsAndBitsScreen;
import mod.chiselsandbits.api.item.withmode.IWithModeItem;
import mod.chiselsandbits.client.screens.ToolModeSelectionScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ToolModeSelectionScreen.class)
public abstract class MixinChiselingOpSetting extends AbstractChiselsAndBitsScreen {

    @Unique
    private ChiselingOperationModeWidget btn;

    protected MixinChiselingOpSetting(Component narrationMessage) {
        super(narrationMessage);
    }

    @Inject(method = "init", at = @At("TAIL"), remap = false)
    private void retrowave$addButton(CallbackInfo ci){
        addRenderableWidget(btn = new ChiselingOperationModeWidget(
                width - ChiselingOperationModeWidget.WIDTH - 6,
                ChiselingOperationModeWidget.HEIGHT + 6,
                Retrowave.getConfig().isReplaceMode() ?
                        Component.translatable("retrowave.chiseling.replace.mode") : Component.translatable("retrowave.chiseling.place.mode"),
                        this::retrowave$setChiselingOperation
                ));
    }

    @Unique
    private void retrowave$setChiselingOperation(Button button){
        Retrowave.getConfig().setReplaceMode(! Retrowave.getConfig().isReplaceMode());
        ChiselingOperationExtension ext = (ChiselingOperationExtension) (Object) ChiselingOperation.PLACING;
        ext.setReplacing(Retrowave.getConfig().isReplaceMode());
        btn.setMessage( Retrowave.getConfig().isReplaceMode() ?
                Component.translatable("retrowave.chiseling.replace.mode") : Component.translatable("retrowave.chiseling.place.mode"));
        Retrowave.getConfigHolder().save();
    }
}
