package com.firework.client.Features.Modules.World;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Events.PacketEvent;
import com.firework.client.Implementations.Mixins.MixinsList.ISPacketPlayerPosLook;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class NoRotate
extends Module {
    public NoRotate() {
        super("NoForceRotate", Module.Category.WORLD);
    }

    @SubscribeEvent
    public void onReceive(PacketEvent.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook && !(this.mc.currentScreen instanceof GuiDownloadTerrain)) {
            SPacketPlayerPosLook packet = (SPacketPlayerPosLook)((Object)event.getPacket());
            ((ISPacketPlayerPosLook)((Object)packet)).setYaw(this.mc.player.rotationYaw);
            ((ISPacketPlayerPosLook)((Object)packet)).setPitch(this.mc.player.rotationPitch);
            packet.getFlags().remove(SPacketPlayerPosLook.EnumFlags.X_ROT);
            packet.getFlags().remove(SPacketPlayerPosLook.EnumFlags.Y_ROT);
        }
    }
}
