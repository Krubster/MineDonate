package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class InventoryShopPacket implements IMessage {

	public Type type ;
	
    public InventoryShopPacket ( ) { }
    
    public InventoryShopPacket ( Type _type ) {

    	type = _type ;
    	
    }


    @Override
    public void toBytes ( ByteBuf buf ) {

    	buf . writeInt ( type . ordinal ( ) ) ;
    	
    }

    @Override
    public void fromBytes ( ByteBuf buf ) {

    	type = Type . values ( ) [ buf . readInt ( ) ] ;
    			
    }
    
    public enum Type {
    	
    	OPEN_INV, CLOSE_WITH_MERGE, CLOSE_NO_MERGE
    	
    }
    
}