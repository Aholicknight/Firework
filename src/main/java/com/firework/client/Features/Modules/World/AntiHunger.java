package com.firework.client.Features.Modules.World;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Events.PacketEvent;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleArgs(name="AntiHunger", category=Module.Category.WORLD)
public class AntiHunger
extends Module {
    @SubscribeEvent
    public void onPacket(PacketEvent e) {
        if (e.getPacket() instanceof CPacketPlayer) {
            boolean groundCheck;
            CPacketPlayer player = (CPacketPlayer)((Object)e.getPacket());
            double differenceY = this.mc.player.posY - this.mc.player.lastTickPosY;
            boolean bl = groundCheck = differenceY == 0.0;
            if (groundCheck && !this.mc.playerController.getIsHittingBlock()) {
                this.mc.player.onGround = true;
            }
        }
    }
}
