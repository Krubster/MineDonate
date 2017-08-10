package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.network.packets.manage.ItemMergedPacket;

public class ItemMergedClientPacketHandler implements IMessageHandler < ItemMergedPacket, IMessage > {
	
    public ItemMergedClientPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( ItemMergedPacket message, MessageContext ctx ) {
    	
    	MineDonate . acc . ms . currentItemStack = message . is ;
    	
        return null ;
        
    }
    
}