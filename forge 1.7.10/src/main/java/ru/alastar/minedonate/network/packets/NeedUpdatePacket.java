package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class NeedUpdatePacket implements IMessage {

	public int r ;
	
	public NeedUpdatePacket ( ) {
		
	}
	
	// r - 0 - client need full info, 1 - client has received needed full info, 2 - client has received needed cat info
	public NeedUpdatePacket(int _r){
		
		r = _r ;
		
	}

    @Override 
    public void toBytes(ByteBuf buf) {
    	
    	buf.writeInt(r);
    	
    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    
    	try {
    		
    		r = buf.readInt();
	    	
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
		
    }
    
}