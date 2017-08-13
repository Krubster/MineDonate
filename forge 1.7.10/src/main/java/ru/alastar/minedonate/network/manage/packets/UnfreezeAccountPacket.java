package ru.alastar.minedonate.network.manage.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.rtnl.Utils;

public class UnfreezeAccountPacket implements IMessage {

	public String player ;
	
    public UnfreezeAccountPacket ( ) { }
    public UnfreezeAccountPacket ( String _player ) {
    	
    	player = _player ;
    	
    }

    @Override 
    public void toBytes ( ByteBuf buf ) {
    	
    	try {
			
    		Utils . netWriteString ( buf, player ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
    	
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {
    	
       try {
    	   
    	   player = Utils . netReadString ( buf ) ;
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
 }