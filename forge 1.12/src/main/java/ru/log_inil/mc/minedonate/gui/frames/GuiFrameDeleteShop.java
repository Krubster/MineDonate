package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameConfirm;

public class GuiFrameDeleteShop extends GuiFrameConfirm {

    public GuiFrameDeleteShop(String _name, DataOfUIFrameConfirm _douifdi) {

        super(_name, _douifdi);

    }

    @Override
    public boolean actionPerformed(ShopGUI g, GuiButton b) {

        if (b.id == deleteButton.id) {

            if (!ShopGUI.instance.confirmFlag && (codeField.getText().trim().isEmpty() || !code.equals(codeField.getText()))) {

                codeField.fieldBorderColor = fieldBorderRedColor;

                return false;

            }

            g.setLoading(true);

            ModNetworkRegistry.sendToServerDeleteShopPacket(shopId);

            hideFrame(g);

        }

        if (b.id == cancelChangesButton.id) {

            hideFrame(g);

        }

        return false;

    }

    public void setShopId(int _shopId) {

        shopId = _shopId;

    }

}
