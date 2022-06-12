package com.firework.client.Features.CommandsSystem.Commands.Dirs;

import com.firework.client.Features.CommandsSystem.Command;
import com.firework.client.Features.CommandsSystem.CommandManifest;
import com.firework.client.Features.Modules.Client.Notifications;
import com.firework.client.Implementations.Utill.Chat.MessageUtil;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

@CommandManifest(label="imgur")
public class ImgurCommand
extends Command {
    private final ByteArrayOutputStream baos = new ByteArrayOutputStream();

    @Override
    public void execute(String[] args) {
        Notifications.notificate();
        Minecraft mc = Minecraft.getMinecraft();
        try {
            ResourceLocation resourceLocation = mc.player.getHeldItem(EnumHand.MAIN_HAND).getItem().getRegistryName();
            InputStream inputStream = mc.getResourceManager().getResource(new ResourceLocation(resourceLocation.getNamespace() + ":textures/items/" + resourceLocation.getPath() + ".png")).getInputStream();
            BufferedImage image = ImageIO.read(inputStream);
            MessageUtil.sendClientMessage(this.imgurUpload(image), false);
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(this.imgurUpload(image)));
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            catch (URISyntaxException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String imgurUpload(BufferedImage bufferedImage) {
        String result = "no";
        try {
            String line;
            URL url = new URL("https://api.imgur.com/3/image");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setDoOutput(true);
            con.setDoInput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Client-ID bfea9c11835d95c");
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            con.connect();
            ImageIO.write((RenderedImage)bufferedImage, "png", this.baos);
            this.baos.flush();
            byte[] imageInByte = this.baos.toByteArray();
            String encoded = Base64.getEncoder().encodeToString(imageInByte);
            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            String data = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(encoded, "UTF-8");
            wr.write(data);
            wr.flush();
            BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder stb = new StringBuilder();
            while ((line = rd.readLine()) != null) {
                stb.append(line).append("\n");
            }
            con.getResponseCode();
            wr.close();
            rd.close();
            JsonObject jsonObject = new JsonParser().parse(stb.toString()).getAsJsonObject();
            result = jsonObject.get("data").getAsJsonObject().get("link").getAsString();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
