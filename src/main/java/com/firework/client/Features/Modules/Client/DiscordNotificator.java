package com.firework.client.Features.Modules.Client;

import com.firework.client.Features.CommandsSystem.CommandManager;
import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import com.firework.client.Implementations.Utill.Client.DiscordUtil;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class DiscordNotificator
extends Module {
    public static Setting<Boolean> enabled = null;
    public static Setting<Boolean> death = null;
    public static Setting<Boolean> notify2b2t = null;
    public static Setting<Boolean> serverConnection = null;
    public static Setting<Boolean> chatListner = null;
    public static String webhook = "";

    public DiscordNotificator() {
        super("DiscordNotificator", Module.Category.CLIENT);
        enabled = this.isEnabled;
        notify2b2t = new Setting<Boolean>("Queue notify", true, this);
        death = new Setting<Boolean>("DeathNotificator", true, this);
        serverConnection = new Setting<Boolean>("ServerConnection", true, this);
        chatListner = new Setting<Boolean>("ChatListner", true, this);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    DiscordUtil.sendMsg("```Discord Notificator module already works```", webhook);
                }
                catch (Exception e) {
                    MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                }
            }
        }).start();
    }

    @SubscribeEvent
    public void queueNotify(final ClientChatReceivedEvent e) {
        if (notify2b2t.getValue().booleanValue() && e.getMessage().getUnformattedText().contains("position")) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        DiscordUtil.sendMsg("@here```" + e.getMessage() + "```", webhook);
                    }
                    catch (Exception e2) {
                        MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                    }
                }
            }).start();
        }
    }

    @SubscribeEvent
    public void onUrina(ClientChatReceivedEvent e) {
        final String msg = e.getMessage().getUnformattedText();
        if (chatListner.getValue().booleanValue()) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        DiscordUtil.sendMsg("```" + msg + "```", webhook);
                    }
                    catch (Exception e) {
                        MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                    }
                }
            }).start();
        }
    }

    @SubscribeEvent
    public void onBebra(FMLNetworkEvent.ClientDisconnectionFromServerEvent e) {
        if (serverConnection.getValue().booleanValue()) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        DiscordUtil.sendMsg("```U are disconnected from server " + Minecraft.getMinecraft().getCurrentServerData().serverIP + "```", webhook);
                    }
                    catch (Exception e) {
                        MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                    }
                }
            }).start();
        }
    }

    @SubscribeEvent
    public void onBebra1(FMLNetworkEvent.ClientConnectedToServerEvent e) {
        if (serverConnection.getValue().booleanValue()) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        DiscordUtil.sendMsg("```U are connected to server " + Minecraft.getMinecraft().getCurrentServerData().serverIP + "```", webhook);
                    }
                    catch (Exception e) {
                        MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                    }
                }
            }).start();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    DiscordUtil.sendMsg("```Discord Notificator is toggeled off```", webhook);
                }
                catch (Exception e) {
                    MessageUtil.sendError("Webhook is invalid, use " + CommandManager.prefix + "webhook webhook link to link ur webhook", -1117);
                }
            }
        }).start();
    }
}
