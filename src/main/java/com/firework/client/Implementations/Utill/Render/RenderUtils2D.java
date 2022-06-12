package com.firework.client.Implementations.Utill.Render;

import com.firework.client.Implementations.Utill.Render.HSLColor;
import com.firework.client.Implementations.Utill.Render.Rectangle;
import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class RenderUtils2D {
    public static Tessellator tessellator = Tessellator.getInstance();
    public static BufferBuilder bufferbuilder = tessellator.getBuffer();

    public static void drawGradientRectVertical(Rectangle rectangle, Color startColor, Color endColor) {
        double zLevel = 0.0;
        float f = (float)startColor.getRed() / 255.0f;
        float f1 = (float)startColor.getGreen() / 255.0f;
        float f2 = (float)startColor.getBlue() / 255.0f;
        float f3 = (float)startColor.getAlpha() / 255.0f;
        float f4 = (float)endColor.getRed() / 255.0f;
        float f5 = (float)endColor.getGreen() / 255.0f;
        float f6 = (float)endColor.getBlue() / 255.0f;
        float f7 = (float)endColor.getAlpha() / 255.0f;
        int x = rectangle.x;
        int y = rectangle.y;
        double w = rectangle.width;
        double h = rectangle.height;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, (double)y + h, zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos((double)x + w, (double)y + h, zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos((double)x + w, y, zLevel).color(f4, f5, f6, f7).endVertex();
        bufferbuilder.pos(x, y, zLevel).color(f4, f5, f6, f7).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawGradientRectHorizontal(Rectangle rectangle, Color startColor, Color endColor) {
        double zLevel = 0.0;
        float f = (float)startColor.getRed() / 255.0f;
        float f1 = (float)startColor.getGreen() / 255.0f;
        float f2 = (float)startColor.getBlue() / 255.0f;
        float f3 = (float)startColor.getAlpha() / 255.0f;
        float f4 = (float)endColor.getRed() / 255.0f;
        float f5 = (float)endColor.getGreen() / 255.0f;
        float f6 = (float)endColor.getBlue() / 255.0f;
        float f7 = (float)endColor.getAlpha() / 255.0f;
        int x = rectangle.x;
        int y = rectangle.y;
        double w = rectangle.width;
        double h = rectangle.height;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, (double)y + h, zLevel).color(f4, f5, f6, f7).endVertex();
        bufferbuilder.pos((double)x + w, (double)y + h, zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos((double)x + w, y, zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(x, y, zLevel).color(f4, f5, f6, f7).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawRectangle(Rectangle rectangle, Color color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.glLineWidth((float)1.0f);
        int x = rectangle.x;
        int y = rectangle.y;
        double w = rectangle.width;
        double h = rectangle.height;
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, (double)y + h, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos((double)x + w, (double)rectangle.y + h, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos((double)x + w, rectangle.y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x, rectangle.y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawRectangleOutline(Rectangle rectangle, float width, Color color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.glLineWidth((float)width);
        int x = rectangle.x;
        int y = rectangle.y;
        double w = rectangle.width;
        double h = rectangle.height;
        bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, (double)y + h, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos((double)x + w, (double)rectangle.y + h, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos((double)x + w, rectangle.y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x, y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawGradientTriangle(ArrayList<Point> points, ArrayList<Color> colors) {
        double zLevel = 0.0;
        float f = (float)colors.get(0).getRed() / 255.0f;
        float f1 = (float)colors.get(0).getGreen() / 255.0f;
        float f2 = (float)colors.get(0).getBlue() / 255.0f;
        float f3 = (float)colors.get(0).getAlpha() / 255.0f;
        float f4 = (float)colors.get(1).getRed() / 255.0f;
        float f5 = (float)colors.get(1).getGreen() / 255.0f;
        float f6 = (float)colors.get(1).getBlue() / 255.0f;
        float f7 = (float)colors.get(1).getAlpha() / 255.0f;
        float f8 = (float)colors.get(2).getRed() / 255.0f;
        float f9 = (float)colors.get(2).getGreen() / 255.0f;
        float f10 = (float)colors.get(2).getBlue() / 255.0f;
        float f11 = (float)colors.get(2).getAlpha() / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.shadeModel((int)7425);
        bufferbuilder.begin(4, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(points.get((int)0).x, points.get((int)0).y, zLevel).color(f, f1, f2, f3).endVertex();
        bufferbuilder.pos(points.get((int)1).x, points.get((int)1).y, zLevel).color(f4, f5, f6, f7).endVertex();
        bufferbuilder.pos(points.get((int)2).x, points.get((int)2).y, zLevel).color(f8, f9, f10, f11).endVertex();
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawCircleOutline(Point o, float radius, float width, Color color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.glLineWidth((float)width);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
        for (int a = 0; a < 360; ++a) {
            double x1 = (double)o.x + (double)radius * Math.sin(Math.toRadians(a));
            double z1 = (double)o.y + (double)radius * Math.cos(Math.toRadians(a));
            bufferbuilder.pos(x1, z1, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        }
        tessellator.draw();
        GlStateManager.shadeModel((int)7424);
        GL11.glDisable(2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawFilledCircle(Point point, Color color, int r) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(point.getX(), point.getY(), 0.0).color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), Color.white.getAlpha()).endVertex();
        for (int i = 0; i <= 360; ++i) {
            double x2 = Math.sin((double)i * Math.PI / 180.0) * (double)r;
            double y2 = Math.cos((double)i * Math.PI / 180.0) * (double)r;
            bufferbuilder.pos(point.getX() + x2, point.getY() + y2, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        }
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawColorPickerBase(Point point, HSLColor hslColor, int r) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(point.getX(), point.getY(), 0.0).color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), Color.white.getAlpha()).endVertex();
        for (int i = 0; i <= 360; ++i) {
            double x2 = Math.sin((double)i * Math.PI / 180.0) * (double)r;
            double y2 = Math.cos((double)i * Math.PI / 180.0) * (double)r;
            HSLColor color = new HSLColor(i + 1, hslColor.saturation, hslColor.light);
            Color colorRGB = color.toRGB();
            bufferbuilder.pos(point.getX() + x2, point.getY() + y2, 0.0).color(colorRGB.getRed(), colorRGB.getGreen(), colorRGB.getBlue(), colorRGB.getAlpha()).endVertex();
        }
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawColorPickerBaseV2(Point point, HSLColor hslColor, int r) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(point.getX(), point.getY(), 0.0).color(Color.white.getRed(), Color.white.getGreen(), Color.white.getBlue(), Color.white.getAlpha()).endVertex();
        for (int i = 0; i <= 360; ++i) {
            double x = Math.sin((double)i * Math.PI / 180.0) * (double)r;
            double y = Math.cos((double)i * Math.PI / 180.0) * (double)r;
            double x2 = Math.sin((double)i * Math.PI / 180.0) * (double)(r - 5);
            double y2 = Math.cos((double)i * Math.PI / 180.0) * (double)(r - 5);
            HSLColor color = new HSLColor(i + 1, hslColor.saturation, hslColor.light);
            Color colorRGB = color.toRGB();
            bufferbuilder.pos(point.getX() + x, point.getY() + y, 0.0).color(colorRGB.getRed(), colorRGB.getGreen(), colorRGB.getBlue(), colorRGB.getAlpha()).endVertex();
            bufferbuilder.pos(point.getX() + x2, point.getY() + y2, 0.0).color(colorRGB.getRed(), colorRGB.getGreen(), colorRGB.getBlue(), colorRGB.getAlpha()).endVertex();
        }
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }

    public static void drawMarker(Rectangle rectangle, Color color) {
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.glLineWidth((float)1.0f);
        int x = rectangle.x;
        int y = rectangle.y;
        double w = rectangle.width;
        double h = rectangle.height;
        bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
        bufferbuilder.pos(x, y, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x - 2, y + 3, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x + 2, y + 3, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x - 2, y + 12, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        bufferbuilder.pos(x + 2, y + 12, 0.0).color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }

    public static void drawCompleteImage(float posX, float posY, int width, int height) {
        GL11.glPushMatrix();
        GL11.glTranslatef(posX, posY, 0.0f);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0f, 0.0f);
        GL11.glVertex3f(0.0f, 0.0f, 0.0f);
        GL11.glTexCoord2f(0.0f, 1.0f);
        GL11.glVertex3f(0.0f, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 1.0f);
        GL11.glVertex3f(width, height, 0.0f);
        GL11.glTexCoord2f(1.0f, 0.0f);
        GL11.glVertex3f(width, 0.0f, 0.0f);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public static void drawCheckMark(float x, float y, int width, int color) {
        float f = (float)(color >> 24 & 0xFF) / 255.0f;
        float f1 = (float)(color >> 16 & 0xFF) / 255.0f;
        float f2 = (float)(color >> 8 & 0xFF) / 255.0f;
        float f3 = (float)(color & 0xFF) / 255.0f;
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(4.0f);
        GL11.glBegin(3);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        GL11.glVertex2d((double)(x + (float)width) - 6.25, y + 2.75f);
        GL11.glVertex2d((double)(x + (float)width) - 11.5, y + 10.25f);
        GL11.glVertex2d(x + (float)width - 13.75f, y + 7.75f);
        GL11.glEnd();
        GL11.glLineWidth(1.5f);
        GL11.glBegin(3);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glVertex2d((double)(x + (float)width) - 6.5, y + 3.0f);
        GL11.glVertex2d((double)(x + (float)width) - 11.5, y + 10.0f);
        GL11.glVertex2d((double)(x + (float)width) - 13.5, y + 8.0f);
        GL11.glEnd();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    public static double distance(Point one, Point two) {
        double ac = Math.abs(two.getY() - one.getY());
        double cb = Math.abs(two.getX() - one.getX());
        return Math.sqrt(ac * ac + cb * cb);
    }
}
