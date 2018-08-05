package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.log_inil.mc.minedonate.gui.DrawType;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameAddItem;

public class GuiFrameAddEntity extends GuiFrameAddItem {

    public GuiFrameAddEntity(String _name, DataOfUIFrameAddItem _douifcs) {

        super(_name, _douifcs);

        drawItemStack = false;

    }

    public void draw(ShopGUI g, int page, int mouseX, int mouseY, float partialTicks, DrawType dt) {

        g.drawRect(posX, posY, posX + width, posY + height, backgroundColor);

        g.drawString(g.getFontRenderer(), douifcs.title, posX + 5, posY + 3, titleColor);

        nameField.drawTextBox();
        costField.drawTextBox();
        limitField.drawTextBox();

    }

    @Override
    public boolean actionPerformed(ShopGUI g, GuiButton b) {

        if (b.id == saveChangesButton.id) {

            int cost = 1, limit = 1;
            if (costField.getText().trim().isEmpty()) {

                costField.fieldBorderColor = fieldBorderRedColor;

                return false;

            } else {

                try {

                    cost = Integer.parseInt(costField.getText());

                    if (cost < 1) {

                        costField.fieldBorderColor = fieldBorderRedColor;

                        return false;

                    }

                } catch (Exception ex) {

                    ex.printStackTrace();

                    costField.fieldBorderColor = fieldBorderRedColor;

                    return false;

                }

            }

            if (MineDonate.getAccount().canUnlimitedEntities() && this.limitField != null) {

                try {

                    limit = Integer.parseInt(this.limitField.getText());

                } catch (Exception ex) {

                    limit = 1;

                }

            } else {

                limit = -1;

            }

            g.setLoading(true);

            ModNetworkRegistry.sendToServerAddNewEntryPacket(shopId, catId, limit, cost, this.nameField.getText());

            this.hideFrame(g);

        }

        if (b.id == cancelChangesButton.id) {

            this.hideFrame(g);

        }

        return false;

    }

}
