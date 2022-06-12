package com.firework.client.Features.Modules.Misc;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleArgs;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import com.firework.client.Implementations.Utill.Client.SoundUtill;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@ModuleArgs(name="AutoRespawn", category=Module.Category.MISC)
public class AutoRespawn
extends Module {
    private static Minecraft mc = Minecraft.getMinecraft();
    public static Setting<Boolean> enabled = null;
    public Setting<Boolean> respawn = new Setting<Boolean>("Respawn", true, this);
    public Setting<Boolean> antiDeathScreen = new Setting<Boolean>("AntiDeathScreen", true, this);
    public Setting<Boolean> deathCoords = new Setting<Boolean>("DeathCords", true, this);
    public Setting<Boolean> clipBoard = new Setting<Boolean>("Clipboard", true, this);
    public Setting<Boolean> deathSounds = new Setting<Boolean>("DeathSounds", true, this);

    public AutoRespawn() {
        enabled = this.isEnabled;
    }

    @SubscribeEvent
    public void onDisplayDeathScreen(GuiOpenEvent event) {
        if (event.getGui() instanceof GuiGameOver) {
            Clipboard clipboard;
            StringSelection contents;
            if (this.deathCoords.getValue().booleanValue() && event.getGui() instanceof GuiGameOver) {
                if (this.clipBoard.getValue().booleanValue()) {
                    MessageUtil.sendClientMessage(String.format(ChatFormatting.BLUE + "[COPIED TO CLIPBOARD] " + ChatFormatting.RESET + " You died at x %d y %d z %d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ), -1117);
                } else {
                    MessageUtil.sendClientMessage(String.format(" You died at x %d y %d z %d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ), -1117);
                }
                contents = new StringSelection(String.format("You died at x %d y %d z %d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ));
                if (this.clipBoard.getValue().booleanValue()) {
                    clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(contents, null);
                }
            }
            if (this.respawn.getValue() != false && AutoRespawn.mc.player.getHealth() <= 0.0f || this.antiDeathScreen.getValue().booleanValue() && AutoRespawn.mc.player.getHealth() > 0.0f) {
                event.setCanceled(true);
                AutoRespawn.mc.player.respawnPlayer();
                contents = new StringSelection(String.format("You died at x %d y %d z %d", (int)AutoRespawn.mc.player.posX, (int)AutoRespawn.mc.player.posY, (int)AutoRespawn.mc.player.posZ));
                if (this.clipBoard.getValue().booleanValue()) {
                    clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(contents, null);
                }
            }
            if (this.deathSounds.getValue().booleanValue() && event.getGui() instanceof GuiGameOver) {
                SoundUtill.playSound(new ResourceLocation("firework/audio/loaded.wav"));
            }
        }
    }
}
