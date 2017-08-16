package ru.alastar.minedonate.network.packets;

import java.io.UnsupportedEncodingException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.rtnl.Utils;

public class MoneyChangedPacket implements IMessage {

    public int money;
    public String moneyType ;
    
    public MoneyChangedPacket(){}

    public MoneyChangedPacket(int money, String _moneyType ) {
    	
        this . money = money ;
        this . moneyType = _moneyType ;
        
    }

    @Override 
    public void toBytes(ByteBuf buf) {
        
    	buf.writeInt(money);
    	
        try {
			
        	Utils . netWriteString(buf, moneyType);
			
		} catch ( UnsupportedEncodingException ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
        
    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
        money = buf . readInt ( ) ;
        
        try {
			
        	moneyType = Utils . netReadString ( buf ) ;
			
		} catch ( UnsupportedEncodingException ex ) {
			
			ex . printStackTrace ( ) ;
			
		}

    }

}
