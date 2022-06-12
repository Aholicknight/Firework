package com.firework.client.Features.Modules.Chat;

import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Managers.Parser.JsonReader;
import com.firework.client.Implementations.Settings.Setting;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.json.simple.JSONObject;
import org.lwjgl.opengl.Display;

public class Spammer
extends Module {
    private int delay = -1;
    public static String message = "Spam!";
    public Setting<Double> delaySeconds = new Setting<Double>("Delay (in seconds)", 1.3, this, 1.3, 10.0);
    public Setting<Boolean> antiFilter = new Setting<Boolean>("AntiFilter", false, this);
    public Setting<Boolean> autoWhisperer = new Setting<Boolean>("Whisperer", false, this);

    public Spammer() {
        super("Spammer", Module.Category.CHAT);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        JSONObject obj = new JSONObject();
        obj.put("Text", "Spam!");
        File theDir = new File(Firework.FIREWORK_DIRECTORY + "Spam.json");
        if (!theDir.exists()) {
            try (FileWriter file = new FileWriter(Firework.FIREWORK_DIRECTORY + "Spam.json");){
                file.write(obj.toJSONString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        JsonReader.getSpamText();
    }

    @Override
    public void onTick() {
        if (!Display.isActive()) {
            return;
        }
        this.spam();
    }

    private void spam() {
        ++this.delay;
        if ((double)this.delay >= 20.0 * this.delaySeconds.getValue()) {
            String[] messageArray = message.split("-");
            String reformattedMessage = messageArray[new Random().nextInt(messageArray.length)];
            Random random = new Random();
            if (this.antiFilter.getValue().booleanValue()) {
                reformattedMessage = reformattedMessage + " | " + random.nextInt(2048);
            }
            if (!this.autoWhisperer.getValue().booleanValue()) {
                Minecraft.getMinecraft().player.sendChatMessage(reformattedMessage);
            }
            if (this.autoWhisperer.getValue().booleanValue()) {
                for (NetworkPlayerInfo player : Minecraft.getMinecraft().getConnection().getPlayerInfoMap()) {
                    String name = Minecraft.getMinecraft().ingameGUI.getTabList().getPlayerName(player);
                    String command = "/w " + name + " " + reformattedMessage;
                    Minecraft.getMinecraft().player.sendChatMessage(command);
                }
            }
            this.delay = -1;
        }
    }
}
