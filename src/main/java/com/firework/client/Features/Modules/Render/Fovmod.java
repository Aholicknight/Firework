package com.firework.client.Features.Modules.Render;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import java.util.Arrays;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@ModuleArgs(name="FovMod", category=Module.Category.RENDER)
public class Fovmod
extends Module {
    public float defaultFov;
    public Setting<Double> Change = new Setting<Double>("Change ", 100.0, this, 0.0, 500.0);
    public Setting<Boolean> Smooth = new Setting<Boolean>("Smooth ", false, this);
    public Setting<String> FovMode = new Setting<String>("FovMode", "ViewModelChanger", (Module)this, Arrays.asList("ViewModelChanger", "FovChanger", "Zoom"));

    @SubscribeEvent
    public void FOVModifier(EntityViewRenderEvent.FOVModifier event) {
        if (this.FovMode.getValue().equals("ViewModelChanger")) {
            event.setFOV(this.Change.getValue().floatValue());
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.defaultFov = this.mc.gameSettings.fovSetting;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.gameSettings.fovSetting = this.defaultFov;
        this.mc.gameSettings.smoothCamera = false;
    }

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        block4: {
            block5: {
                this.mc.gameSettings.smoothCamera = this.Smooth.getValue();
                if (this.FovMode.getValue().equals("FovChanger")) {
                    this.mc.gameSettings.fovSetting = this.Change.getValue().floatValue();
                }
                if (!this.FovMode.getValue().equals("Zoom")) break block4;
                if (!(this.mc.gameSettings.fovSetting > 12.0f)) break block5;
                int i = 0;
                while ((double)i < this.Change.getValue()) {
                    if (this.mc.gameSettings.fovSetting > 12.0f) {
                        this.mc.gameSettings.fovSetting -= 0.1f;
                    }
                    ++i;
                }
                break block4;
            }
            if (!(this.mc.gameSettings.fovSetting < this.defaultFov)) break block4;
            int i = 0;
            while ((double)i < this.Change.getValue()) {
                this.mc.gameSettings.fovSetting += 0.1f;
                ++i;
            }
        }
    }
}
