package ru.log_inil.mc.minedonate.gui.context;

import net.minecraft.client.gui.Gui;
import ru.alastar.minedonate.Utils;
import ru.log_inil.mc.minedonate.gui.MCGuiAccessible;

import java.awt.*;
import java.util.List;

public class ContextMenu {

    static int lineHeight = 10;
    List<ContextElement> l;
    int activateCoordX;
    int activateCoordY;
    int activateCoordXEnd;
    int activateCoordYEnd;
    int drawPosX;
    int drawPosY;
    int interactWidth;
    int interactHeight;
    int activateCoordXEndInteract;
    int activateCoordYEndInteract;
    int i = 0;
    ContextDrawType tmpDT;
    int tmpHeight;
    int maxWidth;
    int maxHeight;
    int dbgColorInteract = Utils.rgbaToInt(new Color(150, 1, 1, 255));
    int dbgColor = Utils.rgbaToInt(new Color(1, 150, 1, 255));
    int dbgColorElementInteractable = Utils.rgbaToInt(new Color(100, 1, 1, 255));
    int backgroundColor = Utils.rgbaToInt(new Color(0, 0, 0, 170));
    int tmp;

    public ContextMenu(int w, int h, List<ContextElement> _l) {

        interactWidth = w;
        interactHeight = h;
        l = _l;

    }

    public void draw(MCGuiAccessible g, int mouseX, int mouseY) {

        Gui.drawRect(drawPosX, drawPosY - 2, drawPosX + maxWidth + 9, drawPosY + tmpHeight + 2, backgroundColor);

        tmpHeight = 0;

        for (ContextElement cme : l) {

            tmpDT = ContextDrawType.NORMAL;

            if (drawPosX < mouseX && mouseX < drawPosX + cme.getLineWidth(g) && mouseY > drawPosY + tmpHeight && mouseY < drawPosY + tmpHeight + cme.lineHeight) {

                tmpDT = ContextDrawType.HOVERED;

            }

            maxWidth = Math.max(maxWidth, cme.getLineWidth(g));

            cme.draw(g, i++, l.size(), drawPosX, drawPosY, tmpDT);

            tmpHeight += cme.lineHeight;

        }

        i = 0;

    }

    public ContextElement getLine(int x, int y) {

        // check out of bounds all menu
        if (x < drawPosX || drawPosX + maxWidth < x) {
            return null;
        }
        if (y < drawPosY || drawPosY + maxHeight < y) {
            return null;
        }

        tmp = (drawPosY - y) / (lineHeight);

        if (tmp < 0) {
            tmp = Math.abs(tmp);
        }

        return l.size() > tmp && tmp >= 0 ? l.get(tmp) : null;

    }

    public void calcMaxs(MCGuiAccessible g) {

        maxHeight = 0;

        for (ContextElement cme : l) {

            maxWidth = Math.max(maxWidth, cme.getLineWidth(g));
            maxHeight += cme.lineHeight;

        }

    }

    public void updateInteractArea(MCGuiAccessible g, int x, int y) {

        activateCoordX = x;
        activateCoordY = y;

        activateCoordXEnd = x + interactWidth;
        activateCoordYEnd = y + interactHeight;

        calcMaxs(g);

        activateCoordXEndInteract = drawPosX + maxWidth + 6;
        activateCoordYEndInteract = drawPosY + maxHeight + 2;

    }

    public void updateInteractAreaSizes(MCGuiAccessible g, int w, int h) {

        interactWidth = w;
        interactHeight = h;

        updateInteractArea(g, activateCoordX, activateCoordY);

    }

    // check interach triger object area
    public boolean coordContains(int x, int y) {

        return (activateCoordX <= x && x <= activateCoordXEnd) && (activateCoordY - 2 <= y && activateCoordYEnd >= y);

    }

    // check interact menu area
    public boolean coordContainsInteract(int x, int y) {

        return (activateCoordX < x && x < activateCoordXEndInteract) && (activateCoordY - 2 < y && activateCoordYEndInteract > y);

    }

    public void drawDebugInteractable(MCGuiAccessible g, int mouseX, int mouseY) {

        for (int i = 0; i < l.size(); i++) {

            g.drawRect(drawPosX, drawPosY - 2 + (lineHeight * (i + 1)), activateCoordXEndInteract, drawPosY - 1 + (lineHeight * (i + 1)), dbgColorElementInteractable);

        }

        g.drawRect(drawPosX, drawPosY - 2, activateCoordXEndInteract, drawPosY - 1, dbgColorInteract);

        g.drawRect(drawPosX, drawPosY - 1, drawPosX + 1, activateCoordYEndInteract, dbgColorInteract);

        g.drawRect(activateCoordXEndInteract, drawPosY - 2, activateCoordXEndInteract + 1, activateCoordYEndInteract, dbgColorInteract);

        g.drawRect(drawPosX, activateCoordYEndInteract, activateCoordXEndInteract + 1, activateCoordYEndInteract + 1, dbgColorInteract);

    }

    public void drawDebug(MCGuiAccessible g, int mouseX, int mouseY) {

        g.drawRect(activateCoordX, activateCoordY, activateCoordXEnd, activateCoordY + 1, dbgColor);

        g.drawRect(activateCoordX, activateCoordY + 1, activateCoordX + 1, activateCoordYEnd, dbgColor);

        g.drawRect(activateCoordXEnd, activateCoordY, activateCoordXEnd + 1, activateCoordYEnd, dbgColor);

        g.drawRect(activateCoordX, activateCoordYEnd, activateCoordXEnd + 1, activateCoordYEnd + 1, dbgColor);

    }

}