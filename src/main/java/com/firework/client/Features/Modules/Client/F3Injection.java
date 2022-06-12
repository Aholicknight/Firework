package com.firework.client.Features.Modules.Client;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleArgs(name="Custom F3", category=Module.Category.CLIENT)
public class F3Injection
extends Module {
    public Setting<Boolean> Coords = new Setting<Boolean>("Coords", true, this);
    public Setting<Boolean> FPS = new Setting<Boolean>("FPS", true, this);
    public Setting<Boolean> Direction = new Setting<Boolean>("Direction", true, this);
    public Setting<Boolean> Biome = new Setting<Boolean>("Biome ", true, this);

    @SubscribeEvent
    public void onRenderGameOverlay(RenderGameOverlayEvent.Text event) {
        if (this.mc.gameSettings.showDebugInfo) {
            for (int i = 0; i < event.getLeft().size(); ++i) {
                if (this.Coords.getValue().booleanValue()) {
                    if (((String)event.getLeft().get(i)).contains("Looking")) {
                        event.getLeft().set(i, "Looking at a block!");
                    }
                    if (((String)event.getLeft().get(i)).contains("XYZ")) {
                        event.getLeft().set(i, "XYZ: Hidden!");
                    }
                    if (((String)event.getLeft().get(i)).contains("Block:")) {
                        event.getLeft().set(i, "Block: Hidden!");
                    }
                    if (((String)event.getLeft().get(i)).contains("Chunk:")) {
                        event.getLeft().set(i, "Chunk: Hidden!");
                    }
                }
                if (this.FPS.getValue().booleanValue() && ((String)event.getLeft().get(i)).contains("fps")) {
                    event.getLeft().set(i, "fps: 0!");
                }
                if (this.Direction.getValue().booleanValue() && ((String)event.getLeft().get(i)).contains("Facing:")) {
                    event.getLeft().set(i, "Facing: Hidden!");
                }
                if (!this.Biome.getValue().booleanValue() || !((String)event.getLeft().get(i)).contains("Biome:")) continue;
                event.getLeft().set(i, "Biome: Hidden!");
            }
        }
    }
}
