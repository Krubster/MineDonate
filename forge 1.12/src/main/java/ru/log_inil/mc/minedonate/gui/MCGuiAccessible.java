package ru.log_inil.mc.minedonate.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;

public abstract class MCGuiAccessible extends GuiScreen {

    GuiScreen gs;
    FontRenderer fr;

    public GuiScreen getParent() {

        return gs;

    }

    public void setParent(GuiScreen _gs) {

        gs = _gs;

    }

    public FontRenderer getFontRenderer() {

        return fr;

    }

    public void setFontRenderer(FontRenderer _fr) {

        fr = _fr;

    }

}
