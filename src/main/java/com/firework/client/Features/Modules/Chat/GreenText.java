package com.firework.client.Features.Modules.Chat;

import com.firework.client.Features.Modules.Module;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GreenText
extends Module {
    String GREATERTHAN = ">";

    public GreenText() {
        super("GreenText", Module.Category.CHAT);
    }

    @SubscribeEvent
    public void onChat(ClientChatEvent event) {
        if (event.getMessage().startsWith("/") || event.getMessage().startsWith(".") || event.getMessage().startsWith(",") || event.getMessage().startsWith("-") || event.getMessage().startsWith("$") || event.getMessage().startsWith("*")) {
            return;
        }
        event.setMessage(this.GREATERTHAN + event.getMessage());
    }
}
