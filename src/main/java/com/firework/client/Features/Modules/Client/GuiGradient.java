package com.firework.client.Features.Modules.Client;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.HSLColor;

public class GuiGradient
extends Module {
    public static Setting<Boolean> enabled = null;
    public static Setting<HSLColor> Color1 = null;
    public static Setting<HSLColor> Color2 = null;

    public GuiGradient() {
        super("GuiGradient", Module.Category.CLIENT);
        enabled = this.isEnabled;
        this.isEnabled.setValue(false);
        Color1 = new Setting<HSLColor>("UpColor", new HSLColor(1.0f, 54.0f, 43.0f), this);
        Color2 = new Setting<HSLColor>("DownColor", new HSLColor(1.0f, 54.0f, 43.0f), this);
    }
}
