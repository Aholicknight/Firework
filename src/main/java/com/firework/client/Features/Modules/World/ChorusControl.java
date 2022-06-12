package com.firework.client.Features.Modules.World;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Events.ItemUsedEvent;
import com.firework.client.Implementations.Events.PacketEvent;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.CPacketConfirmTeleport;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleArgs(name="ChorusControl", category=Module.Category.WORLD)
public class ChorusControl
extends Module {
    private SPacketPlayerPosLook packet;
    private boolean consumed = false;

    @Override
    public void onTick() {
        super.onTick();
        if (this.consumed && this.mc.gameSettings.keyBindSneak.isKeyDown()) {
            this.packet = null;
            this.consumed = false;
        }
        if (this.packet != null && !this.consumed) {
            this.mc.player.connection.handlePlayerPosLook(this.packet);
            this.packet = null;
            this.consumed = false;
        }
    }

    @SubscribeEvent
    public void onItemUsed(ItemUsedEvent event) {
        if (event.getEntityLiving().equals(this.mc.player) && event.getStack().getItem().equals(Items.CHORUS_FRUIT)) {
            this.consumed = true;
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            event.setCanceled(true);
        }
        if (event.getPacket() instanceof CPacketPlayer || event.getPacket() instanceof CPacketConfirmTeleport) {
            event.setCanceled(this.consumed);
        }
    }
}
