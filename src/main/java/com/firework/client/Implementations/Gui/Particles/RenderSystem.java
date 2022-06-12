package com.firework.client.Implementations.Gui.Particles;

import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import java.awt.Color;
import java.awt.Point;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class RenderSystem {
    public static void drawGradientLine(Point one, Point two, Color startColor, Color endColor) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth((float)5.0f);
        GlStateManager.shadeModel((int)7425);
        RenderUtils2D.bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
        RenderUtils2D.bufferbuilder.pos(one.getX(), one.getY(), 0.0).color(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), startColor.getAlpha()).endVertex();
        RenderUtils2D.bufferbuilder.pos(two.getX(), two.getY(), 0.0).color(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), endColor.getAlpha()).endVertex();
        RenderUtils2D.tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
}
