package com.firework.client.Implementations.Utill.Render;

import java.awt.Color;

public class HSLColor {
    public float hue;
    public float saturation;
    public float light;

    public HSLColor(float hue, float saturation, float light) {
        this.hue = hue;
        this.saturation = saturation;
        this.light = light;
    }

    public Color toRGB() {
        boolean alpha = true;
        float hue = this.hue;
        float saturation = this.saturation;
        float light = this.light;
        if (saturation < 0.0f || saturation > 100.0f) {
            String message = "Color parameter outside of expected range - Saturation";
            throw new IllegalArgumentException(message);
        }
        if (light < 0.0f || light > 100.0f) {
            String message = "Color parameter outside of expected range - Luminance";
            throw new IllegalArgumentException(message);
        }
        hue %= 360.0f;
        float q = 0.0f;
        q = (double)light < 0.5 ? light * (1.0f + saturation) : (light /= 100.0f) + (saturation /= 100.0f) - saturation * light;
        float p = 2.0f * light - q;
        float r = Math.max(0.0f, HSLColor.HueToRGB(p, q, (hue /= 360.0f) + 0.33333334f));
        float g = Math.max(0.0f, HSLColor.HueToRGB(p, q, hue));
        float b = Math.max(0.0f, HSLColor.HueToRGB(p, q, hue - 0.33333334f));
        r = Math.min(r, 1.0f);
        g = Math.min(g, 1.0f);
        b = Math.min(b, 1.0f);
        return new Color(r, g, b, 1);
    }

    private static float HueToRGB(float p, float q, float h) {
        if (h < 0.0f) {
            h += 1.0f;
        }
        if (h > 1.0f) {
            h -= 1.0f;
        }
        if (6.0f * h < 1.0f) {
            return p + (q - p) * 6.0f * h;
        }
        if (2.0f * h < 1.0f) {
            return q;
        }
        if (3.0f * h < 2.0f) {
            return p + (q - p) * 6.0f * (0.6666667f - h);
        }
        return p;
    }
}
