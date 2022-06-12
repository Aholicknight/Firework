package com.firework.client.Features.Modules.Misc;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import java.util.Comparator;
import java.util.Objects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

@ModuleArgs(name="AutoNametag", category=Module.Category.MISC)
public class AutoNametag
extends Module {
    public Setting<Double> Radius = new Setting<Double>("Radius", 4.0, this, 0.0, 10.0);
    public Setting<Boolean> ReplaceOldNames = new Setting<Boolean>("ReplaceOldNames", false, this);
    public Setting<Boolean> AutoSwitch = new Setting<Boolean>("AutoSwitch", false, this);
    public Setting<Boolean> WithersOnly = new Setting<Boolean>("WithersOnly", false, this);

    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event) {
        ItemStack name;
        if (this.mc.currentScreen != null) {
            return;
        }
        if (!(this.mc.player.getHeldItemMainhand().getItem() instanceof ItemNameTag)) {
            int i1 = -1;
            if (this.AutoSwitch.getValue().booleanValue()) {
                for (int i = 0; i < 9; ++i) {
                    ItemStack item = this.mc.player.inventory.getStackInSlot(i);
                    if (item.isEmpty() || !(item.getItem() instanceof ItemNameTag) || !item.hasDisplayName()) continue;
                    this.mc.player.inventory.currentItem = i1 = i;
                    this.mc.playerController.updateController();
                    break;
                }
            }
            if (i1 == -1) {
                return;
            }
        }
        if (!(name = this.mc.player.getHeldItemMainhand()).hasDisplayName()) {
            return;
        }
        EntityLivingBase l_Entity = this.mc.world.loadedEntityList.stream().filter(p_Entity -> this.IsValidEntity((Entity)p_Entity, name.getDisplayName())).map(p_Entity -> (EntityLivingBase)p_Entity).min(Comparator.comparing(p_Entity -> Float.valueOf(this.mc.player.getDistance((Entity)p_Entity)))).orElse(null);
        if (l_Entity != null) {
            double[] lPos = AutoNametag.calculateLookAt(l_Entity.posX, l_Entity.posY, l_Entity.posZ, this.mc.player);
            MessageUtil.sendClientMessage(String.format("Gave %s the nametag of %s", l_Entity.getName(), name.getDisplayName()), -1117);
            this.mc.player.rotationYawHead = (float)lPos[0];
            Objects.requireNonNull(this.mc.getConnection()).sendPacket(new CPacketUseEntity(l_Entity, EnumHand.MAIN_HAND));
        }
    }

    public static double[] calculateLookAt(double px, double py, double pz, EntityPlayer me) {
        double dirx = me.posX - px;
        double diry = me.posY - py;
        double dirz = me.posZ - pz;
        double len = Math.sqrt(dirx * dirx + diry * diry + dirz * dirz);
        double pitch = Math.asin(diry /= len);
        double yaw = Math.atan2(dirz /= len, dirx /= len);
        pitch = pitch * 180.0 / Math.PI;
        yaw = yaw * 180.0 / Math.PI;
        return new double[]{yaw += 90.0, pitch};
    }

    private boolean IsValidEntity(Entity entity, String pName) {
        if (!(entity instanceof EntityLivingBase)) {
            return false;
        }
        if ((double)entity.getDistance(this.mc.player) > this.Radius.getValue()) {
            return false;
        }
        if (entity instanceof EntityPlayer) {
            return false;
        }
        if (!entity.getCustomNameTag().isEmpty() && !this.ReplaceOldNames.getValue().booleanValue()) {
            return false;
        }
        if (this.ReplaceOldNames.getValue().booleanValue() && !entity.getCustomNameTag().isEmpty() && entity.getName().equals(pName)) {
            return false;
        }
        return this.WithersOnly.getValue() == false || entity instanceof EntityWither;
    }
}
