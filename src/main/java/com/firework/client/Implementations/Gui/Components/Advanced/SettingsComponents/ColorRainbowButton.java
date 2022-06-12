package com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents;

import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Gui.GuiValueStorage;
import com.firework.client.Implementations.Managers.Updater.Updater;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.ColorUtils;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;
import java.util.ArrayList;

public class ColorRainbowButton
extends Button {
    public Color activeColor;
    public Updater updater = new Updater(){

        @Override
        public void run() {
            this.delay = 20;
            this.index = ColorRainbowButton.this.localIndex;
            super.run();
            float saturation = ((HSLColor)ColorRainbowButton.this.setting.getValue()).saturation;
            float light = ((HSLColor)ColorRainbowButton.this.setting.getValue()).light;
            float hue = ((HSLColor)ColorRainbowButton.this.setting.getValue()).hue;
            hue += 1.0f;
            if (hue > 360.0f) {
                hue -= 360.0f;
            }
            ColorRainbowButton.this.setting.setValue(new HSLColor(hue, saturation, light));
        }
    };

    public ColorRainbowButton(Setting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
        this.originOffset = setting.opened ? 13 : 0;
        this.offset = setting.opened ? 13 : 0;
        this.originHeight = setting.opened ? 12 : 0;
        this.height = setting.opened ? 12 : 0;
        try {
            if (GuiValueStorage.values[this.localIndex].get(0) == null) {
                System.out.println(this.localIndex);
            }
        }
        catch (NullPointerException exception) {
            GuiValueStorage.values[this.localIndex] = new ArrayList();
            GuiValueStorage.values[this.localIndex].add(false);
        }
    }

    @Override
    public void draw() {
        if (!this.setting.opened) {
            return;
        }
        super.draw();
        this.activeColor = (Boolean)GuiValueStorage.values[this.localIndex].get(0) != false ? new Color(ColorUtils.astolfoColors(100, 100)) : Color.WHITE;
        int outlineWidth = 3;
        String text = "RAINBOW";
        int textWidth = Firework.textManager.getStringWidth(text);
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, this.width, this.height), GuiInfo.fillColorA);
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), outlineWidth, GuiInfo.outlineColorA);
        Firework.textManager.drawString(text, this.x + 3, this.y + 1, this.activeColor.getRGB(), false);
    }

    @Override
    public void initialize(int mouseX, int mouseY, int state) {
        super.initialize(mouseX, mouseY, state);
        if (state == 0) {
            GuiValueStorage.values[this.localIndex].set(0, (Boolean)GuiValueStorage.values[this.localIndex].get(0) == false);
            if (Firework.updaterManager.containsIndex(this.localIndex)) {
                Firework.updaterManager.removeUpdater(this.localIndex);
            } else {
                Firework.updaterManager.registerUpdater(this.updater);
            }
        }
    }
}
