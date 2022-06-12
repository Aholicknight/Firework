package com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents;

import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.Gui;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.ColorUtils;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;

public class ColorSliderButton
extends Button {
    public CSliderMode mode;
    public float difference;
    public float percent;

    public ColorSliderButton(Setting setting, int x, int y, int width, int height, CSliderMode mode) {
        super(setting, x, y, width, height);
        this.mode = mode;
        if (mode == CSliderMode.HUE) {
            this.difference = 360.0f;
        } else if (mode == CSliderMode.SATURATION || mode == CSliderMode.LIGHT) {
            this.difference = 100.0f;
        }
        this.originOffset = setting.opened ? 13 : 0;
        this.offset = setting.opened ? 13 : 0;
        this.originHeight = setting.opened ? 12 : 0;
        this.height = setting.opened ? 12 : 0;
    }

    @Override
    public void draw() {
        if (!this.setting.opened) {
            return;
        }
        super.draw();
        int outlineWidth = 3;
        int renderWidth = 2;
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, this.width, this.height), GuiInfo.fillColorB);
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), outlineWidth, GuiInfo.outlineColorA);
        float value = 0.0f;
        if (this.mode == CSliderMode.HUE) {
            value = ((HSLColor)this.setting.getValue()).hue;
        } else if (this.mode == CSliderMode.SATURATION) {
            value = ((HSLColor)this.setting.getValue()).saturation;
            RenderUtils2D.drawGradientRectHorizontal(new Rectangle(this.x, this.y, this.width, this.height), new HSLColor(((HSLColor)this.setting.getValue()).hue, 50.0f, 50.0f).toRGB(), Color.GRAY);
        } else if (this.mode == CSliderMode.LIGHT) {
            value = ((HSLColor)this.setting.getValue()).light;
            RenderUtils2D.drawGradientRectHorizontal(new Rectangle(this.x, this.y, this.width, this.height), new HSLColor(((HSLColor)this.setting.getValue()).hue, 50.0f, 50.0f).toRGB(), Color.BLACK);
        }
        RenderUtils2D.drawMarker(new Rectangle((int)((float)this.x + (float)Math.round((float)this.width * value - 0.0f) / this.difference), this.y, 6.0, this.height), new Color(ColorUtils.astolfoColors(100, 100)));
    }

    public void setSettingFromX(int mouseX) {
        this.percent = ((float)mouseX - (float)this.x) / (float)this.width;
        float result = 0.0f + this.difference * this.percent;
        if (this.mode == CSliderMode.HUE) {
            float saturation = ((HSLColor)this.setting.getValue()).saturation;
            float light = ((HSLColor)this.setting.getValue()).light;
            this.setting.setValue(new HSLColor(result, saturation, light));
        } else if (this.mode == CSliderMode.SATURATION) {
            float hue = ((HSLColor)this.setting.getValue()).hue;
            float light = ((HSLColor)this.setting.getValue()).light;
            this.setting.setValue(new HSLColor(hue, result, light));
        } else if (this.mode == CSliderMode.LIGHT) {
            float hue = ((HSLColor)this.setting.getValue()).hue;
            float saturation = ((HSLColor)this.setting.getValue()).saturation;
            this.setting.setValue(new HSLColor(hue, saturation, result));
        }
    }

    @Override
    public void initialize(int mouseX, int mouseY, int state) {
        super.initialize(mouseX, mouseY, state);
        if (state == 0) {
            if (!this.setting.opened) {
                return;
            }
            this.setSettingFromX(mouseX);
            Gui.isDragging = true;
        }
    }

    public static enum CSliderMode {
        HUE,
        SATURATION,
        LIGHT;

    }
}
