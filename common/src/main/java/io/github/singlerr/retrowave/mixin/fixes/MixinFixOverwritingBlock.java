package io.github.singlerr.retrowave.mixin.fixes;

import mod.chiselsandbits.change.ChangeTracker;
import mod.chiselsandbits.change.ChangeTrackerManger;
import mod.chiselsandbits.change.ChangeTrackerSyncManager;
import mod.chiselsandbits.network.packets.NeighborBlockUpdatedPacket;
import net.minecraft.client.gui.screens.controls.KeyBindsScreen;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChangeTrackerSyncManager.class)
public abstract class MixinFixOverwritingBlock {

    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void retrowave$disableTracking(ChangeTracker tracker, ServerPlayer serverPlayer, CallbackInfo ci){
        ci.cancel();
    }
}
