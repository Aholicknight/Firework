package com.firework.client.Implementations.Gui.Particles;

import com.firework.client.Features.Modules.Client.GuiParticles;
import com.firework.client.Implementations.Gui.Particles.Particle;
import com.firework.client.Implementations.Gui.Particles.RenderSystem;
import com.firework.client.Implementations.Utill.Client.Pair;
import com.firework.client.Implementations.Utill.Render.RenderUtils2D;
import com.firework.client.Implementations.Utill.Timer;
import com.firework.client.Implementations.Utill.Util;
import java.util.ArrayList;
import net.minecraft.client.gui.ScaledResolution;

public class ParticleSystem {
    public static Timer timer;
    public static int speed;
    public static double scale;
    public static double lineLong;
    public static int amount;
    public static ArrayList<Particle> particles;
    public static ArrayList<Pair> lines;

    public ParticleSystem() {
        particles.clear();
        timer = new Timer();
        timer.reset();
        for (int i = 0; i < amount; ++i) {
            particles.add(new Particle());
        }
    }

    public void drawParticles() {
        for (Particle particle : particles) {
            RenderUtils2D.drawFilledCircle(particle.location, particle.color, (int)Math.round((double)particle.radius * scale));
        }
    }

    public void drawLines() {
        for (Pair pair : lines) {
            Particle one = (Particle)pair.one;
            Particle two = (Particle)pair.two;
            RenderSystem.drawGradientLine(one.location, two.location, one.color, two.color);
        }
        lines.clear();
        for (Particle particle1 : particles) {
            for (Particle particle2 : particles) {
                Pair pair;
                if (!(RenderUtils2D.distance(particle1.location, particle2.location) <= lineLong) || Pair.containsPair(lines, pair = new Pair(particle1, particle2))) continue;
                lines.add(pair);
            }
        }
    }

    public void updatePositions() {
        scale = GuiParticles.scaleFactor.getValue();
        lineLong = GuiParticles.lineLong.getValue();
        ScaledResolution scaledResolution = new ScaledResolution(Util.mc);
        if (timer.hasPassed(speed)) {
            timer.reset();
            for (Particle particle : particles) {
                int newX = particle.location.x = (int)((float)particle.location.x + particle.dir.x);
                int newY = particle.location.y = (int)((float)particle.location.y + particle.dir.y);
                int radius = (int)Math.round((double)particle.radius * scale);
                if (newX - radius > 0 && newX + radius < scaledResolution.getScaledWidth()) {
                    particle.location.x = newX;
                } else {
                    particle.dir.x = -particle.dir.x;
                }
                if (newY - radius > 0 && newY + radius < scaledResolution.getScaledHeight()) {
                    particle.location.y = newY;
                    continue;
                }
                particle.dir.y = -particle.dir.y;
            }
        }
    }

    static {
        speed = 15;
        scale = 1.0;
        lineLong = 10.0;
        amount = 20;
        particles = new ArrayList();
        lines = new ArrayList();
    }
}
