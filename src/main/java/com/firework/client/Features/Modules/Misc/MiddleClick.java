package com.firework.client.Features.Modules.Misc;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Managers.FriendManager;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import com.firework.client.Implementations.Utill.InventoryUtil;
import java.io.File;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import org.lwjgl.input.Mouse;

public class MiddleClick
extends Module {
    private static Minecraft mc = Minecraft.getMinecraft();
    private boolean clicked = false;
    public Setting<Boolean> pearl = new Setting<Boolean>("Pearl", true, this);
    public Setting<Boolean> friend = new Setting<Boolean>("Friend", true, this);
    public Setting<Boolean> notify = new Setting<Boolean>("NotifyFriend", true, this);

    public MiddleClick() {
        super("MiddleClick", Module.Category.MISC);
    }

    @Override
    public void onTick() {
        super.onTick();
        if (Mouse.isButtonDown(2)) {
            if (!this.clicked && this.friend.getValue().booleanValue()) {
                this.onClick();
            } else if (this.pearl.getValue().booleanValue()) {
                this.throwPearl();
            }
            this.clicked = true;
        } else {
            this.clicked = false;
        }
    }

    private void throwPearl() {
        boolean offhand;
        int pearlSlot = InventoryUtil.findHotbarBlock(ItemEnderPearl.class);
        boolean bl = offhand = MiddleClick.mc.player.getHeldItemOffhand().getItem() == Items.ENDER_PEARL;
        if (pearlSlot != -1 || offhand) {
            int oldslot = MiddleClick.mc.player.inventory.currentItem;
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(pearlSlot, false);
            }
            MiddleClick.mc.playerController.processRightClick(MiddleClick.mc.player, MiddleClick.mc.world, offhand ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
            if (!offhand) {
                InventoryUtil.switchToHotbarSlot(oldslot, false);
            }
        }
    }

    private void onClick() {
        Entity entity;
        RayTraceResult result = MiddleClick.mc.objectMouseOver;
        if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY && (entity = result.entityHit) instanceof EntityPlayer) {
            File theDir = new File(Firework.FIREWORK_DIRECTORY + "Friends/" + entity.getName() + ".json");
            if (theDir.exists()) {
                theDir.delete();
                MessageUtil.sendClientMessage(entity.getName() + " removed as friend!", false);
            } else {
                FriendManager.parse(entity.getName());
                MessageUtil.sendClientMessage(entity.getName() + " added as friend!", false);
                if (this.notify.getValue().booleanValue()) {
                    MiddleClick.mc.player.sendChatMessage("/w " + entity.getName() + " You are added as friend [FIREWORK CLIENT]");
                }
            }
        }
    }
}
