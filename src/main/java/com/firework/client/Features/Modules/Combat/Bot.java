package com.firework.client.Features.Modules.Combat;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import com.firework.client.Implementations.Utill.Client.MathUtil;
import com.firework.client.Implementations.Utill.Entity.EntityUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.input.Mouse;

@ModuleArgs(name="Bot", category=Module.Category.COMBAT)
public class Bot
extends Module {
    private List<EntityPlayer> bots = new ArrayList<EntityPlayer>();
    private boolean clicked = false;
    public Setting<Boolean> Trigger = new Setting<Boolean>("TriggerBot", true, this);
    public Setting<Boolean> Aim = new Setting<Boolean>("AimBot", true, this);
    public Setting<Boolean> BowAim = new Setting<Boolean>("BowAimBot", true, this);
    public Setting<Boolean> BowSpam = new Setting<Boolean>("BowSpamBot", true, this);
    public Setting<Double> BowSpamSpeed = new Setting<Double>("BowSpamSpeed", 1.0, this, 1.0, 30.0);
    public Setting<String> mode = new Setting<String>("AntiBot", "Zamorozka", (Module)this, Arrays.asList("None", "Zamorozka"));

    @Override
    public void onTick() {
        float[] angels;
        Object pos;
        float dis;
        EntityPlayer player;
        super.onTick();
        RayTraceResult objectMouseOver = Minecraft.getMinecraft().objectMouseOver;
        if (this.mode.equals("Zamorozka") && this.mc.currentScreen == null && Mouse.isButtonDown(0)) {
            if (!this.clicked) {
                this.clicked = true;
                RayTraceResult result = this.mc.objectMouseOver;
                if (result == null || result.typeOfHit != RayTraceResult.Type.ENTITY) {
                    return;
                }
                Entity entity = this.mc.objectMouseOver.entityHit;
                if (entity == null || !(entity instanceof EntityPlayer)) {
                    return;
                }
                MessageUtil.sendClientMessage("[AntiBot] Current target is " + entity.getName(), true);
            } else {
                this.clicked = false;
            }
        }
        if (this.Trigger.getValue().booleanValue() && objectMouseOver != null && objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY && Minecraft.getMinecraft().player.getCooledAttackStrength(0.0f) == 1.0f) {
            Minecraft.getMinecraft().playerController.attackEntity(Minecraft.getMinecraft().player, objectMouseOver.entityHit);
            Minecraft.getMinecraft().player.swingArm(EnumHand.MAIN_HAND);
            Minecraft.getMinecraft().player.resetCooldown();
        }
        if (this.Aim.getValue().booleanValue()) {
            player = null;
            float tickDis = 100.0f;
            for (EntityPlayer p : this.mc.world.playerEntities) {
                if (p instanceof EntityPlayerSP || !((dis = p.getDistance(this.mc.player)) < tickDis)) continue;
                tickDis = dis;
                player = p;
            }
            if (player != null) {
                pos = EntityUtil.getInterpolatedPos(player, this.mc.getRenderPartialTicks());
                angels = MathUtil.calcAngle(EntityUtil.getInterpolatedPos(this.mc.player, this.mc.getRenderPartialTicks()), (Vec3d)pos);
                this.mc.player.rotationYaw = angels[0];
                this.mc.player.rotationPitch = angels[1];
            }
        }
        if (this.BowAim.getValue().booleanValue() && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && this.mc.player.isHandActive() && this.mc.player.getItemInUseMaxCount() >= 3) {
            player = null;
            float tickDis = 100.0f;
            for (EntityPlayer p : this.mc.world.playerEntities) {
                if (p instanceof EntityPlayerSP || !((dis = p.getDistance(this.mc.player)) < tickDis)) continue;
                tickDis = dis;
                player = p;
            }
            if (player != null) {
                pos = EntityUtil.getInterpolatedPos(player, this.mc.getRenderPartialTicks());
                angels = MathUtil.calcAngle(EntityUtil.getInterpolatedPos(this.mc.player, this.mc.getRenderPartialTicks()), (Vec3d)pos);
                this.mc.player.rotationYaw = angels[0];
                this.mc.player.rotationPitch = angels[1];
            }
        }
        if (this.BowSpam.getValue().booleanValue() && this.mc.player.getHeldItemMainhand().getItem() instanceof ItemBow && this.mc.player.isHandActive() && (double)this.mc.player.getItemInUseMaxCount() >= this.BowSpamSpeed.getValue()) {
            this.mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, this.mc.player.getHorizontalFacing()));
            this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(this.mc.player.getActiveHand()));
            this.mc.player.stopActiveHand();
        }
    }
}
