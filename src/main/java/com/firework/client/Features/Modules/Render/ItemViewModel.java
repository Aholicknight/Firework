package com.firework.client.Features.Modules.Render;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;

public class ItemViewModel
extends Module {
    public static Setting<Boolean> enabled = null;
    public static Setting<Double> fov = null;
    public static Setting<Double> translateX = null;
    public static Setting<Double> translateY = null;
    public static Setting<Double> translateZ = null;
    public static Setting<Double> rotateXR = null;
    public static Setting<Double> rotateYR = null;
    public static Setting<Double> rotateZR = null;
    public static Setting<Double> rotateXL = null;
    public static Setting<Double> rotateYL = null;
    public static Setting<Double> rotateZL = null;
    public static Setting<Double> scaleX = null;
    public static Setting<Double> scaleY = null;
    public static Setting<Double> scaleZ = null;

    public ItemViewModel() {
        super("ItemViewModel", Module.Category.RENDER);
        enabled = this.isEnabled;
        translateX = new Setting<Double>("translateX", 0.0, this, -300.0, 300.0);
        translateY = new Setting<Double>("translateY", 0.0, this, -300.0, 300.0);
        translateZ = new Setting<Double>("translateZ", 0.0, this, -300.0, 300.0);
        rotateXR = new Setting<Double>("rotateXR", 0.0, this, -300.0, 300.0);
        rotateYR = new Setting<Double>("rotateYR", 0.0, this, -300.0, 300.0);
        rotateZR = new Setting<Double>("rotateZR", 0.0, this, -300.0, 300.0);
        rotateXL = new Setting<Double>("rotateXL", 0.0, this, -300.0, 300.0);
        rotateYL = new Setting<Double>("rotateYL", 0.0, this, -300.0, 300.0);
        rotateZL = new Setting<Double>("rotateZL", 0.0, this, -300.0, 300.0);
        scaleX = new Setting<Double>("scaleX", 100.0, this, -300.0, 300.0);
        scaleY = new Setting<Double>("scaleY", 100.0, this, -300.0, 300.0);
        scaleZ = new Setting<Double>("scaleZ", 100.0, this, -300.0, 300.0);
    }
}
