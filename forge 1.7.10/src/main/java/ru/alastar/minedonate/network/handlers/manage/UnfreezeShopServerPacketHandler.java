package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket;
import ru.alastar.minedonate.network.packets.manage.UnfreezeShopPacket;

public class UnfreezeShopServerPacketHandler implements IMessageHandler < UnfreezeShopPacket, IMessage > {
	
    public UnfreezeShopServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( UnfreezeShopPacket message, MessageContext ctx ) {
    	
        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.UNFREEZ, ManageResponsePacket.ResponseStatus.OK ) ;
        
    }
    
}