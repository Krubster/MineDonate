package ru.alastar.minedonate.network.manage.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.Utils;

public class EditMerchFieldPacket implements IMessage {

	public int shopId, catId, merchId ;
	public FieldType type ;
	public FieldName name ;
	public Object data ;
	
    public EditMerchFieldPacket ( ) { }
    
    public EditMerchFieldPacket ( int _shopId, int _catId, int _merchId, FieldType _type, FieldName _name, Object _data ) {
    	
    	shopId = _shopId ;
    	catId = _catId ;
    	merchId = _merchId ;
    	type = _type ;
    	name = _name ;
    	data = _data ;
    	
    }
    
    @Override 
    public void toBytes ( ByteBuf buf ) {
		
		buf . writeInt ( shopId ) ;
		buf . writeInt ( catId ) ;
		buf . writeInt ( merchId ) ;
		buf . writeInt ( type . ordinal ( ) ) ;
		buf . writeInt ( name . ordinal ( ) ) ;


		switch ( type ) {
		
			case STRING:
				
				boolean b = data == null || ( (String) data ) . trim ( ) . isEmpty ( ) ;
				
				buf . writeBoolean ( b ) ;
				
				if ( ! b ) {
					
					try {
						
						Utils . netWriteString ( buf, (String) data ) ;
						
					} catch ( Exception ex ) {
						
						ex . printStackTrace ( ) ;
						
					}
	
				}
				
			break;
			
			case INTEGER:
			
				buf . writeInt ( (Integer) data ) ;

			break ;
			
	    	default: break;

		}
		
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

    	shopId = buf . readInt ( ) ;
    	catId = buf . readInt ( ) ;
    	merchId = buf . readInt ( ) ;
    	type = FieldType . values ( ) [ buf . readInt ( ) ] ;
    	name = FieldName . values ( ) [ buf . readInt ( ) ] ;

		switch ( type ) {
		
			case STRING:
				
		    	boolean b = buf . readBoolean ( ) ;
		    	
		    	if ( ! b ) {
		    		
					try {
						
						data = Utils . netReadString ( buf ) ;
						
					} catch ( Exception ex ) {
						
						ex . printStackTrace ( ) ;
						
					}
					
		    	} else {
		    		
		    		data = "" ;
		    		
		    	}
	    	
	    	break;
	    	
			case INTEGER:
				
				data = buf . readInt ( ) ;

			break ;
			
	    	default: break;
    		
		}
		
    }
    
    public enum FieldType {
    	
    	STRING, 
    	INTEGER 
    	
    }
    
	public enum FieldName {
	
		NAME, 
		LIMIT, COST
		
	}
	
}
