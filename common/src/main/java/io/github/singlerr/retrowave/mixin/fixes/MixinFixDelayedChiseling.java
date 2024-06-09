package io.github.singlerr.retrowave.mixin.fixes;

import mod.chiselsandbits.client.model.data.ChiseledBlockModelDataExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Mixin(ChiseledBlockModelDataExecutor.class)
public abstract class MixinFixDelayedChiseling {

    @Redirect(method = "updateModelDataCore", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;supplyAsync(Ljava/util/function/Supplier;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;"), remap = false)
    private static CompletableFuture retrowave$forceSync$0(Supplier supplier, Executor executor){
        Object model = supplier.get();
        return CompletableFuture.completedFuture(model);
    }

    @Redirect(method = "updateModelDataCore", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;thenAcceptAsync(Ljava/util/function/Consumer;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", ordinal = 0), remap = false)
    private static CompletableFuture retrowave$forceSync$1(CompletableFuture instance, Consumer action, Executor executor){
        return instance.thenAccept(action);
    }
    @Redirect(method = "updateModelDataCore", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;thenRunAsync(Ljava/lang/Runnable;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", ordinal = 0), remap = false)
    private static CompletableFuture retrowave$forceSync$2(CompletableFuture instance, Runnable action, Executor executor){

        return instance.thenRun(action);
    }
    @Redirect(method = "updateModelDataCore", at = @At(value = "INVOKE", target = "Ljava/util/concurrent/CompletableFuture;thenRunAsync(Ljava/lang/Runnable;Ljava/util/concurrent/Executor;)Ljava/util/concurrent/CompletableFuture;", ordinal = 1), remap = false)
    private static CompletableFuture retrowave$forceSync$3(CompletableFuture instance, Runnable action, Executor executor){
        return instance.thenRun(action);
    }

}
