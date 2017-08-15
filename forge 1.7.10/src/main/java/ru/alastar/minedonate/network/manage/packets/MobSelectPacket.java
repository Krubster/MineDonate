package ru.alastar.minedonate.network.manage.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MobSelectPacket implements IMessage {

	int s ;
	
    public MobSelectPacket ( ) { } 

    public MobSelectPacket ( int _s ) {

    	s = _s ;
    	
    }

    @Override
    public void toBytes ( ByteBuf buf ) {

    	buf . writeInt ( s ) ;
    	
    }

    @Override
    public void fromBytes ( ByteBuf buf ) {

    	s = buf . readInt ( ) ;
    	
    }
    
}