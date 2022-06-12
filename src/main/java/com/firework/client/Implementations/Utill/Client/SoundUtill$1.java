package com.firework.client.Implementations.Utill.Client;

import java.io.BufferedInputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

final class SoundUtill$1
implements Runnable {
    final ResourceLocation val$rl;

    SoundUtill$1(ResourceLocation resourceLocation) {
        this.val$rl = resourceLocation;
    }

    @Override
    public void run() {
        try {
            Clip clip = AudioSystem.getClip();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(Minecraft.getMinecraft().getResourceManager().getResource(this.val$rl).getInputStream());
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
            clip.open(inputStream);
            clip.start();
        }
        catch (Exception e) {
            System.err.println(e);
        }
    }
}
