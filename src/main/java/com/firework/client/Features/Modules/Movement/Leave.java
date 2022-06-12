package com.firework.client.Features.Modules.Movement;

import com.firework.client.Features.Modules.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Leave
extends Module {
    public Leave() {
        super("LiquidLeave", Module.Category.MOVEMENT);
    }

    @SubscribeEvent
    public void onUpdate(TickEvent.PlayerTickEvent e) {
        int motion = 10;
        BlockPos blockPos = new BlockPos(this.mc.player.posX, this.mc.player.posY - 0.1, this.mc.player.posZ);
        Block block = this.mc.world.getBlockState(blockPos).getBlock();
        if (block instanceof BlockLiquid) {
            if (this.mc.world.getBlockState(new BlockPos(this.mc.player.posX, this.mc.player.posY + 0.01, this.mc.player.posZ)).getBlock() == Block.getBlockById((int)9) && this.mc.player.isInsideOfMaterial(Material.AIR)) {
                this.mc.player.motionY = 0.08;
            }
            if (this.mc.player.fallDistance > 0.0f && this.mc.player.motionY < 0.01) {
                this.mc.player.motionY = motion;
            }
        }
        if (block instanceof BlockLiquid) {
            if (this.mc.world.getBlockState(new BlockPos(this.mc.player.posX, this.mc.player.posY + 0.01, this.mc.player.posZ)).getBlock() == Block.getBlockById((int)10) && this.mc.player.isInsideOfMaterial(Material.AIR)) {
                this.mc.player.motionY = 0.08;
            }
            if (this.mc.player.fallDistance > 0.0f && this.mc.player.motionY < 0.01) {
                this.mc.player.motionY = motion;
            }
        }
    }
}
