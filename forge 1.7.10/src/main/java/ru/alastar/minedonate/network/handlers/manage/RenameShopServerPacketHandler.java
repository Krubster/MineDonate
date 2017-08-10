package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import ru.alastar.minedonate.network.packets.manage.RenameShopPacket;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket;

public class RenameShopServerPacketHandler implements IMessageHandler < RenameShopPacket, IMessage > {
	
    public RenameShopServerPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( RenameShopPacket message, MessageContext ctx ) {
    	
        return new ManageResponsePacket ( ManageResponsePacket.ResponseType.SHOP, ManageResponsePacket.ResponseCode.RENAME, ManageResponsePacket.ResponseStatus.OK ) ;
        
    }
    
}