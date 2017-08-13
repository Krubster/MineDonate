package ru.alastar.minedonate.network.manage.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class UnfreezeShopPacket implements IMessage {

	public int shopId ;
	
    public UnfreezeShopPacket ( ) { }
    public UnfreezeShopPacket ( int _shopId ) {
    	
    	shopId = _shopId ;
    	
    }

    @Override 
    public void toBytes ( ByteBuf buf ) {
    	
    	buf . writeInt ( shopId ) ;
    	
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

	   shopId = buf . readInt ( ) ;

    }
    
 }