package com.firework.client.Features.Modules.Combat;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Render.HSLColor;
import java.util.Arrays;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemEndCrystal;
import net.minecraft.util.EnumHand;

@ModuleArgs(name="AutoCrystal", category=Module.Category.COMBAT)
public class AutoCrystal
extends Module {
    public Setting<String> timing = new Setting<String>("Timing", "Vanilla", (Module)this, Arrays.asList("Vanilla", "Strict", "Sequential"));
    public Setting<String> rotates = new Setting<String>("Rotates", "Normal", (Module)this, Arrays.asList("Normal", "Packet", "None"));
    public Setting<Double> placeRange = new Setting<Double>("PlaceRange", 5.0, this, 1.0, 10.0);
    public Setting<Double> breakRange = new Setting<Double>("BreakRange", 5.0, this, 1.0, 10.0);
    public Setting<Double> WallPlaceRange = new Setting<Double>("WallPlaceRange", 5.0, this, 1.0, 10.0);
    public Setting<Double> WallBreakRange = new Setting<Double>("WallBreakRange", 5.0, this, 1.0, 10.0);
    public Setting<Double> placeDelay = new Setting<Double>("PlaceDelay", 20.0, this, 1.0, 100.0);
    public Setting<Double> breakDelay = new Setting<Double>("BreakDelay", 20.0, this, 1.0, 100.0);
    public Setting<Boolean> raytrace = new Setting<Boolean>("Raytrace", false, this);
    public Setting<Double> minDmg = new Setting<Double>("MinimalDamage", 5.0, this, 1.0, 20.0);
    public Setting<Double> maxSelfDmg = new Setting<Double>("MaximalSelfDamage", 5.0, this, 1.0, 20.0);
    public Setting<String> switcch = new Setting<String>("Switch", "Normal", (Module)this, Arrays.asList("Normal", "Silent", "None"));
    public Setting<String> swing = new Setting<String>("Swing", "Right", (Module)this, Arrays.asList("Right", "Left", "None"));
    public Setting<HSLColor> color = new Setting<HSLColor>("RenderColor", new HSLColor(1.0f, 54.0f, 43.0f), this);

    @Override
    public void onTick() {
        super.onTick();
        if (this.switcch.getValue().equals("Normal")) {
            if (!this.mc.player.getHeldItemOffhand().getItem().equals(Items.END_CRYSTAL)) {
                for (Entity e : this.mc.world.loadedEntityList) {
                    if (!(e instanceof EntityPlayer) || e == this.mc.player || !((double)this.mc.player.getDistance(e) <= this.placeRange.getValue()) || !((double)this.mc.player.getDistance(e) <= this.breakRange.getValue())) continue;
                    this.makeNormalSwitch();
                }
            }
        } else if (this.switcch.getValue().equals("Silent")) {
            for (Entity e : this.mc.world.loadedEntityList) {
                if (!(e instanceof EntityPlayer) || e == this.mc.player || !((double)this.mc.player.getDistance(e) <= this.placeRange.getValue()) || !((double)this.mc.player.getDistance(e) <= this.breakRange.getValue())) continue;
                this.makeSilentSwitch();
            }
        }
        if (this.swing.getValue().equals("Right")) {
            this.mc.player.swingingHand = EnumHand.MAIN_HAND;
        } else if (this.swing.getValue().equals("Left")) {
            this.mc.player.swingingHand = EnumHand.OFF_HAND;
        } else if (this.swing.getValue().equals("None")) {
            this.mc.player.isSwingInProgress = false;
            this.mc.player.swingProgressInt = 0;
            this.mc.player.swingProgress = 0.0f;
            this.mc.player.prevSwingProgress = 0.0f;
        }
    }

    public void makeNormalSwitch() {
        if (this.mc.player.getHeldItemMainhand().getItem() == null || !this.mc.player.inventory.getCurrentItem().getItem().equals(Items.END_CRYSTAL)) {
            for (int j = 0; j < 9; ++j) {
                if (this.mc.player.inventory.getStackInSlot(j) == null || this.mc.player.inventory.getStackInSlot(j).getCount() == 0 || !(this.mc.player.inventory.getStackInSlot(j).getItem() instanceof ItemEndCrystal)) continue;
                this.mc.player.inventory.currentItem = j;
                break;
            }
        }
    }

    public void makeSilentSwitch() {
    }
}
