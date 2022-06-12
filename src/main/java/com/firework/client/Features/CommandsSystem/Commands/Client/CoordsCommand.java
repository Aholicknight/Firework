package com.firework.client.Features.CommandsSystem.Commands.Client;

import com.firework.client.Features.CommandsSystem.Command;
import com.firework.client.Features.CommandsSystem.CommandManifest;
import com.firework.client.Features.Modules.Client.Notifications;
import com.firework.client.Firework;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.text.DecimalFormat;

@CommandManifest(label="coords", aliases={"cord"})
public class CoordsCommand
extends Command {
    @Override
    public void execute(String[] args) {
        Notifications.notificate();
        DecimalFormat format = new DecimalFormat("#");
        StringSelection contents = new StringSelection(format.format(Firework.minecraft.player.posX) + ", " + format.format(Firework.minecraft.player.posY) + ", " + format.format(Firework.minecraft.player.posZ));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(contents, null);
        MessageUtil.sendClientMessage("Ur coords copied to clipboard: " + Firework.minecraft.player.posX + ", " + Firework.minecraft.player.posY + ", " + Firework.minecraft.player.posZ, -11114);
    }
}
