package com.firework.client.Features.Modules.Render;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SlowAnimations
extends Module {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static Setting<Double> swingDelay = null;
    public static Setting<Boolean> enabled = null;

    public SlowAnimations() {
        super("SlowAnimations", Module.Category.RENDER);
        enabled = this.isEnabled;
        swingDelay = new Setting<Double>("swingDelay", 20.0, this, 1.0, 30.0);
    }

    @SubscribeEvent
    public void onBebra(WorldEvent e) {
    }
}
