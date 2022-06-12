package com.firework.client.Features.Modules.World;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Events.PacketEvent;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraft.item.ItemBoat;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class Bypass
extends Module {
    public Setting<Boolean> boatPlace = new Setting<Boolean>("BoatPlace", true, this);
    public Setting<Boolean> buildHeight = new Setting<Boolean>("BuildHeight", true, this);

    public Bypass() {
        super("Bypass", Module.Category.WORLD);
    }

    @Override
    public void onTick() {
        if (this.boatPlace.getValue().booleanValue() && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBoat && Mouse.isButtonDown(1)) {
            this.mc.getConnection().sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            this.mc.getConnection().sendPacket(new CPacketPlayerTryUseItemOnBlock(this.mc.objectMouseOver.getBlockPos(), EnumFacing.SOUTH, EnumHand.MAIN_HAND, 1.0f, 1.0f, 1.0f));
        }
    }

    @SubscribeEvent
    public void onPacket(PacketEvent event) {
        if (this.buildHeight.getValue().booleanValue()) {
            CPacketPlayerTryUseItemOnBlock oldPacket;
            if (this.mc.player == null) {
                return;
            }
            if (event.getPacket() instanceof CPacketPlayerTryUseItemOnBlock && (oldPacket = (CPacketPlayerTryUseItemOnBlock)event.getPacket()).getPos().getY() >= 255 && oldPacket.getDirection() == EnumFacing.UP) {
                this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItemOnBlock(oldPacket.getPos(), EnumFacing.DOWN, oldPacket.getHand(), oldPacket.getFacingX(), oldPacket.getFacingY(), oldPacket.getFacingZ()));
                event.setCanceled(true);
            }
        }
    }
}
