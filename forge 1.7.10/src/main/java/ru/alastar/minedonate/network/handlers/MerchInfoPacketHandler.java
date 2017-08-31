package ru.alastar.minedonate.network.handlers;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.packets.MerchInfoPacket;

/**
 * Created by Alastar on 23.07.2017.
 */
public class MerchInfoPacketHandler implements IMessageHandler<MerchInfoPacket, IMessage> {
	
    public MerchInfoPacketHandler(){

    }
    
    @Override
    public IMessage onMessage(MerchInfoPacket message, MessageContext ctx) {
        
    	MineDonate.modify(message.shopId, message.m_category, message.info.getId(), message.info);
        
        if(ShopGUI.instance != null){
        	
            ShopGUI.instance.updateButtons(true);
            ShopGUI.instance.initGui();

        }
        
        return null;
        
    }
}