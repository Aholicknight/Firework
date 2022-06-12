package com.firework.client.Features.Modules.World.Burrow;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.World.Burrow.BurrowUtil;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import net.minecraft.block.BlockAnvil;
import net.minecraft.block.BlockEnderChest;
import net.minecraft.block.BlockObsidian;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class SelfBlock
extends Module {
    public Setting<Double> offset = new Setting<Double>("Offset", 3.0, this, -5.0, 5.0);
    public Setting<Boolean> ground = new Setting<Boolean>("Ground check", true, this);
    public Setting<Boolean> rotate = new Setting<Boolean>("Rotate", true, this);
    public Setting<Boolean> swing = new Setting<Boolean>("Swing", true, this);
    public Setting<Boolean> echest = new Setting<Boolean>("Use echest", false, this);
    public Setting<Boolean> anvil = new Setting<Boolean>("Use anvil", false, this);
    private BlockPos originalPos;
    private int oldSlot = -1;

    public SelfBlock() {
        super("SelfBlock", Module.Category.WORLD);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.originalPos = new BlockPos(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ);
        if (this.mc.world.getBlockState(new BlockPos(this.mc.player.posX, this.mc.player.posY, this.mc.player.posZ)).getBlock().equals(Blocks.OBSIDIAN) || this.intersectsWithEntity(this.originalPos)) {
            this.isEnabled.setValue(false);
            return;
        }
        this.oldSlot = this.mc.player.inventory.currentItem;
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.ground.getValue().booleanValue() && !this.mc.player.onGround) {
            this.isEnabled.setValue(false);
            return;
        }
        if (this.swing.getValue().booleanValue()) {
            this.mc.player.isSwingInProgress = false;
            this.mc.player.swingProgressInt = 0;
            this.mc.player.swingProgress = 0.0f;
            this.mc.player.prevSwingProgress = 0.0f;
        }
        if (this.anvil.getValue().booleanValue() && BurrowUtil.findHotbarBlock(BlockAnvil.class) != -1) {
            BurrowUtil.switchToSlot(BurrowUtil.findHotbarBlock(BlockAnvil.class));
        } else if (this.echest.getValue() != false ? BurrowUtil.findHotbarBlock(BlockEnderChest.class) != -1 : BurrowUtil.findHotbarBlock(BlockObsidian.class) != -1) {
            BurrowUtil.switchToSlot(this.echest.getValue() != false ? BurrowUtil.findHotbarBlock(BlockEnderChest.class) : BurrowUtil.findHotbarBlock(BlockObsidian.class));
        } else {
            MessageUtil.sendClientMessage("Unable to place burrow block (anvil, ec or oby)", true);
            this.isEnabled.setValue(false);
            return;
        }
        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.41999998688698, this.mc.player.posZ, true));
        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 0.7531999805211997, this.mc.player.posZ, true));
        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.00133597911214, this.mc.player.posZ, true));
        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + 1.16610926093821, this.mc.player.posZ, true));
        BurrowUtil.placeBlock(this.originalPos, EnumHand.MAIN_HAND, this.rotate.getValue(), true, false);
        this.mc.player.connection.sendPacket(new CPacketPlayer.Position(this.mc.player.posX, this.mc.player.posY + this.offset.getValue(), this.mc.player.posZ, false));
        this.mc.player.connection.sendPacket(new CPacketEntityAction(this.mc.player, CPacketEntityAction.Action.STOP_SNEAKING));
        this.mc.player.setSneaking(false);
        BurrowUtil.switchToSlot(this.oldSlot);
        this.isEnabled.setValue(false);
    }

    private boolean intersectsWithEntity(BlockPos pos) {
        for (Entity entity : this.mc.world.loadedEntityList) {
            if (entity.equals(this.mc.player) || entity instanceof EntityItem || !new AxisAlignedBB(pos).intersects(entity.getEntityBoundingBox())) continue;
            return true;
        }
        return false;
    }
}
