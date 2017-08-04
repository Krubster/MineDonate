package ru.alastar.minedonate.network.packets;

import java.io.UnsupportedEncodingException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AccountInfoPacket implements IMessage {
    // A default constructor is always required
    public AccountInfoPacket(){}

    public int money;
    public String moneyType ;
    public AccountInfoPacket(int money, String _moneyType ) {
    	
        this . money = money ;
        this . moneyType = _moneyType ;
        
    }

    @Override 
    public void toBytes(ByteBuf buf) {
        buf.writeInt(money);
        writeString(buf, moneyType);
    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
        money = buf . readInt ( ) ;
        
        try {
			
        	moneyType = readString ( buf ) ;
			
		} catch ( UnsupportedEncodingException ex ) {
			
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