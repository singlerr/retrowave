package io.github.singlerr.retrowave.mixin.features.replace.sphere;

import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import io.github.singlerr.retrowave.features.MixinHelper;
import mod.chiselsandbits.api.blockinformation.IBlockInformation;
import mod.chiselsandbits.api.change.IChangeTrackerManager;
import mod.chiselsandbits.api.chiseling.IChiselingContext;
import mod.chiselsandbits.api.item.click.ClickProcessingState;
import mod.chiselsandbits.api.multistate.accessor.IStateEntryInfo;
import mod.chiselsandbits.api.util.IBatchMutation;
import mod.chiselsandbits.api.util.LocalStrings;
import mod.chiselsandbits.chiseling.modes.cubed.CubedChiselMode;
import mod.chiselsandbits.chiseling.modes.sphere.SphereChiselMode;
import mod.chiselsandbits.registrars.ModMetadataKeys;
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
import java.util.function.Predicate;

@Mixin(SphereChiselMode.class)
public abstract class MixinApplyReplaceMode implements MixinHelper {

    @Shadow protected abstract Either<ClickProcessingState, Vec3> processRayTraceIntoContext(Player playerEntity, IChiselingContext context, Function<Direction, Vec3> placementFacingAdapter, Function<Vec3, Vec3> fullFacingVectorAdapter);

    @Unique
    private ClickProcessingState replace(final Player playerEntity, final IChiselingContext context){
        final Either<ClickProcessingState, Vec3> rayTraceHandle = this.processRayTraceIntoContext(
                playerEntity,
                context,
                face -> Vec3.atLowerCornerOf(face.getOpposite().getNormal()),
                facing -> facing.multiply(-1, -1, -1)
        );

        if (rayTraceHandle.right().isPresent()) {
            context.setMetadata(ModMetadataKeys.ANCHOR.get(), rayTraceHandle.right().get());
        }

        if (context.isSimulation()) {
            return ClickProcessingState.DEFAULT;
        }

        context.setComplete();

        if (rayTraceHandle.left().isPresent()) {
            return rayTraceHandle.left().get();
        }

        if (rayTraceHandle.right().isEmpty()) {
            throw new IllegalArgumentException("Missing both a click processing result as well as a center vector for sphere processing");
        }

        return context.getMutator().map(mutator -> {
            try (IBatchMutation ignored =
                         mutator.batch(IChangeTrackerManager.getInstance().getChangeTracker(playerEntity))) {

                final IBlockInformation heldBlockState = getHeldBlockState(playerEntity);
                final Predicate<IStateEntryInfo> filter = context.getStateFilter()
                        .map(factory -> factory.apply(mutator))
                        .orElse((s) -> true);

                final int totalModifiedStates = mutator.inWorldMutableStream()
                        .filter(filter)
                        .mapToInt(state -> {
                            state.overrideState(heldBlockState);
                            return 1;
                        })
                        .sum();

                if (totalModifiedStates == 0) {
                    context.setError(LocalStrings.ChiselAttemptFailedNoValidStateFound.getText());
                }
            }

            return ClickProcessingState.ALLOW;
        }).orElse(ClickProcessingState.DEFAULT);
    }

    @Inject(method = "onRightClickBy", at = @At("HEAD"), cancellable = true)
    private void retrowave$onReplaceMode(Player playerEntity, IChiselingContext context, CallbackInfoReturnable<ClickProcessingState> cir){
        if(getChiselingOpExtension(context.getModeOfOperandus()).isReplacing()){
            cir.setReturnValue(replace(playerEntity, context));
            cir.cancel();
        }
    }
}
