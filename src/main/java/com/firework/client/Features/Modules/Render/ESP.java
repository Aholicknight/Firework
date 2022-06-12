package com.firework.client.Features.Modules.Render;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import com.firework.client.Implementations.Utill.Render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityBed;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntityShulkerBox;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ESP
extends Module {
    AxisAlignedBB box = null;
    public Setting<Boolean> pull9 = new Setting<Boolean>("Player", true, this);
    public static Setting<HSLColor> playerColor2 = null;
    public static Setting<HSLColor> playerColor1 = null;
    public Setting<Boolean> pull10 = new Setting<Boolean>("Items", true, this);
    public Setting<Boolean> pull = new Setting<Boolean>("Chest", true, this);
    public Setting<Boolean> pull1 = new Setting<Boolean>("EnderChest", true, this);
    public Setting<Boolean> pull7 = new Setting<Boolean>("Shulker", true, this);
    public Setting<Boolean> pull2 = new Setting<Boolean>("Hopper", true, this);
    public Setting<Boolean> pull3 = new Setting<Boolean>("Dropper", true, this);
    public Setting<Boolean> pull4 = new Setting<Boolean>("Dispenser", true, this);
    public Setting<Boolean> pull5 = new Setting<Boolean>("Bed", true, this);
    public Setting<Boolean> pull6 = new Setting<Boolean>("Spawner", true, this);
    public Setting<Boolean> pull8 = new Setting<Boolean>("Beacon", true, this);

    public ESP() {
        super("ESP", Module.Category.RENDER);
        playerColor2 = new Setting<HSLColor>("PlayerOutline", new HSLColor(1.0f, 54.0f, 43.0f), this);
        playerColor1 = new Setting<HSLColor>("PlayerColor", new HSLColor(1.0f, 54.0f, 43.0f), this);
    }

    @SubscribeEvent
    public void onRender(RenderWorldLastEvent e) {
        for (Object c : this.mc.world.loadedTileEntityList) {
            if (this.pull.getValue().booleanValue() && c instanceof TileEntityChest) {
                RenderUtils.blockESP(((TileEntityChest)c).getPos());
            }
            if (this.pull2.getValue().booleanValue() && c instanceof TileEntityHopper) {
                RenderUtils.blockESP(((TileEntityHopper)c).getPos());
            }
            if (this.pull3.getValue().booleanValue() && c instanceof TileEntityDropper) {
                RenderUtils.blockESP(((TileEntityDropper)c).getPos());
            }
            if (this.pull4.getValue().booleanValue() && c instanceof TileEntityDispenser) {
                RenderUtils.blockESP(((TileEntityDispenser)c).getPos());
            }
            if (this.pull5.getValue().booleanValue() && c instanceof TileEntityBed) {
                RenderUtils.blockESP(((TileEntityBed)c).getPos());
            }
            if (this.pull6.getValue().booleanValue() && c instanceof TileEntityMobSpawner) {
                RenderUtils.blockESP(((TileEntityMobSpawner)c).getPos());
            }
            if (this.pull7.getValue().booleanValue() && c instanceof TileEntityShulkerBox) {
                RenderUtils.blockESP(((TileEntityShulkerBox)c).getPos());
            }
            if (this.pull8.getValue().booleanValue() && c instanceof TileEntityBeacon) {
                RenderUtils.blockESP(((TileEntityBeacon)c).getPos());
            }
            if (this.pull1.getValue().booleanValue() && c instanceof TileEntityEnderChest) {
                RenderUtils.blockESP(((TileEntityEnderChest)c).getPos());
            }
            if (!this.pull10.getValue().booleanValue()) continue;
            for (Entity entity : this.mc.world.loadedEntityList) {
                if (!(entity instanceof EntityItem)) continue;
                this.box = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + ((double)((float)((double)((float)entity.lastTickPosX) + (entity.posX - entity.lastTickPosX) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().minY - entity.posY + ((double)((float)((double)((float)entity.lastTickPosY) + (entity.posY - entity.lastTickPosY) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + ((double)((float)((double)((float)entity.lastTickPosZ) + (entity.posZ - entity.lastTickPosZ) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosZ), entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + ((double)((float)((double)((float)entity.lastTickPosX) + (entity.posX - entity.lastTickPosX) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + ((double)((float)((double)((float)entity.lastTickPosY) + (entity.posY - entity.lastTickPosY) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + ((double)((float)((double)((float)entity.lastTickPosZ) + (entity.posZ - entity.lastTickPosZ) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosZ));
                RenderUtils.FillLine(entity, this.box);
            }
        }
        if (this.pull9.getValue().booleanValue()) {
            for (Entity entity : this.mc.world.playerEntities) {
                if (entity == this.mc.player || entity == null) continue;
                this.box = new AxisAlignedBB(entity.getEntityBoundingBox().minX - 0.05 - entity.posX + ((double)((float)((double)((float)entity.lastTickPosX) + (entity.posX - entity.lastTickPosX) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().minY - entity.posY + ((double)((float)((double)((float)entity.lastTickPosY) + (entity.posY - entity.lastTickPosY) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().minZ - 0.05 - entity.posZ + ((double)((float)((double)((float)entity.lastTickPosZ) + (entity.posZ - entity.lastTickPosZ) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosZ), entity.getEntityBoundingBox().maxX + 0.05 - entity.posX + ((double)((float)((double)((float)entity.lastTickPosX) + (entity.posX - entity.lastTickPosX) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosX), entity.getEntityBoundingBox().maxY + 0.1 - entity.posY + ((double)((float)((double)((float)entity.lastTickPosY) + (entity.posY - entity.lastTickPosY) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosY), entity.getEntityBoundingBox().maxZ + 0.05 - entity.posZ + ((double)((float)((double)((float)entity.lastTickPosZ) + (entity.posZ - entity.lastTickPosZ) * (double)Minecraft.getMinecraft().getRenderPartialTicks())) - Minecraft.getMinecraft().getRenderManager().viewerPosZ));
                RenderUtils.FillLine(entity, this.box);
            }
        }
    }
}
