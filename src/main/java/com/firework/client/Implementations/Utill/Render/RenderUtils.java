package com.firework.client.Implementations.Utill.Render;

import com.firework.client.Features.Modules.Render.ESP;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static ICamera camera = new Frustum();

    public static float colorcalc(int c, int location) {
        return (float)(c >> location & 0xFF) / 255.0f;
    }

    public static void trace(Minecraft mc, Entity e, float partialTicks, int mode) {
        if (mc.getRenderManager().renderViewEntity != null) {
            GL11.glDisable(2929);
            GL11.glDisable(2896);
            GL11.glLineWidth(2.0f);
            GL11.glPushMatrix();
            GL11.glDepthMask(false);
            GL11.glColor4d(0.0, mode == 1 ? 1.0 : 0.0, 0.0, 1.0);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glBegin(1);
            RenderManager r = mc.getRenderManager();
            Vec3d v = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(mc.player.rotationPitch))).rotateYaw(-((float)Math.toRadians(mc.player.rotationYaw)));
            GL11.glVertex3d(v.x, (double)mc.player.getEyeHeight() + v.y, v.z);
            double x = e.lastTickPosX + (e.posX - e.lastTickPosX) * (double)partialTicks;
            double y = e.lastTickPosY + (e.posY - e.lastTickPosY) * (double)partialTicks;
            double z = e.lastTickPosZ + (e.posZ - e.lastTickPosZ) * (double)partialTicks;
            GL11.glVertex3d(x - r.viewerPosX, y - r.viewerPosY + 0.25, z - r.viewerPosZ);
            GL11.glEnd();
            GL11.glDepthMask(true);
            GL11.glEnable(2929);
            GL11.glEnable(3553);
            GL11.glPopMatrix();
        }
    }

    public static void FillLine(Entity entity, AxisAlignedBB box) {
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glLineWidth(2.0f);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        RenderGlobal.renderFilledBox((AxisAlignedBB)box, (float)ESP.playerColor1.getValue().toRGB().getRed(), (float)ESP.playerColor1.getValue().toRGB().getGreen(), (float)ESP.playerColor1.getValue().toRGB().getBlue(), (float)0.3f);
        RenderGlobal.drawSelectionBoundingBox((AxisAlignedBB)box, (float)ESP.playerColor2.getValue().toRGB().getRed(), (float)ESP.playerColor2.getValue().toRGB().getGreen(), (float)ESP.playerColor2.getValue().toRGB().getBlue(), (float)0.8f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
    }

    public static void renderEntity(EntityLivingBase entity, int scale, int posX, int posY) {
        GlStateManager.enableTexture2D();
        GlStateManager.depthMask((boolean)true);
        GL11.glPushAttrib(524288);
        GL11.glDisable(3089);
        GlStateManager.clear((int)256);
        GL11.glPopAttrib();
        GlStateManager.enableDepth();
        GlStateManager.disableAlpha();
        GlStateManager.pushMatrix();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GuiInventory.drawEntityOnScreen((int)posX, (int)posY, (int)scale, (float)1.0f, (float)1.0f, (EntityLivingBase)entity);
        GlStateManager.popMatrix();
        GlStateManager.disableDepth();
        GlStateManager.depthMask((boolean)false);
    }

    public static void blockESP(BlockPos blockPos) {
        GL11.glPushMatrix();
        double x = (double)blockPos.getX() - Minecraft.getMinecraft().getRenderManager().viewerPosX;
        double y = (double)blockPos.getY() - Minecraft.getMinecraft().getRenderManager().viewerPosY;
        double z = (double)blockPos.getZ() - Minecraft.getMinecraft().getRenderManager().viewerPosZ;
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        RenderGlobal.renderFilledBox((AxisAlignedBB)new AxisAlignedBB(x, y, z, x + 1.0, y + 1.0, z + 1.0), (float)1.0f, (float)0.0f, (float)0.0f, (float)0.5f);
        GL11.glEnable(3553);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

    static {
        camera = new Frustum();
    }
}
