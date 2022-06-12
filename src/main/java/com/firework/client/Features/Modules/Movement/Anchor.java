package com.firework.client.Features.Modules.Movement;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Util;
import java.util.ArrayList;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class Anchor
extends Module {
    public Setting<Boolean> pull = new Setting<Boolean>("Pull", true, this);
    public Setting<Double> pitch = new Setting<Double>("Pitch", 60.0, this, 0.0, 90.0);
    private final ArrayList<BlockPos> holes = new ArrayList();
    int holeblocks;
    public static boolean AnchorING;
    private Vec3d Center = Vec3d.ZERO;

    public Anchor() {
        super("Anchor", Module.Category.MOVEMENT);
    }

    public boolean isBlockHole(BlockPos blockpos) {
        this.holeblocks = 0;
        if (Util.mc.world.getBlockState(blockpos.add(0, 3, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (Util.mc.world.getBlockState(blockpos.add(0, 2, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (Util.mc.world.getBlockState(blockpos.add(0, 1, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (Util.mc.world.getBlockState(blockpos.add(0, 0, 0)).getBlock() == Blocks.AIR) {
            ++this.holeblocks;
        }
        if (Util.mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.OBSIDIAN || Util.mc.world.getBlockState(blockpos.add(0, -1, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (Util.mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.OBSIDIAN || Util.mc.world.getBlockState(blockpos.add(1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (Util.mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.OBSIDIAN || Util.mc.world.getBlockState(blockpos.add(-1, 0, 0)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (Util.mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.OBSIDIAN || Util.mc.world.getBlockState(blockpos.add(0, 0, 1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        if (Util.mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.OBSIDIAN || Util.mc.world.getBlockState(blockpos.add(0, 0, -1)).getBlock() == Blocks.BEDROCK) {
            ++this.holeblocks;
        }
        return this.holeblocks >= 9;
    }

    public Vec3d GetCenter(double posX, double posY, double posZ) {
        double x = Math.floor(posX) + 0.5;
        double y = Math.floor(posY);
        double z = Math.floor(posZ) + 0.5;
        return new Vec3d(x, y, z);
    }

    @Override
    public void onTick() {
        super.onTick();
        if (Util.mc.world == null) {
            return;
        }
        if (Util.mc.player.posY < 0.0) {
            return;
        }
        if ((double)Util.mc.player.rotationPitch >= this.pitch.getValue()) {
            if (this.isBlockHole(this.getPlayerPos().down(1)) || this.isBlockHole(this.getPlayerPos().down(2)) || this.isBlockHole(this.getPlayerPos().down(3)) || this.isBlockHole(this.getPlayerPos().down(4))) {
                AnchorING = true;
                if (!this.pull.getValue().booleanValue()) {
                    Util.mc.player.motionX = 0.0;
                    Util.mc.player.motionZ = 0.0;
                } else {
                    this.Center = this.GetCenter(Util.mc.player.posX, Util.mc.player.posY, Util.mc.player.posZ);
                    double XDiff = Math.abs(this.Center.x - Util.mc.player.posX);
                    double ZDiff = Math.abs(this.Center.z - Util.mc.player.posZ);
                    if (XDiff <= 0.1 && ZDiff <= 0.1) {
                        this.Center = Vec3d.ZERO;
                    } else {
                        double MotionX = this.Center.x - Util.mc.player.posX;
                        double MotionZ = this.Center.z - Util.mc.player.posZ;
                        Util.mc.player.motionX = MotionX / 2.0;
                        Util.mc.player.motionZ = MotionZ / 2.0;
                    }
                }
            } else {
                AnchorING = false;
            }
        }
    }

    public BlockPos getPlayerPos() {
        return new BlockPos(Math.floor(Util.mc.player.posX), Math.floor(Util.mc.player.posY), Math.floor(Util.mc.player.posZ));
    }
}
