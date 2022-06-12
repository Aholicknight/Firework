package com.firework.client.Implementations.Gui.Components.Advanced;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Utill.Render.ColorUtils;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;

public class ModuleButton
extends Button {
    public Module module;
    public String name;

    public ModuleButton(Module module, String name, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.module = module;
        this.name = name;
    }

    @Override
    public void draw() {
        super.draw();
        int outlineWidth = 3;
        int textWidth = Firework.textManager.getStringWidth(this.name);
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, this.width, this.height), GuiInfo.fillColorA);
        if (this.module.isEnabled.getValue().booleanValue()) {
            RenderUtils2D.drawRectangle(new Rectangle(this.x + this.width - 3, this.y, 3.0, this.height + 1), new Color(ColorUtils.astolfoColors(100, 100)));
            RenderUtils2D.drawRectangleOutline(new Rectangle(this.x + this.width - 3, this.y, 3.0, this.height), 2.0f, GuiInfo.outlineColorB);
        }
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), outlineWidth, GuiInfo.outlineColorA);
        Firework.textManager.drawString(this.name, this.x + 3, this.y + 1, new Color(ColorUtils.astolfoColors(100, 100)).getRGB(), false);
    }

    @Override
    public void initialize(int mouseX, int mouseY) {
        super.initialize(mouseX, mouseY);
        this.module.onToggle();
        Minecraft mc = Minecraft.getMinecraft();
        mc.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
    }
}
