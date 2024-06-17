package io.github.singlerr.retrowave.mixin.misc;

import com.communi.suggestu.scena.core.event.IPlayerRightClickBlockEvent;
import mod.chiselsandbits.api.item.click.ClickProcessingState;
import mod.chiselsandbits.block.entities.ChiseledBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(targets = {"mod.chiselsandbits.registrars.ModEventHandler$2"})
public abstract class MixinImmediateModelUpdate {

    @Inject(method = "handle", at = @At(value = "RETURN", shift = At.Shift.BEFORE), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private void retrowave$applySwingEffect(Player player, InteractionHand interactionHand, ItemStack itemStack, BlockPos blockPos, Direction direction, IPlayerRightClickBlockEvent.Result result, CallbackInfoReturnable<IPlayerRightClickBlockEvent.Result> cir, ClickProcessingState state) {

    }
}
