package com.firework.client.Features.Modules;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Client.ClassFinder;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import org.json.simple.JSONObject;

public class ModuleManager {
    public ArrayList<Module> modules = new ArrayList();

    public ModuleManager() {
        this.registerModules();
    }

    public void registerModules() {
        Set<Class> classList = ClassFinder.findClasses(Module.class.getPackage().getName(), Module.class);
        classList.forEach(aClass -> {
            try {
                Module module = (Module)aClass.getConstructor(new Class[0]).newInstance(new Object[0]);
                this.modules.add(module);
            }
            catch (InvocationTargetException e) {
                e.getCause().printStackTrace();
                System.err.println("Couldn't initiate module " + aClass.getSimpleName() + "! Err: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
            }
            catch (Exception e) {
                e.printStackTrace();
                System.err.println("Couldn't initiate module " + aClass.getSimpleName() + "! Err: " + e.getClass().getSimpleName() + ", message: " + e.getMessage());
            }
        });
        System.out.println("Modules initialised");
        this.modules.sort(Comparator.comparing(Module::getName));
    }

    public void saveModules() {
        for (Module module : this.modules) {
            JSONObject moduleJson = new JSONObject();
            moduleJson.put("isEnabled", module.isEnabled);
            for (Field var : module.getClass().getDeclaredFields()) {
                if (!Setting.class.isAssignableFrom(var.getType())) continue;
                Setting setting = null;
                try {
                    setting = (Setting)var.get(this);
                }
                catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                moduleJson.put("settings/" + setting.name, setting);
            }
            try {
                FileWriter file = new FileWriter(Firework.FIREWORK_DIRECTORY + "/Modules/" + module.getClass().getSimpleName() + ".json");
                Throwable throwable = null;
                try {
                    file.write(moduleJson.toJSONString());
                }
                catch (Throwable throwable2) {
                    throwable = throwable2;
                    throw throwable2;
                }
                finally {
                    if (file == null) continue;
                    if (throwable != null) {
                        try {
                            file.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        continue;
                    }
                    file.close();
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
