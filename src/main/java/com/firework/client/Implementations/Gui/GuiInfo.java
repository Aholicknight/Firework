package com.firework.client.Implementations.Gui;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Column;
import com.firework.client.Implementations.Utill.Client.Pair;
import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.util.ResourceLocation;

public class GuiInfo {
    public static ArrayList<Pair> icons = new ArrayList();
    public static ArrayList<Column> columns = new ArrayList();
    public static Color outlineColorA = new Color(63, 63, 63);
    public static Color outlineColorB = new Color(65, 65, 65);
    public static Color outlineColorC = new Color(100, 100, 100);
    public static Color fillColorA = new Color(72, 72, 72);
    public static Color fillColorB = new Color(107, 103, 107);
    public static Color outlineFrameColorA = new Color(76, 76, 76);
    public static Color outlineFrameColorB = new Color(115, 115, 115);
    public static double guiScale = 0.01;
    public static int index = 0;

    public static void setupModulesColumns() {
        columns.clear();
        for (Module.Category c : Module.Category.values()) {
            columns.add(new Column(c.toString()));
        }
    }

    public static void addModuleToColumn(Module module) {
        for (Column c : columns) {
            if (c.name != module.category.toString()) continue;
            c.components.add(module);
        }
    }

    public static void icons() {
        icons.add(new Pair((Object)Module.Category.COMBAT, Firework.resourceLocation("firework/textures/combat.png")));
    }

    public static boolean hasCategoryIcon(String category) {
        for (Pair pair : icons) {
            if (((Module.Category)((Object)pair.one)).toString() != category) continue;
            return true;
        }
        return false;
    }

    public static ResourceLocation resourceLocationByCategory(String category) {
        for (Pair pair : icons) {
            if (((Module.Category)((Object)pair.one)).toString() != category) continue;
            return (ResourceLocation)pair.two;
        }
        return null;
    }
}
