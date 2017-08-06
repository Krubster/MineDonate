package ru.alastar.minedonate.network.packets;

import java.io.UnsupportedEncodingException;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.mproc.AbstractMoneyProcessor;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AccountInfoPacket implements IMessage {
    // A default constructor is always required
    public AccountInfoPacket(){}

    public MoneySystem [ ] mSystems ;
    
    String userName ;
    
    public AccountInfoPacket ( String _userName ) {
  
    	userName = _userName ;
    	
	}

	@Override 
    public void toBytes ( ByteBuf buf ) {
        
        try {
        	
            buf . writeInt ( MineDonate . moneyProcessors . size ( ) ) ;

            for ( AbstractMoneyProcessor amp : MineDonate . moneyProcessors . values ( ) ) {
            	
        		System.err.println(amp.getMoneyType() + ", " + amp . getMoneyFor ( userName ) );

            	buf . writeInt ( amp . getMoneyFor ( userName ) ) ;
            	Utils . netWriteString ( buf, amp . getMoneyType ( ) ) ;
              
            	buf . writeBoolean ( amp . isCustomMoneyType ( ) ) ;
            	
            	if ( amp . isCustomMoneyType ( ) ) {
            		
            		Utils . netWriteString ( buf, amp . getClientMoneyType ( ) ) ;
                	
            	}
            	
            }
				
            String [ ] perms = MineDonate . getPermissionsByUser ( userName ) ; 
            
            buf . writeInt ( perms . length ) ;
            
            for ( String p : perms ) {
            	
            	Utils . netWriteString ( buf, p ) ;
            	
            }
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
        
    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
        
        try {
			
        	int l = buf . readInt ( ) ;
        	mSystems = new MoneySystem [ l ] ;
        	
        	for ( int i = 0 ; i < l ; i ++ ) {
        	
        		mSystems [ i ] = new MoneySystem ( buf . readInt ( ), Utils . netReadString ( buf ), buf . readBoolean ( ), buf ) ;
        		
        	}
        	
        	
		} catch ( UnsupportedEncodingException ex ) {
			
			ex . printStackTrace ( ) ;
			
		}

    }
    
     
    public class MoneySystem {
    	
    	public int balance ;
    	public String type ;
    	public boolean isCustom ;
    	public String clData ;
    	
    	public MoneySystem ( int _balance, String _type, boolean _isCustom, ByteBuf _buf ) {
    		
    		balance = _balance ;
    		type = _type ;
    		isCustom = _isCustom ;
    		
    		try {
    			
    			clData = isCustom ? Utils . netReadString ( _buf ) : null ;
    			
    		} catch ( Exception ex ) {
    			
    			ex . printStackTrace ( ) ;
    			
    		}
    		
    	}
    	
    }
    
}