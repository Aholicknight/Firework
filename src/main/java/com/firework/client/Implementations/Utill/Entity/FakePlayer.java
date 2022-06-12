package com.firework.client.Implementations.Utill.Entity;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class FakePlayer
extends EntityOtherPlayerMP {
    public String nickname;

    public FakePlayer(UUID uuid, String nickname) {
        super(Minecraft.getMinecraft().world, new GameProfile(uuid, nickname));
        this.nickname = nickname;
        this.addMeToWorld();
    }

    private void addMeToWorld() {
        Minecraft.getMinecraft().world.addEntityToWorld(-100, this);
    }
}
