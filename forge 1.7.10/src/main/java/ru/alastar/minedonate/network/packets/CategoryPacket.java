package ru.alastar.minedonate.network.packets;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import ru.alastar.minedonate.Utils;
import ru.alastar.minedonate.gui.ShopCategory;

public class CategoryPacket implements IMessage {

	public int shopId, catId ;
    public ShopCategory . SubCategory [ ] subCategories ;

    public CategoryPacket(){}
    public CategoryPacket(int _shopId, int _catId, ShopCategory . SubCategory [ ] _subCategories ){
    	
    	shopId = _shopId ;
    	catId = _catId ;
    	subCategories = _subCategories ;
    	
    }

    @Override 
    public void toBytes(ByteBuf buf) {
    	
		try {
			
			buf . writeInt ( shopId ) ;
			buf . writeInt ( catId ) ;
			
			buf.writeBoolean( subCategories.length != 0 );
			
			if ( subCategories.length != 0 ) {
				
		    	buf.writeInt(subCategories.length);
		    	
		    	for ( ShopCategory . SubCategory sc: subCategories ) {
		    		
		    		buf.writeInt(sc.subCatId);
		
		    		Utils . netWriteString ( buf, sc . displayName ) ;
		    		
		    	}
		    	
			}
			
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }
    	
    }

    @Override 
    public void fromBytes(ByteBuf buf) {
    	
       try {
    	   
    	   shopId = buf . readInt ( ) ;
    	   catId = buf . readInt ( ) ;
    	   
    	   if ( buf . readBoolean ( )  ) {
    	   
	    	   int l = buf . readInt ( ) ;
	    	   
	    	   subCategories = new ShopCategory . SubCategory [ l ] ;
	    	   
	           for ( int i = 0 ; i < l ; i ++ ) {
	        	   
	        	   subCategories [ i ] = new ShopCategory . SubCategory ( buf . readInt ( ), Utils . netReadString ( buf ) ) ;

	           } 
	           
           } else {

        	   subCategories = new ShopCategory . SubCategory [ 0 ] ;
        	   
           }
           
       } catch ( Exception ex ) {
    	   
    	   ex . printStackTrace ( ) ;
    	   
       }

    }
    
 }