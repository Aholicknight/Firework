package com.firework.client.Implementations.Gui.Components.Advanced;

import com.firework.client.Features.Modules.Client.Gui;
import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;

public class StartBlock
extends Button {
    public String name;

    public StartBlock(String name, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.name = name;
        this.offset = 15;
    }

    @Override
    public void draw() {
        super.draw();
        int outlineWidth = 3;
        int textWidth = Firework.textManager.getStringWidth(this.name);
        RenderUtils2D.drawGradientRectVertical(new Rectangle(this.x - 2, this.y, this.width + 4, this.height), Gui.downStartBlockColor.getValue().toRGB(), Gui.upStartBlockColor.getValue().toRGB());
        Firework.textManager.drawString(this.name, this.x + 3, this.y + 3, Color.white.getRGB(), true);
    }
}
