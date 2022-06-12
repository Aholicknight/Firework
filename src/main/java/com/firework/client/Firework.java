package com.firework.client;

import com.firework.client.Features.CommandsSystem.CommandManager;
import com.firework.client.Features.CustomMainMenu.OnGuiOpenEvent;
import com.firework.client.Features.Modules.Module;
import com.firework.client.Features.Modules.ModuleManager;
import com.firework.client.Implementations.Managers.Parser.JsonParser;
import com.firework.client.Implementations.Managers.Parser.JsonPrefixPraser;
import com.firework.client.Implementations.Managers.Parser.JsonReader;
import com.firework.client.Implementations.Managers.PositionManager;
import com.firework.client.Implementations.Managers.Settings.SettingManager;
import com.firework.client.Implementations.Managers.Text.CustomFontManager;
import com.firework.client.Implementations.Managers.Text.TextManager;
import com.firework.client.Implementations.Managers.Updater.UpdaterManager;
import com.firework.client.Implementations.Utill.Client.IconUtil;
import com.firework.client.Implementations.Utill.Client.SoundUtill;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.util.Util;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import xyz.firework.autentification.HwidCheck.HwidManager;

@Mod(modid="firework", name="FireWork Client", version="0.1")
public class Firework {
    public static final String MODID = "firework";
    public static final String NAME = "FireWork Client";
    public static final String VERSION = "0.1";
    public static Minecraft minecraft;
    public static String FIREWORK_DIRECTORY;
    private static Logger logger;
    public static SettingManager settingManager;
    public static ModuleManager moduleManager;
    public static CommandManager commandManager;
    public static CustomFontManager customFontManager;
    public static TextManager textManager;
    public static PositionManager positionManager;
    public static UpdaterManager updaterManager;

    public void loadManagers() {
        settingManager = new SettingManager();
        moduleManager = new ModuleManager();
        commandManager = new CommandManager();
        customFontManager = new CustomFontManager("tcm", 16.0f);
        textManager = new TextManager();
        positionManager = new PositionManager();
        updaterManager = new UpdaterManager();
        MinecraftForge.EVENT_BUS.register(updaterManager);
    }

    public static void unloadManagers() {
        settingManager = null;
        moduleManager = null;
        commandManager = null;
        textManager = null;
        customFontManager = null;
        positionManager = null;
        MinecraftForge.EVENT_BUS.unregister(updaterManager);
        updaterManager = null;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FIREWORK_DIRECTORY = Minecraft.getMinecraft().gameDir + "\\Firework\\";
        HwidManager.hwidCheck();
        SystemTray.sysTray();
        JsonParser.parse();
        JsonPrefixPraser.parse();
        JsonReader.getPrefix();
        JsonReader.getWebhook();
        JsonReader.getSpamText();
        Firework.setWindowIcon();
        MinecraftForge.EVENT_BUS.register(this);
        Display.setTitle("Loading Firework (FMLPreInitializationEvent)");
        this.loadManagers();
        logger = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        minecraft = Minecraft.getMinecraft();
        Display.setTitle("Loading Firework (FMLInitializationEvent)");
        SoundUtill.playSound(new ResourceLocation("firework/audio/loaded.wav"));
        Display.setTitle("Firework | " + Minecraft.getMinecraft().getSession().getUsername() + "");
        MinecraftForge.EVENT_BUS.register(new OnGuiOpenEvent());
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        for (Module m : Firework.moduleManager.modules) {
            if (!m.isEnabled.getValue().booleanValue()) continue;
            try {
                m.onTick();
            }
            catch (NullPointerException nullPointerException) {}
        }
    }

    @SubscribeEvent
    public void onPressedKey(InputEvent.KeyInputEvent event) {
        if (Keyboard.isKeyDown(13)) {
            minecraft.displayGuiScreen(new GuiChat(CommandManager.prefix));
        }
        for (Module module : Firework.moduleManager.modules) {
            if (!Keyboard.isKeyDown(module.key.getValue())) continue;
            module.onToggle();
        }
    }

    public static void setWindowIcon() {
        if (Util.getOSType() != Util.EnumOS.OSX) {
            try (InputStream inputStream16x = Firework.class.getClassLoader().getResourceAsStream("assets/minecraft/firework/textures/icon16.png");
                 InputStream inputStream32x = Firework.class.getClassLoader().getResourceAsStream("assets/minecraft/firework/textures/icon32.png");){
                ByteBuffer[] icons = new ByteBuffer[]{IconUtil.INSTANCE.readImageToBuffer(inputStream16x), IconUtil.INSTANCE.readImageToBuffer(inputStream32x)};
                Display.setIcon(icons);
            }
            catch (Exception e) {
                System.out.println("Icon Exception");
            }
        }
    }

    public static ResourceLocation resourceLocation(String path) {
        try {
            return Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(path)).getResourceLocation();
        }
        catch (IOException iOException) {
            return null;
        }
    }

    public static void setSession(Session s) {
        Class<?> mc = Minecraft.getMinecraft().getClass();
        try {
            Field session = null;
            for (Field f : mc.getDeclaredFields()) {
                if (!f.getType().isInstance(s)) continue;
                session = f;
            }
            if (session == null) {
                throw new IllegalStateException("Session Null");
            }
            session.setAccessible(true);
            session.set(Minecraft.getMinecraft(), s);
            session.setAccessible(false);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
