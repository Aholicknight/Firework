package com.firework.client.Features.CommandsSystem.Commands.Fun;

import com.firework.client.Features.CommandsSystem.Command;
import com.firework.client.Features.CommandsSystem.CommandManifest;
import com.firework.client.Features.Modules.Client.Notifications;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;

@CommandManifest(label="penis")
public class PenisCommand
extends Command {
    @Override
    public void execute(String[] args) {
        Notifications.notificate();
        double random = Math.random();
        double x = random * 100.0;
        int y = (int)x + 1;
        MessageUtil.sendClientMessage("Your penis size is: " + y, -1117);
    }
}
