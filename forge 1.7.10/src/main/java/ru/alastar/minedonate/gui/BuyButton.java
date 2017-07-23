package ru.alastar.minedonate.gui;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.MineDonateNetwork;
import ru.alastar.minedonate.network.packets.BuyPacket;

/**
 * Created by Alastar on 19.07.2017.
 */
public class BuyButton extends GuiButton {
    int merch_id = -1;

    public BuyButton( int merch_id, int buttonId, int x, int y, String buttonText) {
        super(buttonId, x, y, buttonText);
        this.merch_id = merch_id;
    }

    public BuyButton(int merch_id, int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        super(buttonId, x, y, widthIn, heightIn, buttonText);
        this.merch_id = merch_id;
    }

    public void buy(int category) {
        if (merch_id != -1) {
            BuyPacket packet = new BuyPacket(merch_id, category, MineDonate.m_Categories[category].getMerch(merch_id).getAmountToBuy());
            MineDonateNetwork.INSTANCE.sendToServer(packet);
        }
    }


}
