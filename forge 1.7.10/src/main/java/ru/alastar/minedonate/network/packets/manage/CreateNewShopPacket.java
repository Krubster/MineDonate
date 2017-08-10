package ru.alastar.minedonate.network.packets.manage;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.rtnl.Utils;

public class CreateNewShopPacket implements IMessage {

	String name ;
	
    public CreateNewShopPacket ( ) { }
    public CreateNewShopPacket ( String _name ) {
    	
    	name = _name ;
    	
    }

    @Override 
    public void toBytes ( ByteBuf buf ) {
    	
    	try {
			
    		Utils . netWriteString ( buf, name ) ;
			
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
    	
    }

    @Override 
    public void fromBytes ( ByteBuf buf ) {
    	
       try {
    	   
    	   name = Utils . netReadString ( buf ) ;
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
 }