package com.firework.client.Features.Modules.Misc;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraft.network.play.server.SPacketDisconnect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;

@ModuleArgs(name="AutoLog", category=Module.Category.MISC)
public class AutoLog
extends Module {
    static String gegra = "";
    public Setting<Boolean> enabled;
    public Setting<Double> helth;
    public Setting<Boolean> disable;

    public AutoLog() {
        this.enabled = this.isEnabled;
        this.helth = new Setting<Double>("Health", 5.0, this, 1.0, 20.0);
        this.disable = new Setting<Boolean>("AutoDisable", false, this);
    }

    public void onDamage(DamageSource e) {
        String bebra;
        gegra = bebra = e.getImmediateSource().getName();
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        int health = this.helth.getValue().intValue();
        if (this.mc.player.getHealth() < (float)health) {
            this.mc.player.connection.handleDisconnect(new SPacketDisconnect(new TextComponentString("Autolog!")));
            if (this.disable.getValue().booleanValue()) {
                this.enabled.setValue(false);
            }
        }
    }
}
