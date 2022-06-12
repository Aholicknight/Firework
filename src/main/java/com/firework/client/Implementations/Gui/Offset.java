package com.firework.client.Implementations.Gui;

import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.Gui;
import java.util.Collections;

public class Offset {
    public int offset;

    public void register(Button ... button) {
        Collections.addAll(Gui.initializedButtons, button);
        for (Button b : button) {
            this.offset += b.offset;
        }
    }
}
