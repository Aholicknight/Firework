package com.firework.client.Features.Modules.Misc;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketEntityAction;

@ModuleArgs(name="FastSleep", category=Module.Category.MISC)
public class FastSleep
extends Module {
    @Override
    public void onTick() {
        super.onTick();
        EntityPlayerSP player = this.mc.player;
        if (player.isPlayerSleeping() && player.getSleepTimer() > 10) {
            player.connection.sendPacket(new CPacketEntityAction(player, CPacketEntityAction.Action.STOP_SLEEPING));
        }
    }
}
