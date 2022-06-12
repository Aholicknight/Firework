package com.firework.client.Features.Modules.Client;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Implementations.Settings.Setting;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import com.firework.client.Implementations.Utill.Client.SoundUtill;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class Notifications
extends Module {
    public static Setting<Boolean> enabled = null;
    public static Setting<Boolean> sounds = null;
    public static Setting<Boolean> burrowNotificator = null;
    private final ConcurrentHashMap<EntityPlayer, Integer> players = new ConcurrentHashMap();
    private final List<EntityPlayer> anti_spam = new ArrayList<EntityPlayer>();

    public Notifications() {
        super("Notifications", Module.Category.CLIENT);
        enabled = this.isEnabled;
        this.isEnabled.setValue(true);
        sounds = new Setting<Boolean>("Sounds", true, this);
        burrowNotificator = new Setting<Boolean>("Burrow", true, this);
    }

    public static void notificate() {
        if (sounds.getValue().booleanValue() && enabled.getValue().booleanValue()) {
            SoundUtill.playSound(new ResourceLocation("firework/audio/pop.wav"));
        }
    }

    public void update() {
        if (this.mc.player == null || this.mc.world == null) {
            return;
        }
        for (EntityPlayer player : this.mc.world.playerEntities) {
            BlockPos pos;
            if (this.anti_spam.contains(player) || !this.mc.world.getBlockState(pos = new BlockPos(player.posX, player.posY + 0.2, player.posZ)).getBlock().equals(Blocks.OBSIDIAN)) continue;
            this.add_player(player);
            this.anti_spam.add(player);
        }
    }

    private void add_player(EntityPlayer player) {
        if (player == null) {
            return;
        }
        if (this.players.containsKey(player)) {
            int value = this.players.get(player) + 1;
            this.players.put(player, value);
            if (burrowNotificator.getValue().booleanValue()) {
                Notifications.notificate();
                MessageUtil.warning(player.getName() + " has burrowed " + value + " times", -1117);
            }
        } else {
            this.players.put(player, 1);
            if (burrowNotificator.getValue().booleanValue()) {
                Notifications.notificate();
                MessageUtil.warning(player.getName() + " has burrowed ", -1117);
            }
        }
    }
}
