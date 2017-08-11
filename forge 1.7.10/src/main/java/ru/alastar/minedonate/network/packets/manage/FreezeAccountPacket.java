package ru.alastar.minedonate.network.packets.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.rtnl.Utils;

public class FreezeAccountPacket implements IMessage {

	public String player ;
	public String reason ;
	
    public FreezeAccountPacket ( ) { }
    public FreezeAccountPacket ( String _player, String _reason ) {
    	
    	player = _player ;
    	reason = _reason ;
    	
    }

    @Override 
    public void toBytes ( ByteBuf buf ) {
    	    	
    	try {
    	
    		Utils . netWriteString ( buf, player ) ;
    		Utils . netWriteString ( buf, reason ) ;
    		
    	} catch ( Exception ex ) {
    		
    		ex . printStackTrace ( ) ;
    		
    	}
    	
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {

	   	try {
	    	
	   		player = Utils . netReadString ( buf ) ;
	   		reason = Utils . netReadString ( buf ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
   	
    }
    
 }