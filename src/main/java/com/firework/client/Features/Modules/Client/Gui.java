package com.firework.client.Features.Modules.Client;

import com.firework.client.Features.Modules.Client.Notifications;
import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import net.minecraft.client.Minecraft;

public class Gui
extends Module {
    public static Setting<Double> scrollSpeed;
    public static Setting<Boolean> background;
    public static Setting<HSLColor> upStartBlockColor;
    public static Setting<HSLColor> downStartBlockColor;

    public Gui() {
        super("GuiModule", Module.Category.CLIENT);
        this.key.setValue(54);
        scrollSpeed = new Setting<Double>("ScrollSpeed", 3.0, this, 0.1, 10.0);
        background = new Setting<Boolean>("Background", false, this);
        upStartBlockColor = new Setting<HSLColor>("upStartBlockColor", new HSLColor(175.0f, 50.0f, 50.0f), this);
        downStartBlockColor = new Setting<HSLColor>("downStartBlockColor", new HSLColor(334.0f, 74.0f, 77.0f), this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        Notifications.notificate();
        Minecraft.getMinecraft().player.closeScreen();
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    Minecraft.getMinecraft().displayGuiScreen(new com.firework.client.Implementations.Gui.Gui());
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        this.isEnabled.setValue(false);
    }
}
