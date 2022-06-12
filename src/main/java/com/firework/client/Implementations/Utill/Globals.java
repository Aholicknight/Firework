package com.firework.client.Implementations.Utill;

import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventBus;

public interface Globals {
    public static final Minecraft mc = Minecraft.getMinecraft();
    public static final EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;
    public static final String MESSAGE_PREFIX = ChatFormatting.DARK_PURPLE + "[Nebula]" + ChatFormatting.RESET;

    default public void sendChatMessage(String message, int id) {
        Globals.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion(new TextComponentString(MESSAGE_PREFIX).appendText(" ").appendText(message), id);
    }

    default public void sendChatMessage(String message) {
        Globals.mc.ingameGUI.getChatGUI().printChatMessage(new TextComponentString(MESSAGE_PREFIX).appendText(" ").appendText(message));
    }

    default public boolean nullCheck() {
        return Globals.mc.world == null || Globals.mc.player == null;
    }
}
