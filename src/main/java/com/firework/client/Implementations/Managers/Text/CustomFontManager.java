package com.firework.client.Implementations.Managers.Text;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.UnicodeFont;
import org.newdawn.slick.font.effects.ColorEffect;

public class CustomFontManager {
    private static final Pattern COLOR_CODE_PATTERN = Pattern.compile("\u00a7[0123456789abcdefklmnor]");
    public final int FONT_HEIGHT = 9;
    private final Map<String, Float> cachedStringWidth = new HashMap<String, Float>();
    private float antiAliasingFactor;
    private UnicodeFont unicodeFont;
    private int prevScaleFactor = new ScaledResolution(Minecraft.getMinecraft()).getScaleFactor();
    public static final char COLOR_CHAR = '\u00a7';
    private final String name;
    private final float size;

    public CustomFontManager(String fontName, float fontSize) {
        this.name = fontName;
        this.size = fontSize;
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        try {
            this.prevScaleFactor = resolution.getScaleFactor();
            this.unicodeFont = new UnicodeFont(this.getFontByName(fontName).deriveFont(fontSize * (float)this.prevScaleFactor / 2.0f));
            this.unicodeFont.addAsciiGlyphs();
            this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
            this.unicodeFont.loadGlyphs();
        }
        catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
        }
        this.antiAliasingFactor = resolution.getScaleFactor();
    }

    public CustomFontManager(Font font) {
        this(font.getFontName(), font.getSize());
    }

    public CustomFontManager(String fontName, int fontType, int size) {
        this(new Font(fontName, fontType, size));
    }

    private Font getFontByName(String name) throws IOException, FontFormatException {
        if (name.equalsIgnoreCase("roboto condensed")) {
            return this.getFontFromInput("/assets/minecraft/firework/fonts/RobotoCondensed-Regular.ttf");
        }
        if (name.equalsIgnoreCase("roboto")) {
            return this.getFontFromInput("/assets/minecraft/firework/fonts/Roboto-Regular.ttf");
        }
        if (name.equalsIgnoreCase("roboto medium")) {
            return this.getFontFromInput("/assets/minecraft/firework/fonts/Roboto-Medium.ttf");
        }
        if (name.equalsIgnoreCase("montserrat")) {
            return this.getFontFromInput("/assets/minecraft/firework/fonts/Montserrat-Regular.ttf");
        }
        if (name.equalsIgnoreCase("segoeui") || name.equalsIgnoreCase("segoeui light")) {
            return this.getFontFromInput("/assets/minecraft/firework/fonts/SegoeUI-Light.ttf");
        }
        if (name.equalsIgnoreCase("jellee bold")) {
            return this.getFontFromInput("/assets/minecraft/firework/fonts/Jellee-Bold.ttf");
        }
        if (name.equalsIgnoreCase("tcm")) {
            return this.getFontFromInput("/assets/minecraft/firework/fonts/Tcm.ttf");
        }
        return this.getFontFromInput("assets/fonts/Roboto-Regular.ttf");
    }

    private Font getFontFromInput(String path) throws IOException, FontFormatException {
        return Font.createFont(0, CustomFontManager.class.getResourceAsStream(path));
    }

    public int drawString(String text, float x, float y, int color) {
        if (text == null) {
            return 0;
        }
        ScaledResolution resolution = new ScaledResolution(Minecraft.getMinecraft());
        try {
            if (resolution.getScaleFactor() != this.prevScaleFactor) {
                this.prevScaleFactor = resolution.getScaleFactor();
                this.unicodeFont = new UnicodeFont(this.getFontByName(this.name).deriveFont(this.size * (float)this.prevScaleFactor / 2.0f));
                this.unicodeFont.addAsciiGlyphs();
                this.unicodeFont.getEffects().add(new ColorEffect(Color.WHITE));
                this.unicodeFont.loadGlyphs();
            }
        }
        catch (FontFormatException | IOException | SlickException e) {
            e.printStackTrace();
        }
        this.antiAliasingFactor = resolution.getScaleFactor();
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)(1.0f / this.antiAliasingFactor), (float)(1.0f / this.antiAliasingFactor), (float)(1.0f / this.antiAliasingFactor));
        y *= this.antiAliasingFactor;
        float originalX = x *= this.antiAliasingFactor;
        float red = (float)(color >> 16 & 0xFF) / 255.0f;
        float green = (float)(color >> 8 & 0xFF) / 255.0f;
        float blue = (float)(color & 0xFF) / 255.0f;
        float alpha = (float)(color >> 24 & 0xFF) / 255.0f;
        GlStateManager.color((float)red, (float)green, (float)blue, (float)alpha);
        char[] characters = text.toCharArray();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((int)770, (int)771, (int)1, (int)0);
        GlStateManager.blendFunc((int)770, (int)771);
        String[] parts = COLOR_CODE_PATTERN.split(text);
        int index = 0;
        for (String s : parts) {
            for (String s2 : s.split("\n")) {
                for (String s3 : s2.split("\r")) {
                    this.unicodeFont.drawString(x, y, s3, new org.newdawn.slick.Color(color));
                    x += (float)this.unicodeFont.getWidth(s3);
                    if ((index += s3.length()) >= characters.length || characters[index] != '\r') continue;
                    x = originalX;
                    ++index;
                }
                if (index >= characters.length || characters[index] != '\n') continue;
                x = originalX;
                y += this.getHeight(s2) * 2.0f;
                ++index;
            }
        }
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.popMatrix();
        return (int)x;
    }

    public int drawStringWithShadow(String text, float x, float y, int color) {
        this.drawString(StringUtils.stripControlCodes((String)text), x + 0.5f, y + 0.5f, 0);
        return this.drawString(text, x, y, color);
    }

    public void drawCenteredString(String text, float x, float y, int color) {
        this.drawString(text, x - (float)((int)this.getWidth(text) >> 1), y, color);
    }

    public static String stripColor(String input) {
        return input == null ? null : Pattern.compile("(?i)\u00a7[0-9A-FK-OR]").matcher(input).replaceAll("");
    }

    public float getWidth(String text) {
        if (this.cachedStringWidth.size() > 1000) {
            this.cachedStringWidth.clear();
        }
        return this.cachedStringWidth.computeIfAbsent(text, e -> Float.valueOf((float)this.unicodeFont.getWidth(CustomFontManager.stripColor(text)) / this.antiAliasingFactor)).floatValue();
    }

    public float getHeight(String s) {
        return (float)this.unicodeFont.getHeight(s) / 2.0f;
    }

    public UnicodeFont getFont() {
        return this.unicodeFont;
    }

    public List<String> splitString(String text, int wrapWidth) {
        ArrayList<String> lines = new ArrayList<String>();
        String[] splitText = text.split(" ");
        StringBuilder currentString = new StringBuilder();
        for (String word : splitText) {
            String potential = currentString + " " + word;
            if (this.getWidth(potential) >= (float)wrapWidth) {
                lines.add(currentString.toString());
                currentString = new StringBuilder();
            }
            currentString.append(word).append(" ");
        }
        lines.add(currentString.toString());
        return lines;
    }
}
