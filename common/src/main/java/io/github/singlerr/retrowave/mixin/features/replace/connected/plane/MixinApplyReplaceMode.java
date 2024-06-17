package io.github.singlerr.retrowave.mixin.features.replace.connected.plane;

import com.google.common.collect.Maps;
import io.github.singlerr.retrowave.features.MixinHelper;
import mod.chiselsandbits.api.blockinformation.IBlockInformation;
import mod.chiselsandbits.api.change.IChangeTrackerManager;
import mod.chiselsandbits.api.chiseling.IChiselingContext;
import mod.chiselsandbits.api.item.click.ClickProcessingState;
import mod.chiselsandbits.api.multistate.accessor.IAreaAccessor;
import mod.chiselsandbits.api.multistate.accessor.IStateEntryInfo;
import mod.chiselsandbits.api.multistate.mutator.IMutatorFactory;
import mod.chiselsandbits.api.util.IBatchMutation;
import mod.chiselsandbits.api.util.IQuadFunction;
import mod.chiselsandbits.api.util.LocalStrings;
import mod.chiselsandbits.api.util.VectorUtils;
import mod.chiselsandbits.chiseling.modes.connected.plane.ConnectedPlaneChiselingMode;
import mod.chiselsandbits.chiseling.modes.cubed.CubedChiselMode;
import mod.chiselsandbits.utils.BitInventoryUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
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
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

@Mixin(ConnectedPlaneChiselingMode.class)
public abstract class MixinApplyReplaceMode implements MixinHelper {


    @Shadow protected abstract Optional<ClickProcessingState> processRayTraceIntoContext(Player playerEntity, IChiselingContext context, UnaryOperator<Direction> searchDirectionAdapter, Function<Direction, Vec3> placementFacingAdapter, IQuadFunction<BlockPos, Vec3, Direction, Vec3, Vec3> stateExtractionAdapter, Function<Vec3, IAreaAccessor> areaAccessorBuilder, Function<Direction, Vec3i> filterOffsetProducer);

    @Unique
    private ClickProcessingState replace(final Player playerEntity, final IChiselingContext context){
        final Optional<ClickProcessingState> rayTraceHandle = this.processRayTraceIntoContext(
                playerEntity,
                context,
                Direction::getOpposite,
                face -> Vec3.atLowerCornerOf(face.getOpposite().getNormal()),
                IQuadFunction.fourthIdentity(),
                position -> IMutatorFactory.getInstance().in(
                        context.getWorld(),
                        VectorUtils.toBlockPos(position)
                ),
                direction -> Vec3i.ZERO
        );

        if (context.isSimulation())
        {
            return ClickProcessingState.DEFAULT;
        }

        return rayTraceHandle.orElseGet(() -> context.getMutator().map(mutator -> {
                    try (IBatchMutation ignored =
                                 mutator.batch(IChangeTrackerManager.getInstance().getChangeTracker(playerEntity)))
                    {
                        context.setComplete();

                        final IBlockInformation heldBlockState = getHeldBlockState(playerEntity);
                        final Predicate<IStateEntryInfo> filter = context.getStateFilter()
                                .map(builder -> builder.apply(mutator))
                                .orElse((state) -> true);

                        final int totalDamage = mutator.inWorldMutableStream()
                                .filter(filter)
                                .mapToInt(state -> {
                                    state.overrideState(heldBlockState);
                                    return 1;
                                }).sum();

                        if (totalDamage == 0) {
                            context.setError(LocalStrings.ChiselAttemptFailedNoValidStateFound.getText());
                        }
                    }

                    return ClickProcessingState.ALLOW;
                }).orElse(ClickProcessingState.DEFAULT)
        );
    }

    @Inject(method = "onRightClickBy", at = @At("HEAD"), cancellable = true)
    private void retrowave$onReplaceMode(Player playerEntity, IChiselingContext context, CallbackInfoReturnable<ClickProcessingState> cir){
        if(getChiselingOpExtension(context.getModeOfOperandus()).isReplacing()){
            cir.setReturnValue(replace(playerEntity, context));
            cir.cancel();
        }
    }
}
