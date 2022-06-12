package com.firework.client.Features.Modules.Movement;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.Movement.AirJump;
import com.firework.client.Implementations.Events.PacketEvent;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import java.util.List;
import java.util.Objects;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.potion.Potion;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MovementHelper
extends Module {
    public static Setting<Boolean> enabled = null;
    public Setting<Boolean> antiLevitate = new Setting<Boolean>("AntiLevitate", true, this);
    public Setting<Boolean> sprint = new Setting<Boolean>("Sprint", true, this);
    public static Setting<Boolean> parkour = null;

    public MovementHelper() {
        super("MovementHelper", Module.Category.MOVEMENT);
        parkour = new Setting<Boolean>("Parkour", true, this);
        enabled = this.isEnabled;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.antiLevitate.getValue().booleanValue() && this.mc.player.isPotionActive(Objects.requireNonNull(Potion.getPotionFromResourceLocation((String)"levitation")))) {
            this.mc.player.removeActivePotionEffect(Potion.getPotionFromResourceLocation((String)"levitation"));
        }
        if (parkour.getValue().booleanValue()) {
            if (!AirJump.enabled.getValue().booleanValue()) {
                if (this.sprint.getValue().booleanValue() && this.mc.player.moveForward > 0.0f && !this.mc.player.collidedHorizontally) {
                    this.mc.player.setSprinting(true);
                }
                if (!this.mc.player.onGround || this.mc.gameSettings.keyBindJump.isPressed()) {
                    return;
                }
                if (this.mc.player.isSneaking() || this.mc.gameSettings.keyBindSneak.isPressed()) {
                    return;
                }
                AxisAlignedBB entityBoundingBox = this.mc.player.getEntityBoundingBox();
                AxisAlignedBB offsetBox = entityBoundingBox.offset(0.0, -0.5, 0.0).expand(-0.001, 0.0, -0.001);
                List collisionBoxes = this.mc.world.getCollisionBoxes(this.mc.player, offsetBox);
                if (!collisionBoxes.isEmpty()) {
                    return;
                }
                this.mc.player.jump();
            } else {
                MessageUtil.sendError("U are dumb dont use air jump with parkour helper", -1117);
            }
        }
        if (this.sprint.getValue().booleanValue() && this.mc.player.moveForward > 0.0f && !this.mc.player.collidedHorizontally) {
            this.mc.player.setSprinting(true);
        }
    }

    @SubscribeEvent
    public void Packet(PacketEvent event) {
        CPacketEntityAction packet;
        if (event.getPacket() instanceof CPacketEntityAction && ((packet = (CPacketEntityAction)event.getPacket()).getAction() == CPacketEntityAction.Action.START_SPRINTING || packet.getAction() == CPacketEntityAction.Action.STOP_SPRINTING)) {
            event.setCanceled(true);
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.mc.player.stepHeight = 0.5f;
    }
}
