package io.github.singlerr.retrowave.mixin.misc;

import mod.chiselsandbits.item.bit.BitItem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemCooldowns;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BitItem.class)
public abstract class MixinRemoveCooldown {

    @Redirect(method = "handleClickProcessing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemCooldowns;addCooldown(Lnet/minecraft/world/item/Item;I)V"))
    private void retrowave$removeItemCooldown(ItemCooldowns instance, Item item, int tick){
        instance.removeCooldown(item);
    }

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void retrowave$removeChiselCooldown(Player playerEntity, ItemStack stack, CallbackInfoReturnable<Boolean> cir){
        cir.setReturnValue(true);
        cir.cancel();
    }
}
