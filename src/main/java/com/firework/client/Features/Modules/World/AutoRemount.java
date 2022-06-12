package com.firework.client.Features.Modules.World;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;

@ModuleArgs(name="AutoRemount", category=Module.Category.WORLD)
public class AutoRemount
extends Module {
    public Setting<Boolean> Bypass = new Setting<Boolean>("Bypass", true, this);
    public Setting<Boolean> boat = new Setting<Boolean>("boat", true, this);
    public Setting<Boolean> Minecart = new Setting<Boolean>("Minecart", true, this);
    public Setting<Boolean> horse = new Setting<Boolean>("horse", true, this);
    public Setting<Boolean> skeletonHorse = new Setting<Boolean>("skeletonHorse", true, this);
    public Setting<Boolean> donkey = new Setting<Boolean>("donkey", true, this);
    public Setting<Boolean> mule = new Setting<Boolean>("mule", true, this);
    public Setting<Boolean> pig = new Setting<Boolean>("pig ", true, this);
    public Setting<Boolean> llama = new Setting<Boolean>("llama", true, this);
    public Setting<Double> range = new Setting<Double>("range", 2.0, this, 0.0, 10.0);
}
