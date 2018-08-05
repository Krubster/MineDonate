package ru.alastar.minedonate.gui;

import ru.log_inil.mc.minedonate.gui.GuiGradientButton;

/**
 * Created by Alastar on 20.07.2017.
 */
public class CategoryButton extends GuiGradientButton {
    private int category;

    public CategoryButton(int category, int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText, true);
        this.category = category;
    }

    public CategoryButton(int category, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText, true);
        this.category = category;
    }

    public int getCategory() {
        return category;
    }
}
