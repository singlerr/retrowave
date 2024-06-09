package io.github.singlerr.retrowave.neoforge;

import io.github.singlerr.retrowave.Retrowave;
import net.neoforged.fml.common.Mod;

@Mod(Retrowave.MOD_ID)
public final class ExampleModNeoForge {
    public ExampleModNeoForge() {
        // Run our common setup.
        Retrowave.init();
    }
}
