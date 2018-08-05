package ru.alastar.minedonate.gui;

import ru.log_inil.mc.minedonate.gui.GuiTexturedButton;

public class GoButton extends GuiTexturedButton {

    int shopId = -1;

    public GoButton(int _shopId, int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
        this.shopId = _shopId;
    }

    public GoButton(int _shopId, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.shopId = _shopId;
    }

}
