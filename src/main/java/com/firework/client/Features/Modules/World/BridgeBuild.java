package com.firework.client.Features.Modules.World;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.input.Keyboard;

public class BridgeBuild
extends Module {
    public Setting<Boolean> disableOnJump = new Setting<Boolean>("DisableOnJump", false, this);

    public BridgeBuild() {
        super("BridgeBuild", Module.Category.WORLD);
    }

    @Override
    public void onTick() {
        super.onTick();
        BlockPos pos = new BlockPos(this.mc.player.posX, this.mc.player.posY - 1.0, this.mc.player.posZ);
        KeyBinding.setKeyBindState((int)this.mc.gameSettings.keyBindSneak.getKeyCode(), (this.mc.world.getBlockState(pos).getBlock() == Blocks.AIR ? 1 : 0) != 0);
        if (this.disableOnJump.getValue().booleanValue() && Keyboard.isKeyDown(Minecraft.getMinecraft().gameSettings.keyBindJump.getKeyCode())) {
            MessageUtil.sendClientMessage("detected jump.. turnin", true);
            this.isEnabled.setValue(false);
        }
    }
}
