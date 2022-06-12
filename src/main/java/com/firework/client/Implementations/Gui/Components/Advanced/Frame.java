package com.firework.client.Implementations.Gui.Components.Advanced;

import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;

public class Frame
extends Button {
    public Frame(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw() {
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), 6.0f, GuiInfo.outlineFrameColorA);
        RenderUtils2D.drawRectangleOutline(new Rectangle(this.x, this.y, this.width, this.height), 2.0f, GuiInfo.outlineFrameColorB);
        super.draw();
    }
}
