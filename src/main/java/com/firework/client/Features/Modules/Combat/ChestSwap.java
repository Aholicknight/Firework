package com.firework.client.Features.Modules.Combat;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemElytra;
import net.minecraft.item.ItemStack;

@ModuleArgs(name="ChestSwap", category=Module.Category.COMBAT)
public class ChestSwap
extends Module {
    public Setting<Boolean> PreferElytra = new Setting<Boolean>("PreferElytra", false, this);
    public Setting<Boolean> Curse = new Setting<Boolean>("Curse ", false, this);

    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mc.player == null) {
            return;
        }
        ItemStack l_ChestSlot = this.mc.player.inventoryContainer.getSlot(6).getStack();
        if (l_ChestSlot.isEmpty()) {
            int l_Slot = this.FindChestItem(this.PreferElytra.getValue());
            if (!this.PreferElytra.getValue().booleanValue() && l_Slot == -1) {
                l_Slot = this.FindChestItem(true);
            }
            if (l_Slot != -1) {
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, this.mc.player);
                this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, this.mc.player);
            }
            this.isEnabled.setValue(false);
            return;
        }
        int l_Slot = this.FindChestItem(l_ChestSlot.getItem() instanceof ItemArmor);
        if (l_Slot != -1) {
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, 6, 0, ClickType.PICKUP, this.mc.player);
            this.mc.playerController.windowClick(this.mc.player.inventoryContainer.windowId, l_Slot, 0, ClickType.PICKUP, this.mc.player);
        }
        this.isEnabled.setValue(false);
    }

    private int FindChestItem(boolean p_Elytra) {
        int slot = -1;
        float damage = 0.0f;
        for (int i = 0; i < this.mc.player.inventoryContainer.getInventory().size(); ++i) {
            ItemStack s;
            if (i == 0 || i == 5 || i == 6 || i == 7 || i == 8 || (s = (ItemStack)this.mc.player.inventoryContainer.getInventory().get(i)) == null || s.getItem() == Items.AIR) continue;
            if (s.getItem() instanceof ItemArmor) {
                boolean cursed;
                ItemArmor armor = (ItemArmor)((Object)s.getItem());
                if (armor.armorType != EntityEquipmentSlot.CHEST) continue;
                float currentDamage = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel((Enchantment)Enchantments.PROTECTION, (ItemStack)s);
                boolean bl = cursed = this.Curse.getValue() != false ? EnchantmentHelper.hasBindingCurse((ItemStack)s) : false;
                if (!(currentDamage > damage) || cursed) continue;
                damage = currentDamage;
                slot = i;
                continue;
            }
            if (!p_Elytra || !(s.getItem() instanceof ItemElytra)) continue;
            return i;
        }
        return slot;
    }
}
