package com.firework.client.Implementations.Utill.Render;

import java.awt.Color;

public class RainbowUtil {
    public static final float DEFAULT_COLOR_SATURATION = 0.95f;
    public static final float DEFAULT_COLOR_BRIGHTNESS = 0.95f;

    public static int generateRainbowFadingColor(float offset, boolean drastic) {
        long offset_ = (long)((float)(drastic ? 200000000L : 20000000L) * offset);
        float hue = (float)(System.nanoTime() + offset_) / 4.0E9f % 1.0f;
        return (int)Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 0.95f, 0.95f)), 16);
    }
}
