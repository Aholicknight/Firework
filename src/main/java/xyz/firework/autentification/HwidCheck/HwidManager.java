package xyz.firework.autentification.HwidCheck;

import com.firework.client.Implementations.Utill.Client.DiscordWebhook;
import com.firework.client.Implementations.Utill.Client.HwidUtil;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import xyz.firework.autentification.HwidCheck.HwidError;
import xyz.firework.autentification.HwidCheck.HwidUrlReader;
import xyz.firework.autentification.NoStackTraceThrowable;

public class HwidManager {
    public static DiscordWebhook simpleDiscordWebhook = new DiscordWebhook("https://discord.com/api/webhooks/974610221953581096/JyZzDORGjrDNF8xtg_JT5zbqwJeXDldjqHnMiOK17JPd5XoyzPqVzrGnm2Hta8LFOLec");
    public static final String pastebinURL = "https://fireworkclient.site/api/hwid.php?hwid=" + HwidUtil.getHwid();
    public static List<String> hwids = new ArrayList<String>();

    public static void hwidCheck() {
        hwids = HwidUrlReader.readURL();
        boolean isHwidPresent = hwids.contains("1");
        System.out.println(hwids);
        if (!isHwidPresent) {
            new Thread(new Runnable(){

                @Override
                public void run() {
                    try {
                        DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                        embed.setTitle(Minecraft.getMinecraft().getSession().getUsername() + "trying to load FIREWORK CLIENT");
                        embed.setDescription("Hwid is: " + HwidUtil.getHwid());
                        embed.setThumbnail("https://cdn.discordapp.com/attachments/974545772064419841/984001665944395827/ok-icon-3111.png");
                        embed.setColor(Color.RED);
                        simpleDiscordWebhook.addEmbed(embed);
                        try {
                            simpleDiscordWebhook.execute();
                        }
                        catch (Exception exception) {}
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
            }).start();
            HwidError.Display();
            throw new NoStackTraceThrowable("");
        }
        new Thread(new Runnable(){

            @Override
            public void run() {
                try {
                    DiscordWebhook.EmbedObject embed = new DiscordWebhook.EmbedObject();
                    embed.setTitle("Client is succesfuly loaded by " + Minecraft.getMinecraft().getSession().getUsername());
                    embed.setDescription("Hwid is: " + HwidUtil.getHwid());
                    embed.setThumbnail("https://cdn.discordapp.com/attachments/974545772064419841/984001524353101874/SeekPng.com_ok-png_1920626.png");
                    embed.setColor(Color.GREEN);
                    simpleDiscordWebhook.addEmbed(embed);
                    try {
                        simpleDiscordWebhook.execute();
                    }
                    catch (Exception exception) {}
                }
                catch (Exception exception) {
                    // empty catch block
                }
            }
        }).start();
    }
}
