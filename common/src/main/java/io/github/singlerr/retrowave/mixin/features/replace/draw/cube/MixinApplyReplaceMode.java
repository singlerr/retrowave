package io.github.singlerr.retrowave.mixin.features.replace.draw.cube;

import com.google.common.collect.Maps;
import io.github.singlerr.retrowave.features.MixinHelper;
import mod.chiselsandbits.api.blockinformation.IBlockInformation;
import mod.chiselsandbits.api.change.IChangeTrackerManager;
import mod.chiselsandbits.api.chiseling.IChiselingContext;
import mod.chiselsandbits.api.item.click.ClickProcessingState;
import mod.chiselsandbits.api.multistate.StateEntrySize;
import mod.chiselsandbits.api.util.IBatchMutation;
import mod.chiselsandbits.chiseling.modes.cubed.CubedChiselMode;
import mod.chiselsandbits.chiseling.modes.draw.DrawnCubeChiselMode;
import mod.chiselsandbits.utils.BitInventoryUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Mixin(DrawnCubeChiselMode.class)
public abstract class MixinApplyReplaceMode implements MixinHelper {

    @Shadow protected abstract ClickProcessingState processRayTraceIntoContext(Player playerEntity, IChiselingContext context, Function<Direction, Vec3> offsetGenerator);

    @Unique
    private ClickProcessingState replace(final Player playerEntity, final IChiselingContext context){
        return processRayTraceIntoContext(
                playerEntity,
                context,
                direction -> Vec3.atLowerCornerOf(direction.getOpposite().getNormal()).multiply(StateEntrySize.current().getSizePerHalfBitScalingVector())
        );
    }
    @Unique
    private void replaceEnd(final Player playerEntity, final IChiselingContext context){
        replace(playerEntity, context);
        context.setComplete();

        if (context.isSimulation())
            return;

        context.getMutator().ifPresent(mutator -> {
            try (IBatchMutation ignored =
                         mutator.batch(IChangeTrackerManager.getInstance().getChangeTracker(playerEntity)))
            {
                final IBlockInformation heldBlockState = getHeldBlockState(playerEntity);
                mutator.inWorldMutableStream()
                        .forEach(state -> {
                            state.overrideState(heldBlockState);
                        });

            }
        });
    }
    @Inject(method = "onRightClickBy", at = @At("HEAD"), remap = false, cancellable = true)
    private void retrowave$onReplaceMode(Player playerEntity, IChiselingContext context, CallbackInfoReturnable<ClickProcessingState> cir){
        if(getChiselingOpExtension(context.getModeOfOperandus()).isReplacing()){
            cir.setReturnValue(replace(playerEntity, context));
            cir.cancel();
        }
    }

    @Inject(method = "onStoppedRightClicking", at = @At("HEAD"), remap = false, cancellable = true)
    private void retrowave$onReplaceModeEnd(Player playerEntity, IChiselingContext context, CallbackInfo ci){
        if(getChiselingOpExtension(context.getModeOfOperandus()).isReplacing()){
            replaceEnd(playerEntity, context);
            ci.cancel();
        }
    }
}
