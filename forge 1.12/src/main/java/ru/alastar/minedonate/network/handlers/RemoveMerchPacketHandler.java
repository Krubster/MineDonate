package ru.alastar.minedonate.network.handlers;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.packets.RemoveMerchPacket;

/**
 * Created by Alastar on 21.07.2017.
 */
public class RemoveMerchPacketHandler implements IMessageHandler<RemoveMerchPacket, IMessage> {
    public RemoveMerchPacketHandler() {

    }

    @Override
    public IMessage onMessage(RemoveMerchPacket message, MessageContext ctx) {

        MineDonate.removeMerch(message.shopId, message.category_id, message.merch_id);

        if (ShopGUI.instance != null) {

            ShopGUI.instance.updateButtons(true);
            ShopGUI.instance.refresh();

        }

        return null;

    }
}