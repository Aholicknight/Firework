package com.firework.client.Features.CommandsSystem.Commands.Fun;

import com.firework.client.Features.CommandsSystem.Command;
import com.firework.client.Features.CommandsSystem.CommandManifest;
import com.firework.client.Features.Modules.Client.Notifications;
import com.firework.client.Firework;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import java.util.Random;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;

@CommandManifest(label="dupe", aliases={"dp"})
public class DupeCommand
extends Command {
    private final Random random = new Random();

    @Override
    public void execute(String[] args) {
        WorldClient world = Firework.minecraft.world;
        Notifications.notificate();
        if (Firework.minecraft.player == null || world == null) {
            return;
        }
        ItemStack itemStack = Firework.minecraft.player.getHeldItemMainhand();
        if (itemStack.isEmpty()) {
            MessageUtil.sendError("You need to hold an item in hand to dupe!", -1117);
            return;
        }
        int count = this.random.nextInt(31) + 1;
        for (int i = 0; i <= count; ++i) {
            EntityItem entityItem = Firework.minecraft.player.dropItem(itemStack.copy(), false, true);
            if (entityItem == null) continue;
            world.addEntityToWorld(entityItem.getEntityId(), entityItem);
        }
        int total = count * itemStack.getCount();
        MessageUtil.sendClientMessage("I just used the dupe and got " + total + " " + itemStack.getDisplayName(), -1117);
    }
}
