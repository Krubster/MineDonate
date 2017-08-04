package ru.alastar.minedonate.network.packets;

import java.io.UnsupportedEncodingException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class CreateNewShopPacket implements IMessage {

	String name ;
    public CreateNewShopPacket(){}
    public CreateNewShopPacket(String _name){
    	
    	name = _name ;
    	
    }

    @Override 
    public void toBytes(ByteBuf buf) {
    	
		writeString(buf, name);

    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
       try {
    	   
    	   name = readString ( buf ) ;
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
    String readString ( ByteBuf buf ) throws UnsupportedEncodingException {
    	
        return new String ( buf . readBytes ( buf . readInt ( ) ) . array ( ), "UTF-8" ) ;
        
    }
     
    
    void writeString ( ByteBuf buf, String str ) {
    	
        buf.writeInt(str.getBytes().length);
        buf.writeBytes(str.getBytes());
        
    }
    
 }