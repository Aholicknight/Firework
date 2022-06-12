package com.firework.client.Features.Modules.World.Scaffold;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.World.Scaffold.ScaffoldBlock;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.BlockUtil;
import com.firework.client.Implementations.Utill.Client.MathUtil;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class Scaffold
extends Module {
    public Setting<Boolean> rotate = new Setting<Boolean>("Rotates", true, this);
    public Setting<Boolean> swing = new Setting<Boolean>("Swing", true, this);
    public Setting<Boolean> Switch = new Setting<Boolean>("Switch", true, this);
    public Setting<Boolean> Tower = new Setting<Boolean>("Tower", true, this);
    public Setting<Double> speed = new Setting<Double>("Delay", 0.7, this, 0.0, 1.0);
    private List<ScaffoldBlock> blocksToRender = new ArrayList<ScaffoldBlock>();
    private BlockPos pos;
    private boolean packet = false;

    public Scaffold() {
        super("Scaffold", Module.Category.WORLD);
    }

    @Override
    public void onTick() {
        super.onTick();
        this.pos = new BlockPos(this.mc.player.posX, this.mc.player.posY - 1.0, this.mc.player.posZ);
        if (this.isAir(this.pos)) {
            BlockUtil.placeBlock(this.pos, EnumHand.MAIN_HAND, this.rotate.getValue(), this.packet, this.mc.player.isSneaking());
            this.blocksToRender.add(new ScaffoldBlock(BlockUtil.posToVec3d(this.pos)));
        }
        if (this.swing.getValue().booleanValue()) {
            this.mc.player.isSwingInProgress = false;
            this.mc.player.swingProgressInt = 0;
            this.mc.player.swingProgress = 0.0f;
            this.mc.player.prevSwingProgress = 0.0f;
        }
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        double[] calc = MathUtil.directionSpeed(this.speed.getValue() / 10.0);
        this.mc.player.motionX = calc[0];
        this.mc.player.motionZ = calc[1];
        if (this.Switch.getValue().booleanValue() && (this.mc.player.getHeldItemMainhand().getItem() == null || !(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock))) {
            for (int j = 0; j < 9; ++j) {
                if (this.mc.player.inventory.getStackInSlot(j) == null || this.mc.player.inventory.getStackInSlot(j).getCount() == 0 || !(this.mc.player.inventory.getStackInSlot(j).getItem() instanceof ItemBlock)) continue;
                this.mc.player.inventory.currentItem = j;
                break;
            }
        }
        if (this.Tower.getValue().booleanValue() && this.mc.gameSettings.keyBindJump.isKeyDown() && this.mc.player.moveForward == 0.0f && this.mc.player.moveStrafing == 0.0f && !this.mc.player.isPotionActive(MobEffects.JUMP_BOOST)) {
            this.mc.player.motionY = 0.2444441;
            this.mc.player.motionZ = 0.0;
            this.mc.player.motionX = 0.0;
        }
    }

    private boolean isAir(BlockPos pos) {
        return this.mc.world.getBlockState(pos).getBlock() == Blocks.AIR;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        this.blocksToRender.clear();
    }
}
