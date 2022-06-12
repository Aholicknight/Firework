package com.firework.client.Implementations.Gui.Particles;

import com.firework.client.Implementations.Utill.Client.MathUtil;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import com.firework.client.Implementations.Utill.Util;
import java.awt.Color;
import java.awt.Point;
import java.util.Arrays;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.util.vector.Vector2f;

public class Particle {
    public int radius;
    public Color color;
    public Vector2f dir;
    public Point location;

    public Particle() {
        ScaledResolution scaledResolution = new ScaledResolution(Util.mc);
        this.radius = MathUtil.randomValue(5, 8);
        this.color = new HSLColor(MathUtil.randomValue(180, 360), 100.0f, 50.0f).toRGB();
        this.location = new Point(MathUtil.randomValue(this.radius, scaledResolution.getScaledWidth() - this.radius), MathUtil.randomValue(this.radius, scaledResolution.getScaledHeight() - this.radius));
        this.dir = new Vector2f(MathUtil.getRandomElement(Arrays.asList(-1, -2, 1, 2)), MathUtil.getRandomElement(Arrays.asList(-1, -2, 1, 2)));
    }
}
