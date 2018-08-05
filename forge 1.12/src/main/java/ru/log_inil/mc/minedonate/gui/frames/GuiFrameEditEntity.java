package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.manage.packets.EditMerchNumberPacket;
import ru.alastar.minedonate.network.manage.packets.EditMerchStringPacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFramEditObject;

public class GuiFrameEditEntity extends GuiFrameEditItem {

    public GuiFrameEditEntity(String _name, DataOfUIFramEditObject _douifcs) {

        super(_name, _douifcs);

    }

    @Override
    public boolean actionPerformed(ShopGUI g, GuiButton b) {

        if (b.id == saveChangesButton.id) {

            g.setLoading(true);

            if (MineDonate.getAccount().canUnlimitedEntities()) {

                try {

                    Integer n = Integer.parseInt(limitField.getText());

                    if (limit != n) {

                        hideFrame(g);

                        ModNetworkRegistry.sendToServerEditMerchNumberPacket(shopId, catId, merch_id, EditMerchNumberPacket.Type.LIMIT, (int) n);

                        limit = n;

                    }

                } catch (Exception ex) {

                }

            }

            try {

                Integer n = Integer.parseInt(costField.getText());

                if (cost != n) {

                    hideFrame(g);

                    ModNetworkRegistry.sendToServerEditMerchNumberPacket(shopId, catId, merch_id, EditMerchNumberPacket.Type.COST, (int) n);

                    cost = n;

                }

            } catch (Exception ex) {

            }

            if (!nameField.getText().trim().equals(fieldText)) {

                hideFrame(g);

                ModNetworkRegistry.sendToServerEditMerchStringPacket(shopId, catId, merch_id, EditMerchStringPacket.Type.NAME, (fieldText = nameField.getText()));
                System.err.println(fieldText);
            }

        }

        if (b.id == cancelChangesButton.id) {

            this.hideFrame(g);

        }

        return false;

    }

}
