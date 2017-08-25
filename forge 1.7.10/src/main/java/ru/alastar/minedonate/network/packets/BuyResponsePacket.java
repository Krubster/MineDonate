package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Alastar on 18.07.2017.
 */
public class BuyResponsePacket implements IMessage {

    public BuyResponsePacket ( ) { }

    public Status status ;

    public BuyResponsePacket ( Status _status ) {

    	status = _status ;
    	
    }

    @Override 
    public void toBytes ( ByteBuf buf ) {

		buf . writeByte ( status . ordinal ( ) ) ;

    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

    	status = Status . values ( ) [ buf . readByte ( ) ] ;

    }
    
    public enum Status {
    	
    	SUCCESSFUL, ERROR_UNKNOWN, ERROR_SHOP_FREEZED, ERROR_NOT_ENOUGH_MONEY, ERROR_CANT_BUY
    	
    }
    
}