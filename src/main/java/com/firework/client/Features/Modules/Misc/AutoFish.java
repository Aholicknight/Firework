package com.firework.client.Features.Modules.Misc;

import com.firework.client.Features.CommandsSystem.CommandManager;
import com.firework.client.Features.Modules.Client.DiscordNotificator;
import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Events.PacketEvent;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import com.firework.client.Implementations.Utill.Client.DiscordWebhook;
import java.awt.Color;
import java.util.Arrays;
import java.util.Random;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketSoundEffect;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.entity.player.ItemFishedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleArgs(name="AutoFish", category=Module.Category.MISC)
public class AutoFish
extends Module {
    DiscordWebhook simpleDiscordWebhook;
    Color embedColor = new Color(191, 10, 10);
    private final Random random = new Random();
    public Setting<Boolean> enabled = this.isEnabled;
    public Setting<String> mode = new Setting<String>("Mode", "Normal", (Module)this, Arrays.asList("Normal", "Advanced"));
    public Setting<String> swith = new Setting<String>("Switch", "Normal", (Module)this, Arrays.asList("Normal", "Silent", "None"));
    public Setting<Boolean> swing = new Setting<Boolean>("Swing", true, this);
    public Setting<Boolean> antiAfk = new Setting<Boolean>("AntiAFK", true, this);
    public boolean isWebhookPresent = false;

    @Override
    public void onEnable() {
        super.onEnable();
        if (this.mode.getValue().equals("Normal")) {
            MessageUtil.sendClientMessage("You need to press right click to start fishing", false);
        }
        if (this.mode.getValue().equals("Advanced")) {
            MessageUtil.sendClientMessage("You need to press right click to start fishing", false);
            this.sendEnabledMsg();
        }
        if (!this.mc.player.inventory.getCurrentItem().getItem().equals(Items.FISHING_ROD) && this.swith.getValue().equals("Normal")) {
            this.makeNormalSwitch();
        } else if (this.mc.player.getHeldItemMainhand().getItem() == null || !this.mc.player.inventory.getCurrentItem().getItem().equals(Items.FISHING_ROD)) {
            MessageUtil.sendError("You need to hold Fishing rod in mainhand!", -1117);
            this.enabled.setValue(false);
        }
    }

    @SubscribeEvent
    public void antiAFK(PacketEvent.Receive e) {
        if (this.antiAfk.getValue().booleanValue()) {
            int randomNumber = this.random.nextInt(500);
            switch (randomNumber) {
                case 0: {
                    this.pressAndUnpress(this.mc.gameSettings.keyBindLeft.getKeyCode(), this.random.nextInt(200));
                    break;
                }
                case 100: {
                    this.pressAndUnpress(this.mc.gameSettings.keyBindRight.getKeyCode(), this.random.nextInt(200));
                    break;
                }
                case 200: {
                    if (!this.mc.player.onGround) break;
                    this.mc.player.jump();
                    break;
                }
                case 300: {
                    this.pressAndUnpress(this.mc.gameSettings.keyBindSneak.getKeyCode(), this.random.nextInt(200));
                }
            }
        }
    }

    @Override
    public void onTick() {
        super.onTick();
        if (this.swing.getValue().booleanValue()) {
            this.mc.player.isSwingInProgress = false;
            this.mc.player.swingProgressInt = 0;
            this.mc.player.swingProgress = 0.0f;
            this.mc.player.prevSwingProgress = 0.0f;
        }
        if (this.mode.getValue().equals("Advanced")) {
            this.putIsWebhookLinked();
            if (!this.isWebhookPresent) {
                MessageUtil.sendError("Webhook is not present, use " + CommandManager.prefix + "webhook to set webhook link", -1117);
                this.enabled.setValue(false);
            }
        }
        if (this.swith.getValue().equals("Normal")) {
            this.makeNormalSwitch();
        } else if (this.swith.getValue().equals("Silent")) {
            System.out.println("silent");
        } else if (this.mc.player.getHeldItemMainhand().getItem() == null || !this.mc.player.inventory.getCurrentItem().getItem().equals(Items.FISHING_ROD)) {
            MessageUtil.sendError("You need to hold Fishing rod in mainhand!", -1117);
            this.enabled.setValue(false);
        }
    }

    @SubscribeEvent
    public void onFishedItem(ItemFishedEvent event) {
        final ItemStack item = (ItemStack)event.getDrops().get(0);
        if (this.mode.getValue().equals("Advanced")) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                        embed.setTitle("You fished an item: " + item.getDisplayName());
                        embed.setDescription("Enchants: " + item.getEnchantmentTagList());
                        embed.setThumbnail("https://media.discordapp.net/attachments/962299429795295282/980160689568120842/98be7b5d87318d89133a7384346cf787.jpg");
                        embed.setColor(AutoFish.this.embedColor);
                        AutoFish.this.simpleDiscordWebhook.addEmbed(embed);
                        try {
                            AutoFish.this.simpleDiscordWebhook.execute();
                        }
                        catch (Exception exception) {}
                    }
                    catch (Exception e) {
                        MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                    }
                }
            }).start();
        }
    }

    @SubscribeEvent
    public void autoFish(PacketEvent e) {
        SPacketSoundEffect packet;
        if ((this.mode.getValue().equals("Advanced") && this.isWebhookPresent || this.mode.getValue().equals("Normal")) && e.getPacket() instanceof SPacketSoundEffect && (packet = (SPacketSoundEffect)((Object)e.getPacket())).getSound().equals(SoundEvents.ENTITY_BOBBER_SPLASH)) {
            if (this.mc.player.getHeldItemMainhand().getItem() instanceof ItemFishingRod) {
                this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
                this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                this.mc.player.swingArm(EnumHand.MAIN_HAND);
            }
            if (this.mc.player.getHeldItemOffhand().getItem() instanceof ItemFishingRod) {
                this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                this.mc.player.swingArm(EnumHand.OFF_HAND);
                this.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.OFF_HAND));
                this.mc.player.swingArm(EnumHand.OFF_HAND);
            }
        }
    }

    public void putIsWebhookLinked() {
        this.isWebhookPresent = !DiscordNotificator.webhook.equals("");
        this.simpleDiscordWebhook = new DiscordWebhook(DiscordNotificator.webhook);
    }

    public void makeNormalSwitch() {
        if (this.mc.player.getHeldItemMainhand().getItem() == null || !this.mc.player.inventory.getCurrentItem().getItem().equals(Items.FISHING_ROD)) {
            for (int j = 0; j < 9; ++j) {
                if (this.mc.player.inventory.getStackInSlot(j) == null || this.mc.player.inventory.getStackInSlot(j).getCount() == 0 || !(this.mc.player.inventory.getStackInSlot(j).getItem() instanceof ItemFishingRod)) continue;
                this.mc.player.inventory.currentItem = j;
                break;
            }
        }
    }

    public void sendEnabledMsg() {
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                    embed.setTitle("AutoFish module mod advanced is enabled!");
                    embed.setThumbnail("https://media.discordapp.net/attachments/962299429795295282/980160689568120842/98be7b5d87318d89133a7384346cf787.jpg");
                    embed.setColor(AutoFish.this.embedColor);
                    AutoFish.this.simpleDiscordWebhook.addEmbed(embed);
                    try {
                        AutoFish.this.simpleDiscordWebhook.execute();
                    }
                    catch (Exception exception) {}
                }
                catch (Exception e) {
                    MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                }
            }
        }).start();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (this.mode.getValue().equals("Advanced")) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                        embed.setTitle("AutoFish module mod advanced is disabled!");
                        embed.setThumbnail("https://media.discordapp.net/attachments/962299429795295282/980160689568120842/98be7b5d87318d89133a7384346cf787.jpg");
                        embed.setColor(AutoFish.this.embedColor);
                        AutoFish.this.simpleDiscordWebhook.addEmbed(embed);
                        try {
                            AutoFish.this.simpleDiscordWebhook.execute();
                        }
                        catch (Exception exception) {}
                    }
                    catch (Exception e) {
                        MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                    }
                }
            }).start();
        }
    }

    private void pressAndUnpress(int key, int delay) {
        new Thread(() -> {
            try {
                KeyBinding.setKeyBindState((int)key, (boolean)true);
                Thread.sleep(delay);
                KeyBinding.setKeyBindState((int)key, (boolean)false);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
