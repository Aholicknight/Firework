package com.firework.client.Features.Modules.Movement;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Wrapper;
import java.util.Arrays;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@ModuleArgs(name="Fly", category=Module.Category.MOVEMENT)
public class Fly
extends Module {
    public Setting<String> mode = new Setting<String>("Mode", "Vanilla", (Module)this, Arrays.asList("Vanilla", "Motion", "Tp", "Servers"));
    public Setting<Double> speed = new Setting<Double>("Speed ", 3.0, this, 1.0, 10.0);

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        if (this.mode.getValue().equals("Clicktp") && this.mc.gameSettings.keyBindAttack.isPressed()) {
            this.mc.player.capabilities.isFlying = true;
            double yaw = this.mc.player.rotationYaw;
            float increment = 8.5f;
            this.mc.player.setPosition(this.mc.player.posX + Math.sin(Math.toRadians(-yaw)) * (double)increment, this.mc.player.posY, this.mc.player.posZ + Math.cos(Math.toRadians(-yaw)) * (double)increment);
        }
        if (this.mode.getValue().equals("Tp")) {
            EntityPlayerSP player = this.mc.player;
            player.capabilities.isFlying = false;
            float speedScaled = (float)(this.speed.getValue() * 0.5);
            double[] directionSpeedVanilla = Fly.directionSpeed(speedScaled);
            if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                player.setPosition(player.posX + directionSpeedVanilla[0], player.posY, player.posZ + directionSpeedVanilla[1]);
            }
            player.motionX = 0.0;
            player.motionZ = 0.0;
            player.motionY = 0.0;
            player.setVelocity(0.0, 0.0, 0.0);
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                player.setPosition(player.posX, player.posY + this.speed.getValue(), player.posZ);
                player.motionY = 0.0;
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown() && !player.onGround) {
                player.setPosition(player.posX, player.posY - this.speed.getValue(), player.posZ);
            }
        }
        if (this.mode.getValue().equals("Motion")) {
            float speedScaled = (float)(this.speed.getValue() * (double)0.6f);
            double[] directionSpeedVanilla = Fly.directionSpeed(speedScaled);
            if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
                this.mc.player.motionX = directionSpeedVanilla[0];
                this.mc.player.motionZ = directionSpeedVanilla[1];
            }
            this.mc.player.motionY = 0.0;
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                this.mc.player.motionY = this.speed.getValue();
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown() && !this.mc.player.onGround) {
                this.mc.player.motionY = -this.speed.getValue().doubleValue();
            }
        }
        if (this.mode.getValue().equals("Vanilla")) {
            EntityPlayerSP player = this.mc.player;
            player.capabilities.isFlying = false;
            player.motionX = 0.0;
            player.motionY = 0.0;
            player.motionZ = 0.0;
            player.jumpMovementFactor = this.speed.getValue().floatValue();
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                player.motionY += this.speed.getValue().doubleValue();
            }
            if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                player.motionY -= this.speed.getValue().doubleValue();
            }
        }
        if (this.mode.getValue().equals("Servers")) {
            this.mc.player.motionY = 0.0;
            if (this.mc.player.ticksExisted % 3 == 0) {
                double d = this.mc.player.posY - 1.0E-10;
            }
            double y1 = this.mc.player.posY + 1.0E-10;
            this.mc.player.setPosition(this.mc.player.posX, y1, this.mc.player.posZ);
        }
    }

    public void utils(float speed) {
        this.mc.player.motionX = -(Math.sin(this.aan()) * (double)speed);
        this.mc.player.motionZ = Math.cos(this.aan()) * (double)speed;
    }

    public float aan() {
        float var1 = this.mc.player.rotationYaw;
        if (this.mc.player.moveForward < 0.0f) {
            var1 += 180.0f;
        }
        float forward = 1.0f;
        if (this.mc.player.moveForward < 0.0f) {
            forward = -0.5f;
        } else if (this.mc.player.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (this.mc.player.moveStrafing > 0.0f) {
            var1 -= 90.0f * forward;
        }
        if (this.mc.player.moveStrafing < 0.0f) {
            var1 += 90.0f * forward;
        }
        return var1 *= (float)Math.PI / 180;
    }

    private void Movement(float forward, float strafe, EntityPlayerSP player, float var5, float var6) {
        this.mc.player.motionX = player.posX * 5.01E-8;
        double speed = 2.7999100260353087;
        this.mc.player.motionZ = player.posZ * 5.01E-8;
        this.mc.player.motionZ = player.posZ * 5.01E-8;
        this.Runmove(player);
        this.Runmove(player);
        this.mc.player.motionX = player.posX * 5.01E-8;
        double motionX = (double)(strafe * var6 - forward * var5) * speed;
        double motionZ = (double)(forward * var6 + strafe * var5) * speed;
        this.mc.player.motionX = motionX;
        this.mc.player.motionZ = motionZ;
        if (!this.mc.gameSettings.keyBindJump.isPressed()) {
            this.mc.player.setPosition(this.mc.player.posX, this.mc.player.posY - 0.03948584, this.mc.player.posZ);
        }
    }

    private void Runmove(EntityPlayerSP player) {
        this.mc.player.motionZ = player.posZ * 5.01E-8;
        this.mc.player.motionX = player.posX * 5.01E-8;
        this.mc.player.motionZ = player.posZ * 5.01E-8;
        this.mc.player.motionX = player.posX * 5.01E-8;
        this.mc.player.motionZ = player.posZ * 5.01E-8;
        this.mc.player.motionX = player.posX * 5.01E-8;
        this.mc.player.motionZ = player.posZ * 5.01E-8;
        this.mc.player.motionX = player.posX * 5.01E-8;
        this.mc.player.motionZ = player.posZ * 5.01E-8;
        this.mc.player.motionX = player.posX * 5.01E-8;
    }

    private void SendPacket() {
        this.mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(this.mc.player.posX + this.mc.player.motionX, this.mc.player.posY + (this.mc.gameSettings.keyBindJump.isKeyDown() ? 0.0621 : 0.0) - (this.mc.gameSettings.keyBindSneak.isKeyDown() ? 0.0621 : 0.0), this.mc.player.posZ + this.mc.player.motionZ, this.mc.player.rotationYaw, this.mc.player.rotationPitch, false));
        this.mc.player.connection.sendPacket(new CPacketPlayer.PositionRotation(this.mc.player.posX + this.mc.player.motionX, this.mc.player.posY - 999.0, this.mc.player.posZ + this.mc.player.motionZ, this.mc.player.rotationYaw, this.mc.player.rotationPitch, true));
    }

    public double getPosForSetPosX(double value) {
        double yaw = Math.toRadians(this.mc.player.rotationYaw);
        double x = -Math.sin(yaw) * value;
        return x;
    }

    public double getPosForSetPosZ(double value) {
        double yaw = Math.toRadians(this.mc.player.rotationYaw);
        double z = Math.cos(yaw) * value;
        return z;
    }

    @Override
    public void onDisable() {
        this.mc.player.capabilities.isFlying = false;
        super.onDisable();
    }

    public static double[] directionSpeed(double speed) {
        float forward = Wrapper.INSTANCE.mc().player.movementInput.moveForward;
        float side = Wrapper.INSTANCE.mc().player.movementInput.moveStrafe;
        float yaw = Wrapper.INSTANCE.mc().player.prevRotationYaw + (Wrapper.INSTANCE.mc().player.rotationYaw - Wrapper.INSTANCE.mc().player.prevRotationYaw) * Wrapper.INSTANCE.mc().getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double posX = (double)forward * speed * cos + (double)side * speed * sin;
        double posZ = (double)forward * speed * sin - (double)side * speed * cos;
        return new double[]{posX, posZ};
    }
}
