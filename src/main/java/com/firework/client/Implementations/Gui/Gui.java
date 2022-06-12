package com.firework.client.Implementations.Gui;

import com.firework.client.Features.Modules.Client.GuiGradient;
import com.firework.client.Features.Modules.Module;
import com.firework.client.Firework;
import com.firework.client.Implementations.Gui.Components.Advanced.EndBlock;
import com.firework.client.Implementations.Gui.Components.Advanced.Frame;
import com.firework.client.Implementations.Gui.Components.Advanced.ModuleButton;
import com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents.BoolButton;
import com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents.ColorButton;
import com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents.ColorRainbowButton;
import com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents.ColorSliderButton;
import com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents.KeyButton;
import com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents.ModeButton;
import com.firework.client.Implementations.Gui.Components.Advanced.SettingsComponents.SliderButton;
import com.firework.client.Implementations.Gui.Components.Advanced.StartBlock;
import com.firework.client.Implementations.Gui.Components.Advanced.SubModule;
import com.firework.client.Implementations.Gui.Components.Button;
import com.firework.client.Implementations.Gui.Components.Column;
import com.firework.client.Implementations.Gui.GuiInfo;
import com.firework.client.Implementations.Gui.GuiValueStorage;
import com.firework.client.Implementations.Gui.Offset;
import com.firework.client.Implementations.Gui.Particles.ParticleInfo;
import com.firework.client.Implementations.Gui.Particles.ParticleSystem;
import com.firework.client.Implementations.Settings.Setting;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import org.lwjgl.input.Mouse;

public class Gui
extends GuiScreen {
    public ParticleSystem particleSystem = new ParticleSystem();
    public static ArrayList<Button> initializedButtons;
    public static boolean isDragging;
    public static boolean keyIsDragging;
    public static String activeKeyModule;
    public int origYOffset = 20;
    public int buttonWidth = 70;

    public Gui() {
        GuiInfo.setupModulesColumns();
        for (Module m : Firework.moduleManager.modules) {
            GuiInfo.addModuleToColumn(m);
        }
        isDragging = false;
        keyIsDragging = false;
        initializedButtons = null;
        initializedButtons = new ArrayList();
        GuiInfo.icons();
        this.init();
    }

    public void init() {
        GuiInfo.index = 0;
        int newXOffset = 0;
        initializedButtons.clear();
        for (Column column : GuiInfo.columns) {
            int xOffset = this.buttonWidth / 7;
            int yOffset = this.origYOffset;
            StartBlock startBlock = new StartBlock(column.name, xOffset + newXOffset, yOffset, this.buttonWidth, 15);
            yOffset += startBlock.offset;
            int index = 0;
            for (Object obj : column.components) {
                if (obj instanceof Module) {
                    Module m = (Module)obj;
                    ModuleButton moduleButton = new ModuleButton(m, m.name, xOffset + newXOffset, yOffset, this.buttonWidth, 14);
                    initializedButtons.add(moduleButton);
                    yOffset += moduleButton.offset;
                    if (m.isOpened.getValue().booleanValue()) {
                        for (Setting setting : Firework.settingManager.modulesSettings(m)) {
                            Offset offsetObject = new Offset();
                            if (setting.hidden) continue;
                            if (setting.mode == Setting.Mode.BOOL) {
                                offsetObject.register(new BoolButton(setting, xOffset + newXOffset, yOffset, this.buttonWidth, 11));
                            }
                            if (setting.mode == Setting.Mode.MODE) {
                                offsetObject.register(new ModeButton(setting, xOffset + newXOffset, yOffset, this.buttonWidth, 11));
                            }
                            if (setting.mode == Setting.Mode.NUMBER) {
                                offsetObject.register(new SliderButton(setting, xOffset + newXOffset, yOffset, this.buttonWidth, 11));
                            }
                            if (setting.mode == Setting.Mode.KEY) {
                                offsetObject.register(new KeyButton(setting, xOffset + newXOffset, yOffset, this.buttonWidth, 11));
                            }
                            if (setting.mode == Setting.Mode.COLOR) {
                                offsetObject.register(new ColorButton(setting, xOffset + newXOffset, yOffset, this.buttonWidth, 11), new ColorSliderButton(setting, xOffset + newXOffset, yOffset + 71, this.buttonWidth, 12, ColorSliderButton.CSliderMode.HUE), new ColorSliderButton(setting, xOffset + newXOffset, yOffset + 84, this.buttonWidth, 12, ColorSliderButton.CSliderMode.SATURATION), new ColorSliderButton(setting, xOffset + newXOffset, yOffset + 97, this.buttonWidth, 12, ColorSliderButton.CSliderMode.LIGHT), new ColorRainbowButton(setting, xOffset + newXOffset, yOffset + 110, this.buttonWidth, 12));
                            }
                            yOffset += offsetObject.offset;
                        }
                    }
                } else if (obj instanceof SubModule) {
                    SubModule sb = (SubModule)obj;
                    Offset offsetObject = new Offset();
                    offsetObject.register(sb);
                    if (((Boolean)GuiValueStorage.values[sb.localIndex].get(0)).booleanValue()) {
                        for (Module module : sb.modules) {
                            column.components.add(index, module);
                        }
                    }
                    yOffset += offsetObject.offset;
                }
                ++index;
            }
            Frame frame = new Frame(xOffset + newXOffset, this.origYOffset + 15, this.buttonWidth, yOffset - this.origYOffset - 15);
            EndBlock endBlock = new EndBlock(xOffset + newXOffset - 1, yOffset + 1, this.buttonWidth + 2, 1);
            initializedButtons.add(frame);
            initializedButtons.add(endBlock);
            initializedButtons.add(startBlock);
            newXOffset += this.buttonWidth + this.buttonWidth / 7;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) throws ConcurrentModificationException {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (GuiGradient.enabled.getValue().booleanValue()) {
            this.drawGradientRect(0, 0, this.mc.displayWidth, this.mc.displayHeight, new Color(GuiGradient.Color1.getValue().toRGB().getRed(), GuiGradient.Color1.getValue().toRGB().getGreen(), GuiGradient.Color1.getValue().toRGB().getBlue(), 100).getRGB(), new Color(GuiGradient.Color2.getValue().toRGB().getRed(), GuiGradient.Color2.getValue().toRGB().getGreen(), GuiGradient.Color2.getValue().toRGB().getBlue(), 100).getRGB());
        }
        if (com.firework.client.Features.Modules.Client.Gui.background.getValue().booleanValue()) {
            this.drawDefaultBackground();
        }
        if (ParticleInfo.isEnabled) {
            this.particleSystem.updatePositions();
            this.particleSystem.drawLines();
            this.particleSystem.drawParticles();
        }
        for (Button button : initializedButtons) {
            button.draw();
        }
        GuiInfo.setupModulesColumns();
        for (Module m : Firework.moduleManager.modules) {
            GuiInfo.addModuleToColumn(m);
        }
        if (isDragging) {
            this.mouseClicked(mouseX, mouseY, 0);
        }
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        float scroll = Math.signum(Mouse.getDWheel());
        int speed = MathHelper.floor((double)com.firework.client.Features.Modules.Client.Gui.scrollSpeed.getValue());
        if (scroll == 1.0f) {
            this.origYOffset += speed;
        }
        if (scroll == -1.0f) {
            this.origYOffset -= speed;
        }
        if (scroll != 0.0f) {
            this.init();
        }
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        super.keyTyped(typedChar, keyCode);
        for (Button button : initializedButtons) {
            button.onKeyTyped(keyCode);
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int state) {
        for (Button button : initializedButtons) {
            if (!this.isHoveringOnTheButton(button, new Vec2f(mouseX, mouseY))) continue;
            if (button instanceof ModuleButton) {
                if (state == 0) {
                    button.initialize(mouseX, mouseY);
                } else {
                    ((ModuleButton)button).module.isOpened.setValue(((ModuleButton)button).module.isOpened.getValue() == false);
                    this.mc.player.playSound(SoundEvents.UI_BUTTON_CLICK, 1.0f, 1.0f);
                    this.init();
                }
            }
            if (button instanceof BoolButton && state == 0) {
                ((BoolButton)button).initialize(mouseX, mouseY);
            }
            if (button instanceof SliderButton && state == 0) {
                ((SliderButton)button).initialize(mouseX, mouseY);
                isDragging = true;
            }
            if (button instanceof KeyButton && state == 0) {
                ((KeyButton)button).initialize(mouseX, mouseY);
                activeKeyModule = ((KeyButton)button).setting.module.name;
                keyIsDragging = true;
            }
            if (button instanceof ModeButton && state == 0) {
                ((ModeButton)button).initialize(mouseX, mouseY);
            }
            if (button instanceof ColorButton) {
                ((ColorButton)button).initialize(mouseX, mouseY, state);
                this.init();
            }
            if (button instanceof ColorSliderButton) {
                ((ColorSliderButton)button).initialize(mouseX, mouseY, state);
            }
            if (button instanceof ColorRainbowButton) {
                ((ColorRainbowButton)button).initialize(mouseX, mouseY, state);
            }
            return;
        }
    }

    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        isDragging = false;
    }

    public void onGuiClosed() {
        super.onGuiClosed();
        initializedButtons.clear();
    }

    public boolean isHoveringOnTheButton(Button button, Vec2f mousePoint) {
        return mousePoint.x > (float)button.x && mousePoint.x < (float)(button.x + button.width) && mousePoint.y > (float)button.y && mousePoint.y < (float)(button.y + button.height);
    }

    static {
        isDragging = false;
        keyIsDragging = false;
        activeKeyModule = "";
    }
}
