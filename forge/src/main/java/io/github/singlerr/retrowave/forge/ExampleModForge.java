package io.github.singlerr.retrowave.forge;

import net.minecraftforge.fml.common.Mod;

import io.github.singlerr.retrowave.Retrowave;

@Mod(Retrowave.MOD_ID)
public final class ExampleModForge {
    public ExampleModForge() {
        // Run our common setup.
        Retrowave.init();
    }
}
