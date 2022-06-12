package com.firework.client.Implementations.Gui.Components;

import java.util.ArrayList;

public class Column {
    public ArrayList<Object> components = new ArrayList();
    public String name;
    int offset = 0;

    public Column(String name) {
        this.name = name;
    }
}
