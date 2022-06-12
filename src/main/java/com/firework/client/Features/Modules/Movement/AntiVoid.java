package com.firework.client.Features.Modules.Movement;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import java.util.Arrays;

@ModuleArgs(name="AntiVoid", category=Module.Category.MOVEMENT)
public class AntiVoid
extends Module {
    public Setting<String> mode = new Setting<String>("Mode", "Jump", (Module)this, Arrays.asList("Jump", "PacketFly"));

    @Override
    public void onTick() {
        super.onTick();
        if (this.mode.getValue().equals("Jump")) {
            if (this.mc.player.posY <= 0.5) {
                this.mc.player.moveVertical = 10.0f;
                this.mc.player.jump();
            } else {
                this.mc.player.moveVertical = 0.0f;
            }
        }
    }

    @Override
    public void onDisable() {
        this.mc.player.moveVertical = 0.0f;
    }
}
