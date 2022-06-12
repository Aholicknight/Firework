package com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents;

import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.ColorUtils;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;

public class SliderButton
extends Button {
    public double difference;
    public float percent;
    public double renderPercent;

    public SliderButton(Setting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
        this.difference = setting.max - setting.min;
    }

    @Override
    public void draw() {
        super.draw();
        int outlineWidth = 3;
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, this.width, this.height), GuiInfo.fillColorB);
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, (double)this.width * ((Double)this.setting.getValue() - this.setting.min) / this.difference, this.height), new Color(ColorUtils.astolfoColors(100, 100)));
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), outlineWidth, GuiInfo.outlineColorA);
        Firework.textManager.drawString(this.setting.name + ":" + this.setting.getValue(), this.x + 3, this.y + 1, Color.WHITE.getRGB(), false);
    }

    public void setSettingFromX(int mouseX) {
        this.percent = ((float)mouseX - (float)this.x) / (float)this.width;
        double result = Double.valueOf(this.setting.min) + (double)((float)this.difference * this.percent);
        this.setting.setValue((double)Math.round(10.0 * result) / 10.0);
    }

    @Override
    public void initialize(int mouseX, int mouseY) {
        super.initialize(mouseX, mouseY);
        this.setSettingFromX(mouseX);
    }
}
