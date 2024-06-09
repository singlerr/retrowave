package io.github.singlerr.retrowave.gui.widgets;

import mod.chiselsandbits.api.client.screen.widget.AbstractChiselsAndBitsButton;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;

public final class ChiselingOperationModeWidget extends AbstractChiselsAndBitsButton {

    public static int WIDTH = 80;
    public static final int HEIGHT = 20;

    public ChiselingOperationModeWidget(int x, int y, Component narration, OnPress pressable, CreateNarration tooltip) {
        super(x, y, WIDTH, HEIGHT, narration, pressable, tooltip);
    }


    public ChiselingOperationModeWidget(int x, int y, Component narration, OnPress pressable) {
        super(x, y, WIDTH, HEIGHT, narration, pressable, Button.DEFAULT_NARRATION);
    }
}
