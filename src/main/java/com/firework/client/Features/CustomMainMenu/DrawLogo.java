package com.firework.client.Features.CustomMainMenu;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class DrawLogo {
    public static void drawString(double scale, String text, float x, float y, int color) {
        GlStateManager.pushMatrix();
        GlStateManager.scale((double)scale, (double)scale, (double)scale);
        Minecraft.getMinecraft().fontRenderer.drawStringWithShadow(text, x, y, color);
        GlStateManager.popMatrix();
    }
}
