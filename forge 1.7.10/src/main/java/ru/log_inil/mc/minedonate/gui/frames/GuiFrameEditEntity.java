package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.manage.packets.EditMerchFieldPacket;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFramEditObject;

public class GuiFrameEditEntity extends GuiFrameEditItem {

    public GuiFrameEditEntity ( String _name, DataOfUIFramEditObject _douifcs ) {
		
    	super ( _name, _douifcs ) ;
		
	}

    @Override
	public boolean actionPerformed ( ShopGUI g, GuiButton b ) {

    	if ( b . id == saveChangesButton . id ) {
    	
    		g . setLoading ( true ) ;
    		
    		if ( MineDonate . getAccount ( ) . canUnlimitedEntities ( ) ) {

    			try {
    				
    				Integer n = Integer . parseInt ( limitField . getText ( ) ) ;
	    			
    				if ( limit != n ) {
    					
    					hideFrame ( g ) ;
    			    		
    					ModNetworkRegistry . sendToServerEditMerchFieldPacket ( shopId, catId, merch_id, EditMerchFieldPacket . FieldType . INTEGER, EditMerchFieldPacket . FieldName . LIMIT, n ) ;
    					
    					limit = n ;
    					    						
    				}
    				
	    		} catch ( Exception ex ) {
	    				    			
	    		}
    			
    		}
    		
			try {
				
				Integer n = Integer . parseInt ( costField . getText ( ) ) ;
    			
				if ( cost != n && cost > 0 ) {
					
					hideFrame ( g ) ;
			        
					ModNetworkRegistry . sendToServerEditMerchFieldPacket ( shopId, catId, merch_id, EditMerchFieldPacket . FieldType . INTEGER, EditMerchFieldPacket . FieldName . COST, n ) ;
					
					cost = n ;
										
				}
				
    		} catch ( Exception ex ) {
    			    			
    		}

    		if ( ! nameField . getText ( ) . trim ( ) . equals ( fieldText ) ) {
    			
				hideFrame ( g ) ;
				
				fieldText = nameField . getText ( ) ;
				
    			ModNetworkRegistry . sendToServerEditMerchFieldPacket ( shopId, catId, merch_id, EditMerchFieldPacket . FieldType . STRING, EditMerchFieldPacket . FieldName . NAME, fieldText ) ;

    		}
    		
    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    	
            this . hideFrame ( g ) ;
    		
    	}
    	
		return false ;
		
	}
	
}
