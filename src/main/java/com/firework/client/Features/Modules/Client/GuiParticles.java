package com.firework.client.Features.Modules.Client;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Gui.Particles.ParticleInfo;
import com.firework.client.Implementations.Settings.Setting;

@ModuleArgs(name="GuiParticles", category=Module.Category.CLIENT)
public class GuiParticles
extends Module {
    public static Setting<Double> scaleFactor;
    public static Setting<Double> lineLong;

    public GuiParticles() {
        scaleFactor = new Setting<Double>("scaleFactor", 0.4, this, 0.1, 10.0);
        lineLong = new Setting<Double>("maxLineLong", 90.0, this, 0.0, 200.0);
        this.isEnabled.setValue(true);
        ParticleInfo.isEnabled = (Boolean)this.isEnabled.getValue();
    }

    @Override
    public void onToggle() {
        super.onToggle();
        ParticleInfo.isEnabled = (Boolean)this.isEnabled.getValue();
    }
}
