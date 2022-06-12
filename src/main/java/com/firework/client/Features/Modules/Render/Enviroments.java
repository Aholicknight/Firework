package com.firework.client.Features.Modules.Render;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Events.PacketEvent;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Enviroments
extends Module {
    public Setting<Double> time = new Setting<Double>("Time", 0.0, this, 0.0, 23000.0);
    public Setting<HSLColor> mainWorld = new Setting<HSLColor>("Sky Color", new HSLColor(1.0f, 54.0f, 43.0f), this);

    public Enviroments() {
        super("Enviroments", Module.Category.RENDER);
    }

    @Override
    public void onTick() {
        this.mc.world.setWorldTime(this.time.getValue().longValue());
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void fogColors(EntityViewRenderEvent.FogColors event) {
        event.setRed((float)this.mainWorld.getValue().toRGB().getRed() / 255.0f);
        event.setGreen((float)this.mainWorld.getValue().toRGB().getGreen() / 255.0f);
        event.setBlue((float)this.mainWorld.getValue().toRGB().getBlue() / 255.0f);
    }

    @SubscribeEvent
    public void fog_density(EntityViewRenderEvent.FogDensity event) {
        event.setDensity(0.0f);
        event.setCanceled(true);
    }
}
