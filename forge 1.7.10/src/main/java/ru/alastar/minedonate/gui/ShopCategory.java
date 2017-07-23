package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

/**
 * Created by Alastar on 19.07.2017.
 */
public interface ShopCategory {

    boolean getEnabled();

    int getSourceCount();

    String getName();

    void draw(ShopGUI relative, int page, int mouseX, int mouseY, float partialTicks);

    void updateButtons(ShopGUI relative, int page);

    int elements_per_page();

    void actionPerformed(GuiButton button);
}
