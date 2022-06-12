package com.firework.client.Implementations.Gui.Components.Advanced;

import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Utill.Render.ColorUtils;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;

public class EndBlock
extends Button {
    public EndBlock(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw() {
        super.draw();
        RenderUtils2D.drawRectangle(new Rectangle(this.x, this.y, this.width, this.height), new Color(ColorUtils.astolfoColors(100, 100)));
    }
}
