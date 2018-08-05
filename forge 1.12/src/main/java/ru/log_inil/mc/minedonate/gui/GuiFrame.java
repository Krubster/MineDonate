package ru.log_inil.mc.minedonate.gui;

import ru.alastar.minedonate.gui.ShopGUI;

public class GuiFrame extends GuiEntry {

    public GuiFrame(String _name) {

        super(_name);

    }

    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt) {

        super.draw(g, page, mouseX, mouseY, partialTicks, dt);

        if (g.dbgFlag) {

            g.drawString(g.getFontRenderer(), name, 0, 0, 14737632);

        }

    }

    public void hideFrame(ShopGUI g) {

        g.showEntry(name, false);
        unShow(g);

        g.initGui();

    }

}
