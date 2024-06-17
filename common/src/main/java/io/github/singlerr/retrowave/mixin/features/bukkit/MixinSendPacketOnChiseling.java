package io.github.singlerr.retrowave.mixin.features.bukkit;

import mod.chiselsandbits.ChiselsAndBits;
import mod.chiselsandbits.block.entities.ChiseledBlockEntity;
import mod.chiselsandbits.network.packets.UpdateChiseledBlockPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChiseledBlockEntity.class)
public abstract class MixinSendPacketOnChiseling {

    @Shadow @NotNull public abstract BlockPos getBlockPos();

    @Inject(method = "setChanged", at = @At(value = "INVOKE", target = "Lmod/chiselsandbits/voxelshape/SingleBlockVoxelShapeCache;reset()V", shift = At.Shift.AFTER))
    private void retrowave$sendUpdatePacketToServer(CallbackInfo ci){
        ChiselsAndBits.getInstance().getNetworkChannel().sendToServer(new UpdateChiseledBlockPacket((ChiseledBlockEntity)(Object)this));
    }
}
