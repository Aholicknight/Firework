package com.firework.client.Features.Modules.Combat;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import java.util.Arrays;
import net.minecraft.init.Items;

@ModuleArgs(name="AutoTotem", category=Module.Category.COMBAT)
public class AutoTotem
extends Module {
    public Setting<String> hand = new Setting<String>("Mode", "MainHand", (Module)this, Arrays.asList("MainHand", "OffHand"));

    @Override
    public void onTick() {
        super.onTick();
        if (this.hand.getValue().equals("MainHand")) {
            this.makeNormalSwitch();
        } else if (this.hand.getValue().equals("OffHand")) {
            // empty if block
        }
    }

    public void makeNormalSwitch() {
        if (this.mc.player.getHeldItemMainhand().getItem() == null || !this.mc.player.inventory.getCurrentItem().getItem().equals(Items.TOTEM_OF_UNDYING)) {
            for (int j = 0; j < 9; ++j) {
                if (this.mc.player.inventory.getStackInSlot(j) == null || this.mc.player.inventory.getStackInSlot(j).getCount() == 0 || !this.mc.player.inventory.getStackInSlot(j).getItem().equals(Items.TOTEM_OF_UNDYING)) continue;
                this.mc.player.inventory.currentItem = j;
                break;
            }
        }
    }
}
