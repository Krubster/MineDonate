package ru.alastar.minedonate.network.packets.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.rtnl.Utils;

public class EditMerchStringPacket implements IMessage {

	public int shopId, catId, merchId ;
	public Type type ;
	public String s ;
	
    public EditMerchStringPacket ( ) { }
    
    public EditMerchStringPacket ( int _shopId, int _catId, int _merchId, Type _type, String _s ) {
    	
    	shopId = _shopId ;
    	catId = _catId ;
    	merchId = _merchId ;
    	type = _type ;
    	s = _s ;
    	
    }
    
    @Override 
    public void toBytes ( ByteBuf buf ) {
		
		buf . writeInt ( shopId ) ;
		buf . writeInt ( catId ) ;
		buf . writeInt ( merchId ) ;
		buf . writeInt ( type . ordinal ( ) ) ;

		boolean b = s == null || s . trim ( ) . isEmpty ( ) ;
		
		buf . writeBoolean ( b ) ;
		
		if ( b ) {
			
			try {
				
				Utils . netWriteString ( buf, s ) ;
				
			} catch ( Exception ex ) {
				
				ex . printStackTrace ( ) ;
				
			}

		}
		
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

    	shopId = buf . readInt ( ) ;
    	catId = buf . readInt ( ) ;
    	merchId = buf . readInt ( ) ;
    	type = Type . values ( ) [ buf . readInt ( ) ] ;

    	boolean b = buf . readBoolean ( ) ;
    	
    	if ( b ) {
    		
			try {
				
				s = Utils . netReadString ( buf ) ;
				
			} catch ( Exception ex ) {
				
				ex . printStackTrace ( ) ;
				
			}
			
    	} else {
    		
    		s = "" ;
    		
    	}
    	
    }
    
	public enum Type {
	
		NAME
		
	}
	
}
