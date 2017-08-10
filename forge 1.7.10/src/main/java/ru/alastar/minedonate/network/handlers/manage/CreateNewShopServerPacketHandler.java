package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.network.packets.manage.CreateNewShopPacket;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket;

public class CreateNewShopServerPacketHandler implements IMessageHandler < CreateNewShopPacket, IMessage > {
	
    public CreateNewShopServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( CreateNewShopPacket message, MessageContext ctx ) {
    	
        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.CREATE, ManageResponsePacket.ResponseStatus.OK ) ;
        
    }
    
}