package com.firework.client.Implementations.Settings;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import java.util.Arrays;
import java.util.List;

public class Setting<T> {
    private T value;
    public String name;
    public Module module;
    public List<Object> list;
    public int index = 0;
    public Mode mode;
    public double min;
    public double max;
    public boolean opened = false;
    public boolean hidden = false;

    public Setting(String name, T value, Module module) {
        this.name = name;
        this.value = value;
        this.module = module;
        if (value instanceof Boolean) {
            this.mode = Mode.BOOL;
        }
        if (value instanceof Integer) {
            this.mode = Mode.KEY;
        }
        if (value instanceof HSLColor) {
            this.mode = Mode.COLOR;
        }
        Firework.settingManager.settings.add(this);
    }

    public Setting(String name, T value, Module module, double min, double max) {
        this.name = name;
        this.value = value;
        this.module = module;
        this.min = min;
        this.max = max;
        this.mode = Mode.NUMBER;
        Firework.settingManager.settings.add(this);
    }

    public Setting(String name, T value, Module module, List<String> list) {
        this.name = name;
        this.value = value;
        this.module = module;
        this.list = Arrays.asList(list.toArray());
        this.mode = Mode.MODE;
        Firework.settingManager.settings.add(this);
    }

    public Setting(String name, T value, Module module, Enum[] list) {
        this.name = name;
        this.value = value;
        this.module = module;
        this.list = Arrays.asList(Arrays.stream(list).toArray());
        this.mode = Mode.MODE;
        Firework.settingManager.settings.add(this);
    }

    public void setValue(T newValue) {
        this.value = newValue;
        Firework.settingManager.updateSettingsByName(this);
    }

    public Setting<T> setVisibility(boolean visibility) {
        this.hidden = !visibility;
        Firework.settingManager.updateSettingsByName(this);
        return this;
    }

    public T getValue() {
        return this.value;
    }

    public boolean getValue(T checkValue) {
        return this.getValue() == checkValue;
    }

    public static enum Mode {
        BOOL,
        NUMBER,
        MODE,
        KEY,
        COLOR;

    }
}
