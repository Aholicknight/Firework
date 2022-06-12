package com.firework.client.Features.Modules;

import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;

public class Module {
    public Minecraft mc = Minecraft.getMinecraft();
    public String name;
    public Category category;
    private int updateTimer = 0;
    public int delay = 20;
    public boolean existCheck;
    public String subCategory;
    public Setting<Boolean> isEnabled = new Setting<Boolean>("isEnabled", false, this).setVisibility(false);
    public Setting<Boolean> isOpened = new Setting<Boolean>("isOpened", false, this).setVisibility(false);
    public Setting<Integer> key = new Setting<Integer>("Key", 0, this);

    public Module(String name, Category category) {
        this.name = name;
        this.category = category;
    }

    public Module() {
        if (this.getClass().isAnnotationPresent(ModuleArgs.class)) {
            ModuleArgs args = this.getClass().getAnnotation(ModuleArgs.class);
            this.name = args.name();
            this.category = args.category();
            this.subCategory = args.subCategory();
        }
    }

    public void onEnable() {
        this.isEnabled.setValue(true);
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void onDisable() {
        this.isEnabled.setValue(false);
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void onToggle() {
        if (this.isEnabled.getValue().booleanValue()) {
            this.onDisable();
        } else {
            this.onEnable();
        }
    }

    public void disable() {
        MinecraftForge.EVENT_BUS.unregister(this);
    }

    public void onTick() {
        if (this.existCheck && this.mc.player == null | this.mc.world == null) {
            return;
        }
        this.updateTimer = this.updateTimer != this.delay ? ++this.updateTimer : 0;
    }

    public String getName() {
        return this.name;
    }

    public static enum Category {
        CHAT,
        COMBAT,
        MOVEMENT,
        RENDER,
        MISC,
        WORLD,
        CLIENT;

    }
}
