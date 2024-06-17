package io.github.singlerr.retrowave.mixin.features.replace.line;

import com.google.common.collect.Maps;
import io.github.singlerr.retrowave.features.MixinHelper;
import mod.chiselsandbits.api.blockinformation.IBlockInformation;
import mod.chiselsandbits.api.change.IChangeTrackerManager;
import mod.chiselsandbits.api.chiseling.IChiselingContext;
import mod.chiselsandbits.api.item.click.ClickProcessingState;
import mod.chiselsandbits.api.util.IBatchMutation;
import mod.chiselsandbits.chiseling.modes.cubed.CubedChiselMode;
import mod.chiselsandbits.chiseling.modes.line.LinedChiselMode;
import mod.chiselsandbits.chiseling.modes.line.LinedChiselModeBuilder;
import mod.chiselsandbits.utils.BitInventoryUtils;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Mixin(LinedChiselMode.class)
public abstract class MixinApplyReplaceMode implements MixinHelper {

    @Shadow protected abstract Optional<ClickProcessingState> processRayTraceIntoContext(Player playerEntity, IChiselingContext context, Function<Direction, Vec3> placementFacingAdapter, Function<Direction, Direction> iterationAdaptor, boolean airOnly);

    @Unique
    private ClickProcessingState replace(final Player playerEntity, final IChiselingContext context){
        final Optional<ClickProcessingState> rayTraceHandle = this.processRayTraceIntoContext(playerEntity, context, face -> Vec3.atLowerCornerOf(face.getOpposite().getNormal()), Direction::getOpposite, false);

        if (context.isSimulation()) {
            return ClickProcessingState.DEFAULT;
        }

        return rayTraceHandle.orElseGet(() -> context.getMutator().map(mutator -> {
            try (IBatchMutation ignored = mutator.batch(IChangeTrackerManager.getInstance().getChangeTracker(playerEntity))) {
                context.setComplete();

                final IBlockInformation heldBlockState = getHeldBlockState(playerEntity);
                mutator.inWorldMutableStream().forEach(state -> {
                    state.overrideState(heldBlockState);
                });
            }

            return ClickProcessingState.ALLOW;
        }).orElse(ClickProcessingState.DEFAULT));
    }

    @Inject(method = "onRightClickBy", at = @At("HEAD"), cancellable = true)
    private void retrowave$onReplaceMode(Player playerEntity, IChiselingContext context, CallbackInfoReturnable<ClickProcessingState> cir){
        if(getChiselingOpExtension(context.getModeOfOperandus()).isReplacing()){
            cir.setReturnValue(replace(playerEntity, context));
            cir.cancel();
        }
    }
}
