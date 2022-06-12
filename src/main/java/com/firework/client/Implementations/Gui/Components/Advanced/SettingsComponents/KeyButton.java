package com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents;

import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.Gui;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import org.lwjgl.input.Keyboard;

public class KeyButton
extends Button {
    public Color fillColor = GuiInfo.fillColorB;

    public KeyButton(Setting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void draw() {
        super.draw();
        int outlineWidth = 3;
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, this.width, this.height), this.fillColor);
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), outlineWidth, GuiInfo.outlineColorA);
        Firework.textManager.drawString(this.setting.name + ":" + Keyboard.getKeyName((Integer)this.setting.getValue()), this.x + 3, this.y + 1, Color.WHITE.getRGB(), false);
    }

    @Override
    public void initialize(int mouseX, int mouseY) {
        super.initialize(mouseX, mouseY);
        this.fillColor = GuiInfo.fillColorA;
        Minecraft mc = Minecraft.getMinecraft();
        mc.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
    }

    @Override
    public void onKeyTyped(int keyCode) {
        super.onKeyTyped(keyCode);
        if (Gui.keyIsDragging && Gui.activeKeyModule == this.setting.module.name) {
            Gui.keyIsDragging = false;
            this.fillColor = GuiInfo.fillColorB;
            this.setting.setValue(keyCode);
            Gui.activeKeyModule = "";
        }
    }
}
