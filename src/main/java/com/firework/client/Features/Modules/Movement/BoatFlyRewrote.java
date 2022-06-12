package com.firework.client.Features.Modules.Movement;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Events.PacketEvent;
import com.firework.client.Implementations.Settings.Setting;
import java.util.Comparator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.network.play.client.CPacketInput;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleArgs(name="BoatFly", category=Module.Category.MOVEMENT)
public class BoatFlyRewrote
extends Module {
    private EntityBoat target;
    private int teleportID;
    private int packetCounter = 0;
    private boolean bebra = false;
    public Setting<Boolean> fixYaw = new Setting<Boolean>("FixYaw", true, this);
    public Setting<Boolean> noKick = new Setting<Boolean>("AntiKick", true, this);
    public Setting<Double> speed = new Setting<Double>("Speed", 3.0, this, 1.0, 10.0);
    public Setting<Double> verticalSpeed = new Setting<Double>("VSpeed", 3.0, this, 1.0, 10.0);
    public Setting<Double> interact = new Setting<Double>("interact", 3.0, this, 1.0, 10.0);
    public Setting<Double> scale = new Setting<Double>("BoatScale", 3.0, this, 1.0, 10.0);

    @Override
    public void onTick() {
        super.onTick();
        if (this.fixYaw.getValue().booleanValue()) {
            EntityBoat playerBoat = (EntityBoat)((Object)this.mc.player.getRidingEntity());
            playerBoat.rotationYaw = this.mc.player.rotationYaw;
        }
        if (this.mc.player == null) {
            return;
        }
        if (this.mc.world == null || this.mc.player.getRidingEntity() == null) {
            return;
        }
        if (this.mc.player.getRidingEntity() instanceof EntityBoat) {
            this.target = (EntityBoat)((Object)this.mc.player.getRidingEntity());
        }
        this.mc.player.getRidingEntity().setNoGravity(true);
        this.mc.player.getRidingEntity().motionY = 0.0;
        if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
            this.mc.player.getRidingEntity().onGround = false;
            this.mc.player.getRidingEntity().motionY = this.verticalSpeed.getValue() / 10.0;
        }
        if (this.mc.gameSettings.keyBindSprint.isKeyDown()) {
            this.mc.player.getRidingEntity().onGround = false;
            this.mc.player.getRidingEntity().motionY = -(this.verticalSpeed.getValue() / 10.0);
        }
        double[] normalDir = this.directionSpeed(this.speed.getValue() / 2.0);
        if (this.mc.player.movementInput.moveStrafe != 0.0f || this.mc.player.movementInput.moveForward != 0.0f) {
            this.mc.player.getRidingEntity().motionX = normalDir[0];
            this.mc.player.getRidingEntity().motionZ = normalDir[1];
        } else {
            this.mc.player.getRidingEntity().motionX = 0.0;
            this.mc.player.getRidingEntity().motionZ = 0.0;
        }
        if (this.noKick.getValue().booleanValue()) {
            if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                if (this.mc.player.ticksExisted % 8 < 2) {
                    this.mc.player.getRidingEntity().motionY = -0.04f;
                }
            } else if (this.mc.player.ticksExisted % 8 < 4) {
                this.mc.player.getRidingEntity().motionY = -0.08f;
            }
        }
    }

    private void NCPPacketTrick() {
        this.packetCounter = 0;
        this.mc.player.getRidingEntity().dismountRidingEntity();
        Entity l_Entity = this.mc.world.loadedEntityList.stream().filter(p_Entity -> p_Entity instanceof EntityBoat).min(Comparator.comparing(p_Entity -> Float.valueOf(this.mc.player.getDistance((Entity)p_Entity)))).orElse(null);
        if (l_Entity != null) {
            this.mc.playerController.interactWithEntity(this.mc.player, l_Entity, EnumHand.MAIN_HAND);
        }
    }

    @SubscribeEvent
    public void onSendPacket(PacketEvent.Send event) {
        if (this.mc.world != null && this.mc.player != null && this.mc.player.getRidingEntity() instanceof EntityBoat) {
            if (this.noKick.getValue().booleanValue() && event.getPacket() instanceof CPacketInput && !this.mc.gameSettings.keyBindSneak.isKeyDown() && !this.mc.player.getRidingEntity().onGround) {
                ++this.packetCounter;
                if (this.packetCounter == 3) {
                    this.NCPPacketTrick();
                }
            }
            if (this.noKick.getValue().booleanValue() && event.getPacket() instanceof SPacketPlayerPosLook || event.getPacket() instanceof SPacketMoveVehicle) {
                event.setCanceled(true);
            }
        }
        if (event.getPacket() instanceof CPacketVehicleMove && this.mc.player.isRiding() && (double)this.mc.player.ticksExisted % this.interact.getValue() == 0.0) {
            this.mc.playerController.interactWithEntity(this.mc.player, this.mc.player.getRidingEntity(), EnumHand.OFF_HAND);
        }
        if ((event.getPacket() instanceof CPacketPlayer.Rotation || event.getPacket() instanceof CPacketInput) && this.mc.player.isRiding()) {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onReceivePacket(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketMoveVehicle && this.mc.player.isRiding()) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            this.teleportID = ((SPacketPlayerPosLook)((Object)event.getPacket())).getTeleportId();
        }
    }

    private double[] directionSpeed(double speed) {
        float forward = this.mc.player.movementInput.moveForward;
        float side = this.mc.player.movementInput.moveStrafe;
        float yaw = this.mc.player.prevRotationYaw + (this.mc.player.rotationYaw - this.mc.player.prevRotationYaw) * this.mc.getRenderPartialTicks();
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

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.player.getRidingEntity().setNoGravity(false);
    }
}
