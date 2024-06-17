package io.github.singlerr.retrowave.mixin.features.replace;

import io.github.singlerr.retrowave.Retrowave;
import io.github.singlerr.retrowave.features.ChiselingOperationExtension;
import mod.chiselsandbits.api.chiseling.ChiselingOperation;
import mod.chiselsandbits.api.item.click.ClickProcessingState;
import mod.chiselsandbits.item.bit.BitItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BitItem.class)
public abstract class MixinApplyChiselingOpExtToItem {

    @Inject(method = "handleRightClickProcessing", at = @At("HEAD"))
    private void retrowave$setReplaceMode(Player playerEntity, InteractionHand hand, BlockPos position, Direction face, ClickProcessingState currentState, CallbackInfoReturnable<ClickProcessingState> cir){

    }
}
