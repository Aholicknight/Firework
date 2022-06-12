package com.firework.client;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;

public class CustomDiscordRichPresence {
    private static DiscordRichPresence presence;
    private static DiscordEventHandlers handlers;
    private static Thread thread;
    private static DiscordRPC lib;

    public static void run() {
        System.out.println("[RPC] Running!");
        String id = "977837246227054613";
        presence = new DiscordRichPresence();
        handlers = new DiscordEventHandlers();
        lib.Discord_Initialize(id, handlers, true, null);
        CustomDiscordRichPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        CustomDiscordRichPresence.presence.details = "Username: " + Minecraft.getMinecraft().getSession().getUsername();
        CustomDiscordRichPresence.presence.state = "Firework Premium";
        CustomDiscordRichPresence.presence.largeImageKey = "picture";
        lib.Discord_UpdatePresence(presence);
        thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000L);
                }
                catch (InterruptedException interruptedException) {}
            }
        }, "RPC-Callback-Handler");
        thread.start();
    }

    public static void stop() {
        thread.stop();
        lib.Discord_Shutdown();
        presence = null;
        handlers = null;
    }

    public static void rerun() {
        CustomDiscordRichPresence.run();
        CustomDiscordRichPresence.stop();
        lib.Discord_UpdatePresence(presence);
    }

    static {
        lib = DiscordRPC.INSTANCE;
    }
}
