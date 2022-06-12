package com.firework.client.Features.Modules.Render;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRender
extends Module {
    public Setting<Boolean> antiFog = new Setting<Boolean>("AntiFog", true, this);
    public Setting<Boolean> viewBobbing = new Setting<Boolean>("ViewBobbing", true, this);
    public Setting<Boolean> blockoverlay = new Setting<Boolean>("BlockOverlay", true, this);

    public NoRender() {
        super("NoRender", Module.Category.RENDER);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (this.viewBobbing.getValue().booleanValue()) {
            this.mc.gameSettings.viewBobbing = false;
        }
        if (!this.viewBobbing.getValue().booleanValue()) {
            this.mc.gameSettings.viewBobbing = true;
        }
    }

    @SubscribeEvent
    public void fogDensity(EntityViewRenderEvent.FogDensity event) {
        if (this.antiFog.getValue().booleanValue()) {
            event.setDensity(0.0f);
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void blockoverlay(RenderBlockOverlayEvent event) {
        if (this.blockoverlay.getValue().booleanValue()) {
            event.setCanceled(true);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.gameSettings.viewBobbing = true;
    }
}
