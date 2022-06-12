package com.firework.client.Implementations.Managers.Settings;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import java.util.ArrayList;

public class SettingManager {
    public ArrayList<Setting> settings = new ArrayList();

    public void updateSettingsByName(Setting setting) {
        for (Setting _setting : this.settings) {
            if (_setting.name != setting.name) continue;
            Setting setting2 = setting;
        }
    }

    public Setting getSettingByName(String name) {
        for (Setting setting : this.settings) {
            if (setting.name != name) continue;
            return setting;
        }
        return null;
    }

    public ArrayList<Setting> modulesSettings(Module module) {
        ArrayList<Setting> settings = new ArrayList<Setting>();
        for (Setting setting : this.settings) {
            if (setting.module.name != module.name) continue;
            settings.add(setting);
        }
        return settings;
    }

    public ArrayList<String> settingsNames() {
        ArrayList<String> names = new ArrayList<String>();
        for (Setting setting : this.settings) {
            names.add(setting.name);
        }
        return names;
    }

    public ArrayList<String> settingsNames(Module module) {
        ArrayList<String> names = new ArrayList<String>();
        for (Setting setting : this.modulesSettings(module)) {
            names.add(setting.name);
        }
        return names;
    }
}
