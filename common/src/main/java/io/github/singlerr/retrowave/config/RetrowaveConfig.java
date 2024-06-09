package io.github.singlerr.retrowave.config;

import io.github.singlerr.retrowave.Retrowave;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = Retrowave.MOD_ID)
public class RetrowaveConfig implements ConfigData {

    private boolean replaceMode = false;

    public boolean isReplaceMode() {
        return replaceMode;
    }

    public void setReplaceMode(boolean replaceMode) {
        this.replaceMode = replaceMode;
    }
}
