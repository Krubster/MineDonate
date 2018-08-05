package ru.alastar.minedonate.network.manage.handlers;

import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.events.MineDonateGUIHandler;
import ru.alastar.minedonate.network.manage.packets.ItemMergedPacket;

public class ItemMergedClientPacketHandler implements IMessageHandler<ItemMergedPacket, IMessage> {

    public ItemMergedClientPacketHandler() {

    }

    @Override
    public IMessage onMessage(ItemMergedPacket message, MessageContext ctx) {

        MineDonate.acc.ms.currentItemStack = message.is;

        if (message.is != null && MineDonateGUIHandler.lastBacked != null) {

            MineDonate.proxy.clientOpenGui(MineDonateGUIHandler.SHOP_ID);

        }

        return null;

    }

}