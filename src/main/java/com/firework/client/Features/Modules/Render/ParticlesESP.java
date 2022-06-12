package com.firework.client.Features.Modules.Render;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;

public class ParticlesESP
extends Module {
    private static Minecraft mc = Minecraft.getMinecraft();
    public Setting<Boolean> heart = new Setting<Boolean>("Heart", false, this);
    public Setting<Boolean> cloud = new Setting<Boolean>("Cloud", false, this);
    public Setting<Boolean> flame = new Setting<Boolean>("Flame", false, this);
    public Setting<Boolean> smoke = new Setting<Boolean>("Smoke", false, this);
    public Setting<Boolean> redStone = new Setting<Boolean>("RedStone", false, this);
    public Setting<Boolean> firework = new Setting<Boolean>("Firework", false, this);
    public Setting<Boolean> portal = new Setting<Boolean>("Portal", false, this);
    public Setting<Boolean> spit = new Setting<Boolean>("Spit", false, this);
    public Setting<Boolean> slime = new Setting<Boolean>("Slime", false, this);
    public Setting<Boolean> dragonBreath = new Setting<Boolean>("DragonBreath", false, this);
    public Setting<Boolean> endRod = new Setting<Boolean>("EndRod", false, this);
    public Setting<Boolean> totem = new Setting<Boolean>("Totem", false, this);
    public Setting<Boolean> snowBall = new Setting<Boolean>("SnowBall", false, this);

    public ParticlesESP() {
        super("ParticlesESP", Module.Category.RENDER);
    }

    @Override
    public void onTick() {
        if (this.snowBall.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.SNOWBALL, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.totem.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.TOTEM, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.endRod.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.END_ROD, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.dragonBreath.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.DRAGON_BREATH, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.1, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.slime.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.SLIME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.1, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.SLIME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.SLIME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.3, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.SLIME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.4, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.SLIME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.5, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.heart.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.HEART, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.cloud.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.CLOUD, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.flame.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.FLAME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.1, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.FLAME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.FLAME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.3, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.FLAME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.4, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.FLAME, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.5, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.smoke.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.redStone.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.REDSTONE, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.1, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.REDSTONE, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.REDSTONE, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.3, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.REDSTONE, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.4, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.REDSTONE, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.5, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.firework.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.5, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.portal.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.PORTAL, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.PORTAL, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.PORTAL, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.PORTAL, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.PORTAL, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
        if (this.spit.getValue().booleanValue()) {
            ParticlesESP.mc.world.spawnParticle(EnumParticleTypes.SPIT, ParticlesESP.mc.player.posX, ParticlesESP.mc.player.posY + 0.2, ParticlesESP.mc.player.posZ, 0.0, 0.0, 0.0, new int[0]);
        }
    }
}
