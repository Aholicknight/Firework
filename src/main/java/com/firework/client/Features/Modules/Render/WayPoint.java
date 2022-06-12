package com.firework.client.Features.Modules.Render;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import net.minecraft.util.math.BlockPos;

@ModuleArgs(name="testWaypoint", category=Module.Category.RENDER)
public class WayPoint
extends Module {
    BlockPos pos;
}
