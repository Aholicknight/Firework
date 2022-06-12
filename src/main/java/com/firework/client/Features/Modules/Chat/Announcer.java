package com.firework.client.Features.Modules.Chat;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;

public class Announcer
extends Module {
    public Setting<Boolean> join = new Setting<Boolean>("Join", false, this);
    public Setting<Boolean> leave = new Setting<Boolean>("Leave", false, this);
    public Setting<Boolean> food = new Setting<Boolean>("Food", false, this);
    public Setting<Boolean> place = new Setting<Boolean>("Place", false, this);
    public Setting<Boolean> Break = new Setting<Boolean>("Break", false, this);
    public Setting<Boolean> worldTime = new Setting<Boolean>("WorldTime", false, this);
    public Setting<Boolean> clientSideOnly = new Setting<Boolean>("ClientSideOnly", false, this);

    public Announcer() {
        super("Announcer", Module.Category.CHAT);
    }
}
