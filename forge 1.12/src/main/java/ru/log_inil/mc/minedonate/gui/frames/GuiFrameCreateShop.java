package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameField;

public class GuiFrameCreateShop extends GuiFrameTextField {

    public GuiFrameCreateShop(String _name, DataOfUIFrameField _douifcs) {

        super(_name, _douifcs);

    }

    @Override
    public boolean actionPerformed(ShopGUI g, GuiButton b) {

        if (b.id == saveChangesButton.id) {

            if (nameField.getText().trim().isEmpty()) {

                nameField.fieldBorderColor = fieldBorderRedColor;

                return false;

            }

            g.setLoading(true);

            ModNetworkRegistry.sendToServerCreateNewShopPacket(this.nameField.getText());

            hideFrame(g);

        }

        if (b.id == cancelChangesButton.id) {

            hideFrame(g);

        }

        return false;

    }

}
