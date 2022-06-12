package com.firework.client.Implementations.Gui.Components;

import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Settings.Setting;

public class Button {
    public int x;
    public int y;
    public int width;
    public int height;
    public int originHeight;
    public int originOffset;
    public Setting setting = null;
    public int offset = 11;
    public int localIndex;

    public Button(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.localIndex = ++GuiInfo.index;
    }

    public Button(Setting setting, int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.originHeight = height;
        this.originOffset = this.offset;
        this.setting = setting;
        this.localIndex = ++GuiInfo.index;
    }

    public void draw() {
        if (this.setting != null) {
            if (!this.setting.hidden) {
                this.height = this.setting.hidden ? 0 : this.originHeight;
                this.offset = this.setting.hidden ? 0 : this.originOffset;
            } else {
                return;
            }
        }
    }

    public void onKeyTyped(int keyCode) {
    }

    public void initialize(int mouseX, int mouseY) {
    }

    public void initialize(int mouseX, int mouseY, int state) {
    }
}
