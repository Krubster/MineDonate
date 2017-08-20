package ru.alastar.minedonate.network.manage.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.rtnl.Utils;

public class FreezeObjectPacket implements IMessage {

	public int shopId ;
	public String reason ;
	public String accountName ;
	public boolean bool ;
	public Type type ;
	
    public FreezeObjectPacket ( ) { }
    public FreezeObjectPacket ( int _shopId, String _reason, boolean _bool ) {
    	
    	shopId = _shopId ;
    	reason = _reason ;
    	bool = _bool ;
    	
    	type = Type . SHOP ;
    	
    }
 
    public FreezeObjectPacket ( String _accountName, String _reason, boolean _bool ) {
    	
    	accountName = _accountName ;
    	reason = _reason ;
    	bool = _bool ;
    	
    	type = Type . ACCOUNT ;

    }

    @Override 
    public void toBytes ( ByteBuf buf ) {
    	
    	buf . writeInt ( type . ordinal ( ) ) ;
    	
    	try {
    	
    		if ( type == Type . SHOP ) {
    			
    			buf . writeInt ( shopId ) ;
    			
    		} else {
    			
    			Utils . netWriteString ( buf, accountName ) ;

    		}
    		
    		buf . writeBoolean ( reason != null ) ;
    		
    		if ( reason != null ) {
    			
    			Utils . netWriteString ( buf, reason ) ;
    		
    		}

    		buf . writeBoolean ( bool ) ;
    		    		
    	} catch ( Exception ex ) {
    		
    		ex . printStackTrace ( ) ;
    		
    	}
    	
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

	   	try {
	    	
	   		type = Type . values ( ) [ buf . readInt ( ) ] ;
	   		
	   		if ( type == Type . SHOP ) {

	   			shopId = buf . readInt ( ) ;

	   		} else if ( type == Type . ACCOUNT ) {
	   			
	   			accountName = Utils . netReadString ( buf ) ;
	   		 
	   		}
	   		
	   		if ( buf . readBoolean ( ) ) {
	   			
	   			reason = Utils . netReadString ( buf ) ;
	   			
	   		}
	   		
	   		bool = buf . readBoolean ( ) ;
	   					
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
   	
    }
	
	public enum Type {
	
		ACCOUNT, SHOP
		
	}
	
}
