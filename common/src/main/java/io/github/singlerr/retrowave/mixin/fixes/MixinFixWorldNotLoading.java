package io.github.singlerr.retrowave.mixin.fixes;

import com.communi.suggestu.scena.core.entity.block.IBlockEntityPositionManager;
import mod.chiselsandbits.block.entities.ChiseledBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ChiseledBlockEntity.class)
public abstract class MixinFixWorldNotLoading {


    @Redirect(method = "setLevel", at = @At(value = "INVOKE", target = "Lcom/communi/suggestu/scena/core/entity/block/IBlockEntityPositionManager;add(Lnet/minecraft/world/level/block/entity/BlockEntity;)V", remap = false))
    private void retrowave$removeCall(IBlockEntityPositionManager instance, BlockEntity blockEntity){

    }
}
