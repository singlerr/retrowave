package io.github.singlerr.retrowave.fabric.mixin.fixes;

import com.communi.suggestu.scena.fabric.platform.entity.IFabricBlockEntityPositionHolder;
import mod.chiselsandbits.block.entities.ChiseledBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(LevelChunk.class)
public abstract class MixinFixWorldNotLoading {

    @Inject(method = "setBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;setLevel(Lnet/minecraft/world/level/Level;)V"))
    private void retrowave$scena$add(BlockEntity blockEntity, CallbackInfo ci){
        if(blockEntity instanceof ChiseledBlockEntity){
            IFabricBlockEntityPositionHolder holder = (IFabricBlockEntityPositionHolder) this;
            holder.scena$add(blockEntity.getClass(), blockEntity.getBlockPos());
        }
    }

//    @Inject(method = "removeBlockEntity", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/BlockEntity;setRemoved()V"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
//    private void retrowave$scena$remove(BlockPos blockPos, CallbackInfo ci, BlockEntity blockEntity, ServerLevel serverLevel, Level var4){
//        if(blockEntity instanceof ChiseledBlockEntity){
//            IFabricBlockEntityPositionHolder holder = (IFabricBlockEntityPositionHolder) this;
//            holder.scena$remove(blockEntity.getClass(), blockPos);
//        }
//    }


}
