package com.firework.client.Implementations.Gui.Components.Advanced;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Gui.GuiValueStorage;
import com.firework.client.Implementations.Utill.Render.ColorUtils;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;
import java.util.ArrayList;

public class SubModule
extends Button {
    public ArrayList<Module> modules;
    public String name;
    public Color first = ColorUtils.randomColor();
    public Color second = ColorUtils.randomColor();
    public ArrayList<Boolean> valuesB = new ArrayList();

    public SubModule(ArrayList<Module> modules, String name, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.modules = modules;
        this.name = name;
        this.valuesB.set(0, false);
    }

    @Override
    public void draw() {
        super.draw();
        int outlineWidth = 3;
        int textWidth = Firework.textManager.getStringWidth(this.name);
        RenderUtils2D.drawGradientRectHorizontal(new Rectangle(this.x, this.y, this.width, this.height), this.first, this.second);
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), outlineWidth, GuiInfo.outlineColorA);
        Firework.textManager.drawString(this.name, this.x + 3, this.y + 1, new Color(ColorUtils.astolfoColors(100, 100)).getRGB(), false);
    }

    @Override
    public void initialize(int mouseX, int mouseY, int state) {
        super.initialize(mouseX, mouseY, state);
        if (state == 1) {
            this.valuesB.set(0, this.valuesB.get(0) == false);
            GuiValueStorage.values[this.localIndex] = this.valuesB;
        }
    }
}
