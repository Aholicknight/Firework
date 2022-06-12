package com.firework.client.Features.CustomMainMenu;

import com.firework.client.Features.CustomMainMenu.ChangeUser;
import java.io.IOException;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class FireworkMenu
extends GuiScreen {
    private static final ResourceLocation texture = new ResourceLocation("firework/textures/background.jpg");

    public void initGui() {
        int i = this.height / 4 + 48;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 + 150, i + 50 + 12, 50, 20, I18n.format((String)"menu.options", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 202, i + 50 + 12, 48, 20, I18n.format((String)"menu.quit", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 150, i + 50 - 12, 100, 20, "Change User"));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 150, i + 50 - 34, 100, 20, I18n.format((String)"menu.multiplayer", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 + 150, i + 50 - 58, 100, 20, I18n.format((String)"menu.singleplayer", (Object[])new Object[0])));
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 1) {
            this.mc.shutdown();
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new ChangeUser());
        }
        if (button.id == 4) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 5) {
            this.mc.displayGuiScreen(new GuiWorldSelection(this));
        }
        super.actionPerformed(button);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        this.drawDefaultBackground();
        this.mc.renderEngine.bindTexture(texture);
        Gui.drawScaledCustomSizeModalRect((int)0, (int)0, (float)0.0f, (float)0.0f, (int)this.width, (int)this.height, (int)this.width, (int)this.height, (float)this.width, (float)this.height);
        for (GuiButton guiButton : this.buttonList) {
            guiButton.drawButton(this.mc, mouseX, mouseY, partialTicks);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
