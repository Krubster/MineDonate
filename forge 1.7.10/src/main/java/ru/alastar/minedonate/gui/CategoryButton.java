package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.GuiButton;

/**
 * Created by Alastar on 20.07.2017.
 */
public class CategoryButton extends GuiButton {
    private int category;
    public CategoryButton(int category, int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
        this.category = category;
    }

    public CategoryButton(int category, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.category = category;
    }

    public int getCategory(){
        return category;
    }
}
