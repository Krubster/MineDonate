package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.rtnl.Account;
import ru.alastar.minedonate.rtnl.Utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alastar on 18.07.2017.
 */
public class AccountInfoPacket implements IMessage {

	public AccountInfoPacket ( ) { }

    public MoneySystem [ ] mSystems ;
    
    public List < String > permissions ;
    
	public boolean freezShopCreate ;
	public String freezShopCreateFreezer ;
	public String freezShopCreateReason ;
	public int shopsCount ;
	
    public String id, userName ;
    
    public AccountInfoPacket ( String _id, String _userName ) {
  
    	id = _id ;
    	userName = _userName ;
    	
	}

	@Override 
    public void toBytes ( ByteBuf buf ) {
        
        try {
        	
    		Utils . netWriteString ( buf, id ) ;
    		Utils . netWriteString ( buf, userName ) ;

            Account acc = MineDonate . getAccount ( MineDonate . getUUIDFromName ( userName ) ) ;

            buf . writeInt ( acc . moneys . size ( ) ) ;

            for ( String k : acc . moneys . keySet ( ) ) {
            	
                Utils . netWriteString ( buf, k ) ;
                buf . writeInt ( acc . getMoney ( k ) ) ;

            }   
				    		
            buf . writeInt ( acc . permissions . size ( ) ) ;
            
            for ( String p : acc . permissions  ) {
            	
            	Utils . netWriteString ( buf, p ) ;
            	
            }
                        
            buf . writeBoolean ( acc . freezShopCreate ) ;
            
            if ( acc . freezShopCreate ) {
            	
        		Utils . netWriteString ( buf, acc . freezShopCreateFreezer ) ;
        		Utils . netWriteString ( buf, acc . freezShopCreateReason ) ;

            }
            
            buf . writeInt ( acc . shopsCount ) ;
            
		} catch ( Exception ex ) {
			
			ex . printStackTrace ( ) ;
			
		}
        
    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
        try {
        	
        	id = Utils . netReadString ( buf ) ;
        	userName = Utils . netReadString ( buf ) ;

        	int l = buf . readInt ( ) ;
        	mSystems = new MoneySystem [ l ] ;
        	
        	for ( int i = 0 ; i < l ; i ++ ) {
        	
        		mSystems [ i ] = new MoneySystem ( Utils . netReadString ( buf ), buf . readInt ( ) ) ;
        		
        	}
        	
        	l = buf . readInt ( ) ;
        	permissions = new ArrayList < > ( ) ;
        	
        	if ( l > 0 ) {
        		
        		for ( int i = 0 ; i < l ; i ++ ) {
        			
        			permissions . add ( Utils . netReadString ( buf ) . toLowerCase ( ) ) ;
        			
        		}
        		
        	}
        	
        	freezShopCreate = buf . readBoolean ( ) ;
        	
        	if ( freezShopCreate ) {
        		
        		freezShopCreateFreezer = Utils . netReadString ( buf ) ;
        		freezShopCreateReason = Utils . netReadString ( buf ) ;
        		
        	}
        	
        	shopsCount = buf . readInt ( ) ;
        	               
		} catch ( UnsupportedEncodingException ex ) {
			
			ex . printStackTrace ( ) ;
			
		}

    }
    
     
    public class MoneySystem {
    	
    	public String type ;
    	public int balance ;
    	
    	public MoneySystem ( String _type, int _balance ) {
    		
    		balance = _balance ;
    		type = _type ;
    		
    	}
    	
    }
    
}