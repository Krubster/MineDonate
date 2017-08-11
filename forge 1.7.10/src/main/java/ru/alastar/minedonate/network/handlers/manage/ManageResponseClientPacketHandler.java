package ru.alastar.minedonate.network.handlers.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket.ResponseCode;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket.ResponseStatus;
import ru.alastar.minedonate.network.packets.manage.ManageResponsePacket.ResponseType;

public class ManageResponseClientPacketHandler implements IMessageHandler < ManageResponsePacket, IMessage > {
	
    public ManageResponseClientPacketHandler ( ) {

    }

    @Override
    public IMessage onMessage ( ManageResponsePacket message, MessageContext ctx ) {
    	
    	ShopGUI . instance . setLoading ( false ) ;
    	ShopGUI . instance . initGui ( ) ;
    	
    	System.err.println(ResponseType.values()[message.type] + "> " + ResponseCode.values()[message.code] + "> " + ResponseStatus.values()[message.status] );
        return null;
        
    }
    
}