package com.firework.client.Implementations.Managers.Parser;

import com.firework.client.Firework;
import com.firework.client.Implementations.Utill.Client.DiscordUtil;
import java.awt.Desktop;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

public class JsonParser {
    public static String DISORD_WEBHOOK = "";

    public static void parse() {
        Desktop desktop = Desktop.getDesktop();
        JSONObject obj = new JSONObject();
        obj.put("Firework", "bebra");
        File theDir = new File(Firework.FIREWORK_DIRECTORY);
        if (!theDir.exists()) {
            theDir.mkdirs();
            DiscordUtil.OpenServer();
            try (FileWriter file = new FileWriter(Firework.FIREWORK_DIRECTORY + "Firework.json");){
                file.write(obj.toJSONString());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createWebhookJsonFile() {
        JSONObject web = new JSONObject();
        web.put("Webhook", DISORD_WEBHOOK);
        try (FileWriter file = new FileWriter(Firework.FIREWORK_DIRECTORY + "Webhook.json");){
            file.write(web.toJSONString());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
