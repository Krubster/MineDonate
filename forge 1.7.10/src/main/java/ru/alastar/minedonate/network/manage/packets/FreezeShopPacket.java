package ru.alastar.minedonate.network.manage.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.rtnl.Utils;

public class FreezeShopPacket implements IMessage {

	public int shopId ;
	public String reason ;
	
    public FreezeShopPacket ( ) { }
    public FreezeShopPacket ( int _shopId, String _reason ) {
    	
    	shopId = _shopId ;
    	reason = _reason ;
    	
    }

    @Override 
    public void toBytes ( ByteBuf buf ) {
    	
    	buf . writeInt ( shopId ) ;
    	
    	try {
    	
    		Utils . netWriteString ( buf, reason ) ;
    		
    	} catch ( Exception ex ) {
    		
    		ex . printStackTrace ( ) ;
    		
    	}
    	
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

	   shopId = buf . readInt ( ) ;

	   	try {
	    	
	   		reason = Utils . netReadString ( buf ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
   	
    }
    
 }