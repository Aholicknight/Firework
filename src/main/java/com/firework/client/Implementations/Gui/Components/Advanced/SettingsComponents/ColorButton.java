package com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents;

import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;
import java.awt.Point;

public class ColorButton
extends Button {
    public ColorButton(Setting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
        this.originOffset = setting.opened ? 71 : 11;
        this.offset = setting.opened ? 71 : 11;
        this.originHeight = setting.opened ? 73 : 11;
        this.height = setting.opened ? 73 : 11;
    }

    public void drawBase() {
        int outlineWidth = 3;
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, this.width, this.height), GuiInfo.fillColorB);
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), outlineWidth, GuiInfo.outlineColorA);
        RenderUtils2D.drawRectangle(new Rectangle(this.x + this.width - 11, this.y + 2, 7.0, 7.0), ((HSLColor)this.setting.getValue()).toRGB());
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x + this.width - 11, this.y + 2, 7.0, 7.0), outlineWidth, GuiInfo.outlineColorC);
        Firework.textManager.drawString(this.setting.name, this.x + 3, this.y + 1, Color.WHITE.getRGB(), false);
    }

    @Override
    public void draw() {
        int radius = 24;
        this.drawBase();
        if (this.setting.opened) {
            RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y + 11, this.width, this.height), GuiInfo.fillColorB);
            RenderUtils2D.drawColorPickerBaseV2(new Point(this.x + this.width / 2, this.y + 9 + this.height / 2), (HSLColor)this.setting.getValue(), radius);
            Point center = new Point(this.x + this.width / 2, this.y + 11 + this.height / 2);
            Point p = this.hueToPosition(center, radius, (int)((HSLColor)this.setting.getValue()).hue);
            RenderUtils2D.drawFilledCircle(p, ((HSLColor)this.setting.getValue()).toRGB(), 3);
            RenderUtils2D.drawCircleOutline(p, 3.0f, 2.0f, Color.white);
        }
    }

    @Override
    public void initialize(int mouseX, int mouseY, int state) {
        super.initialize(mouseX, mouseY, state);
        if (state == 1) {
            this.setting.opened = !this.setting.opened;
            Firework.settingManager.updateSettingsByName(this.setting);
            this.originOffset = this.setting.opened ? 66 : 11;
            int n = this.originHeight = this.setting.opened ? 66 : 11;
        }
        if (state == 0 && this.setting.opened) {
            double d = 28.0;
        }
    }

    public Point hueToPosition(Point center, int r, int hue) {
        double x = Math.sin((double)hue * Math.PI / 180.0) * (double)r;
        double y = Math.cos((double)hue * Math.PI / 180.0) * (double)r;
        return new Point((int)(center.getX() + x), (int)(center.getY() + y));
    }
}
