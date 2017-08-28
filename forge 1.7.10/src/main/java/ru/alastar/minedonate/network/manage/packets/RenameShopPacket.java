package ru.alastar.minedonate.network.manage.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.Utils;

public class RenameShopPacket implements IMessage {

	public int shopId ;
	public String name ;
	
    public RenameShopPacket ( ) { }
    public RenameShopPacket ( int _shopId, String _name ) {
    	
    	shopId = _shopId ;
    	name = _name ;
    	
    }

    @Override 
    public void toBytes ( ByteBuf buf ) {
    	
    	try {
			
    		buf . writeInt ( shopId ) ;
    		Utils . netWriteString ( buf, name ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
    	
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {
    	
       try {
    	   
    	   shopId = buf . readInt ( ) ;
    	   name = Utils . netReadString ( buf ) ;
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
 }