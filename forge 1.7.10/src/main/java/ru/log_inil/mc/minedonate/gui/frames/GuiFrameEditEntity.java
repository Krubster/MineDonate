package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.network.manage.packets.EditMerchNumberPacket;
import ru.alastar.minedonate.network.manage.packets.EditMerchStringPacket;
import ru.alastar.minedonate.rtnl.ModNetwork;

import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFramEditItem;

public class GuiFrameEditEntity extends GuiFrameEditItem {

    public GuiFrameEditEntity ( String _name, DataOfUIFramEditItem _douifcs ) {
		
    	super ( _name, _douifcs ) ;
		
	}

	@Override
	public void postShow ( ShopGUI g ) {
	
		if ( ! isVisible ( ) ) {
			
			return ;
			
		}
		
    	super . postShow ( g ) ;

    }
	
    @Override
	public boolean actionPerformed ( ShopGUI g, GuiButton b ) {

    	if ( b . id == saveChangesButton . id ) {
    	
    		g . setLoading ( true ) ;
    		
    		if ( MineDonate . getAccount ( ) . canUnlimitedItems ( ) ) {

    			try {
    				
    				Integer n = Integer . parseInt ( limitField . getText ( ) ) ;
	    			
    				if ( limit != n ) {
    					
    					hideFrame ( g ) ;
    			    		
    					ModNetwork . sendToServerEditMerchNumberPacket ( shopId, catId, merch_id, EditMerchNumberPacket . Type . LIMIT, ( int ) n ) ;
    					
    					limit = n ;
    					    						
    				}
    				
	    		} catch ( Exception ex ) {
	    				    			
	    		}
    			
    		}
    		
			try {
				
				Integer n = Integer . parseInt ( costField . getText ( ) ) ;
    			
				if ( cost != n ) {
					
					hideFrame ( g ) ;
			        
					ModNetwork . sendToServerEditMerchNumberPacket ( shopId, catId, merch_id, EditMerchNumberPacket . Type . COST, ( int ) n ) ;
					
					cost = n ;
										
				}
				
    		} catch ( Exception ex ) {
    			    			
    		}

    		if ( ! nameField . getText ( ) . trim ( ) . equals ( fieldText ) ) {
    			
				hideFrame ( g ) ;
		        
    			ModNetwork . sendToServerEditMerchStringPacket ( shopId, catId, merch_id, EditMerchStringPacket . Type . NAME, ( fieldText = nameField . getText ( ) ) ) ;
    			System.err.println(fieldText);
    		}
    		
    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    	
            this . hideFrame ( g ) ;
    		
    	}
    	
		return false ;
		
	}

	public void setInfo ( int _shopId, int _catId, int _merch_id, int _cost ) {
		
		shopId = _shopId ;
		catId = _catId ;
		merch_id = _merch_id ;
		cost = _cost ;
		
	}
	
}
