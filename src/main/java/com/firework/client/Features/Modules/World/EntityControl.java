package com.firework.client.Features.Modules.World;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovementInput;
import net.minecraft.world.chunk.EmptyChunk;

public class EntityControl
extends Module {
    public static EntityControl INSTANCE;
    public Setting<Boolean> saddle = new Setting<Boolean>("Saddle", true, this);
    public Setting<Double> speed = new Setting<Double>("Speed", 3.0, this, 0.5, 10.0);
    public Setting<Boolean> antiStuck = new Setting<Boolean>("AntiStuck", true, this);

    public EntityControl() {
        super("EntityControl", Module.Category.WORLD);
        INSTANCE = this;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (Firework.minecraft.player.getRidingEntity() != null) {
            MovementInput movementInput = Firework.minecraft.player.movementInput;
            double forward = movementInput.moveForward;
            double strafe = movementInput.moveStrafe;
            float yaw = Firework.minecraft.player.rotationYaw;
            if (forward == 0.0 && strafe == 0.0) {
                Firework.minecraft.player.getRidingEntity().motionX = 0.0;
                Firework.minecraft.player.getRidingEntity().motionZ = 0.0;
            } else {
                if (forward != 0.0) {
                    if (strafe > 0.0) {
                        yaw += (float)(forward > 0.0 ? -45 : 45);
                    } else if (strafe < 0.0) {
                        yaw += (float)(forward > 0.0 ? 45 : -45);
                    }
                    strafe = 0.0;
                    if (forward > 0.0) {
                        forward = 1.0;
                    } else if (forward < 0.0) {
                        forward = -1.0;
                    }
                }
                double sin = Math.sin(Math.toRadians(yaw + 90.0f));
                double cos = Math.cos(Math.toRadians(yaw + 90.0f));
                if (this.isBorderingChunk(Firework.minecraft.player.getRidingEntity(), Firework.minecraft.player.getRidingEntity().motionX, Firework.minecraft.player.getRidingEntity().motionZ)) {
                    Firework.minecraft.player.getRidingEntity().motionX = 0.0;
                }
                Firework.minecraft.player.getRidingEntity().motionX = forward * this.speed.getValue() * cos + strafe * this.speed.getValue() * sin;
                Firework.minecraft.player.getRidingEntity().motionZ = forward * this.speed.getValue() * sin - strafe * this.speed.getValue() * cos;
            }
        }
    }

    private boolean isBorderingChunk(Entity entity, double motX, double motZ) {
        return this.antiStuck.getValue() != false && Firework.minecraft.world.getChunk((int)(entity.posX + motX) >> 4, (int)(entity.posZ + motZ) >> 4) instanceof EmptyChunk;
    }
}
