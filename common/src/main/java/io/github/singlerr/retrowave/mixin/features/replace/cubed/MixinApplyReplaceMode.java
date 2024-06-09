package io.github.singlerr.retrowave.mixin.features.replace.cubed;

import io.github.singlerr.retrowave.features.MixinHelper;
import mod.chiselsandbits.api.blockinformation.IBlockInformation;
import mod.chiselsandbits.api.change.IChangeTrackerManager;
import mod.chiselsandbits.api.chiseling.IChiselingContext;
import mod.chiselsandbits.api.item.click.ClickProcessingState;
import mod.chiselsandbits.api.util.IBatchMutation;
import mod.chiselsandbits.chiseling.modes.cubed.CubedChiselMode;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Function;

@Mixin(CubedChiselMode.class)
public abstract class MixinApplyReplaceMode implements MixinHelper {


    @Shadow protected abstract Optional<ClickProcessingState> processRayTraceIntoContext(Player playerEntity, IChiselingContext context, Function<Direction, Vec3> placementFacingAdapter, Function<Vec3, Vec3> fullFacingVectorAdapter);

    @Unique
    private ClickProcessingState replace(final Player playerEntity, final IChiselingContext context){
        final Optional<ClickProcessingState> rayTraceHandle = this.processRayTraceIntoContext(
                playerEntity,
                context,
                face -> Vec3.atLowerCornerOf(face.getOpposite().getNormal()),
                Function.identity()
        );

        if (context.isSimulation())
        {
            return ClickProcessingState.DEFAULT;
        }

        context.setComplete();
        return rayTraceHandle.orElseGet(() -> context.getMutator().map(mutator -> {
                    try (IBatchMutation ignored =
                                 mutator.batch(IChangeTrackerManager.getInstance().getChangeTracker(playerEntity)))
                    {
                        final IBlockInformation heldBlockState = getHeldBlockState(playerEntity);

                        final int totalItemDamage = mutator.inWorldMutableStream()
                                .mapToInt(state -> {
                                    state.overrideState(heldBlockState);
                                    return 1;
                                }).sum();
                    }

                    return ClickProcessingState.ALLOW;
                }).orElse(ClickProcessingState.DEFAULT)
        );
    }

    @Inject(method = "onRightClickBy", at = @At("HEAD"), remap = false, cancellable = true)
    private void retrowave$onReplaceMode(Player playerEntity, IChiselingContext context, CallbackInfoReturnable<ClickProcessingState> cir){
        if(getChiselingOpExtension(context.getModeOfOperandus()).isReplacing()){
            cir.setReturnValue(replace(playerEntity, context));
            cir.cancel();
        }
    }
}
