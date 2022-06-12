package com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents;

import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.ColorUtils;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;

public class BoolButton
extends Button {
    public Color activeColor;

    public BoolButton(Setting setting, int x, int y, int width, int height) {
        super(setting, x, y, width, height);
    }

    @Override
    public void draw() {
        super.draw();
        int outlineWidth = 3;
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, this.width, this.height), GuiInfo.fillColorB);
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), outlineWidth, GuiInfo.outlineColorA);
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x + this.width - 11, this.y + 2, 7.0, 7.0), 1.0f, Color.WHITE);
        if (((Boolean)this.setting.getValue()).booleanValue()) {
            this.activeColor = new Color(ColorUtils.astolfoColors(100, 100));
            RenderUtils2D.drawCheckMark(this.x + this.width - 19, this.y - 1, 22, Color.WHITE.getRGB());
        } else {
            this.activeColor = Color.WHITE;
        }
        Firework.textManager.drawString(this.setting.name, this.x + 3, this.y + 1, this.activeColor.getRGB(), false);
    }

    @Override
    public void initialize(int mouseX, int mouseY) {
        super.initialize(mouseX, mouseY);
        this.setting.setValue((Boolean)this.setting.getValue() == false);
        Minecraft mc = Minecraft.getMinecraft();
        mc.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
    }
}
