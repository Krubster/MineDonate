package ru.log_inil.mc.minedonate.gui.context;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.log_inil.mc.minedonate.gui.MCGuiAccessible;

import java.util.ArrayList;
import java.util.List;

public class ContextMenuManager {

    static List<ContextMenu> menus = new ArrayList<>();
    static boolean currentDrawMenu = false;
    static ContextMenu currentMenu;

    static MCGuiAccessible gs;

    public static void draw(MCGuiAccessible _gs, int mouseX, int mouseY) {

        gs = _gs;

        if (currentDrawMenu && currentMenu != null) {

            currentMenu.draw(_gs, mouseX, mouseY);

        }

    }

    public static void drawDebug(MCGuiAccessible _gs, int mouseX, int mouseY) {

        gs = _gs;

        for (ContextMenu cmm : menus) {

            cmm.drawDebug(_gs, mouseX, mouseY);

        }

        if (currentDrawMenu && currentMenu != null) {

            currentMenu.drawDebugInteractable(_gs, mouseX, mouseY);

        }

    }

    public static boolean click(ShopGUI gs, int x, int y, int t) {

        if (t == 1) {

            // RMB
            currentDrawMenu = false;
            currentMenu = null;

            for (ContextMenu cmm : menus) {

                if (cmm.coordContains(x, y)) {

                    currentDrawMenu = true;

                    cmm.drawPosX = x;
                    cmm.drawPosY = y;

                    cmm.updateInteractArea(gs, cmm.activateCoordX, cmm.activateCoordY);

                    currentMenu = cmm;
                    return true;

                }

            }

        } else if (t == 0) {

            // LMB

            if (currentDrawMenu) {

                if (currentMenu.coordContainsInteract(x, y)) {

                    ContextElement cme = currentMenu.getLine(x, y);

                    if (cme != null) {

                        cme.onClick(gs, currentMenu);

                    }

                    currentDrawMenu = false;
                    currentMenu = null;

                    return true;

                } else {

                    currentDrawMenu = false;
                    currentMenu = null;

                }

            }

        } else {

            // AMB
            currentDrawMenu = false;
            currentMenu = null;

        }

        return false;

    }

    public static void addNewMenu(ContextMenu cmm) {

        menus.add(cmm);

    }

    public static void removeMenu(ContextMenu cmm) {

        menus.remove(cmm);

    }

    public static void clean() {

        menus.clear();

    }

}
