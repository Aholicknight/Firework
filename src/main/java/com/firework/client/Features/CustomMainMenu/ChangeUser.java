package com.firework.client.Features.CustomMainMenu;

import com.firework.client.CustomDiscordRichPresence;
import com.firework.client.Features.CustomMainMenu.FireworkMenu;
import com.firework.client.Firework;
import com.mojang.authlib.Agent;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import java.awt.Color;
import java.io.IOException;
import java.net.Proxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class ChangeUser
extends GuiScreen {
    GuiTextField inputField;
    private static final ResourceLocation texture = new ResourceLocation("firework/textures/background.jpg");

    public void initGui() {
        int i = this.height / 4 + 48;
        this.buttonList.clear();
        this.inputField = new GuiTextField(1, this.fontRenderer, this.width / 2 - 100, i + 72 - 12, 200, 20);
        this.inputField.setText(this.mc.getSession().getUsername());
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, i + 72 + 12, 200, 20, "Login"));
    }

    public static void changeName(String name) {
        YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)((Object)service.createUserAuthentication(Agent.MINECRAFT));
        auth.logOut();
        Session session = new Session(name, name, "0", "legacy");
        try {
            Firework.setSession(session);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        Display.setTitle("Firework | " + Minecraft.getMinecraft().getSession().getUsername() + "");
        CustomDiscordRichPresence.rerun();
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            ChangeUser.changeName(this.inputField.getText());
        }
    }

    public void updateScreen() {
        this.inputField.updateCursorCounter();
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        switch (keyCode) {
            case 1: {
                this.mc.displayGuiScreen(new FireworkMenu());
                break;
            }
            default: {
                this.inputField.textboxKeyTyped(typedChar, keyCode);
            }
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.drawDefaultBackground();
        this.mc.renderEngine.bindTexture(texture);
        Gui.drawScaledCustomSizeModalRect((int)0, (int)0, (float)0.0f, (float)0.0f, (int)this.width, (int)this.height, (int)this.width, (int)this.height, (float)this.width, (float)this.height);
        for (int i = 0; i < this.buttonList.size(); ++i) {
            ((GuiButton)this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
        this.inputField.drawTextBox();
        this.mc.fontRenderer.drawStringWithShadow("Your Username: " + this.mc.getSession().getUsername(), this.width / 2 - 100, this.height / 4 + 48 + 110, Color.yellow.getRGB());
    }
}
