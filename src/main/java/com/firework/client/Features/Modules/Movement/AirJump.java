package com.firework.client.Features.Modules.Movement;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.Movement.MovementHelper;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;

public class AirJump
extends Module {
    public int delay = 1000;
    public static Setting<Boolean> enabled = null;

    public AirJump() {
        super("AirJump", Module.Category.MOVEMENT);
        enabled = this.isEnabled;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (MovementHelper.parkour.getValue().booleanValue() && MovementHelper.enabled.getValue().booleanValue()) {
            MessageUtil.sendError("U are dumb dont use air jump with parkour helper", -1117);
        } else {
            this.mc.player.onGround = true;
        }
    }
}
