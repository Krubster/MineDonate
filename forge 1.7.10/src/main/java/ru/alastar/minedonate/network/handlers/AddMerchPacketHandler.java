package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.packets.AddMerchPacket;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AddMerchPacketHandler implements IMessageHandler<AddMerchPacket, IMessage> {
    public AddMerchPacketHandler(){

    }
    @Override public IMessage onMessage(AddMerchPacket message, MessageContext ctx) {
        MineDonate.AddMerch(message.m_category, message.info);
        if(ShopGUI.instance != null){
            ShopGUI.instance.updateBtns();
        }
        return null;
    }
}