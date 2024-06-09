package io.github.singlerr.retrowave;

import dev.architectury.utils.Env;
import dev.architectury.utils.EnvExecutor;
import io.github.singlerr.retrowave.config.RetrowaveConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.Toml4jConfigSerializer;
import mod.chiselsandbits.api.util.LocalStrings;
import mod.chiselsandbits.block.ChiseledBlock;


public final class Retrowave {

    public static final String MOD_ID = "retrowave";

    private static ConfigHolder<RetrowaveConfig> config;

    public static void init() {

        // Write common init code here.
        EnvExecutor.runInEnv(Env.CLIENT, () -> () -> {
            AutoConfig.register(RetrowaveConfig.class, Toml4jConfigSerializer::new);
            config = AutoConfig.getConfigHolder(RetrowaveConfig.class);
        });
    }

    public static ConfigHolder<RetrowaveConfig> getConfigHolder() {
        return config;
    }

    public static RetrowaveConfig getConfig() {
        return config.getConfig();
    }
}
