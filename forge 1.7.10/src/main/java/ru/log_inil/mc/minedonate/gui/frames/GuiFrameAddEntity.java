package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.MineDonate;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.ModNetwork;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameAddItem;

public class GuiFrameAddEntity extends GuiFrameAddItem {

	public GuiFrameAddEntity ( String _name, DataOfUIFrameAddItem _douifcs ) {
	
		super ( _name, _douifcs ) ;

	}


    @Override
	public void postShow ( ShopGUI g ) {
		
    	super . postShow ( g ) ;

    	limitField.setEnabled(false);
    	limitField.setVisible(false);
    	
    }
    

    @Override
	public boolean actionPerformed ( ShopGUI g, GuiButton b ) {
		
    	if ( b . id == saveChangesButton . id ) {

    		if ( costField . getText ( ) . trim ( ) . isEmpty ( ) ) {
    			
    			costField . fieldBorderColor = fieldBorderRedColor ;
    			
    			return false ;
    			
    		} else {
    			
    			try {
    				
    				Integer i = Integer . parseInt ( costField . getText ( ) ) ;
    				
    				if ( i < 1 ) {
    					
    	    			costField . fieldBorderColor = fieldBorderRedColor ;
    	    			
    	    			return false ;
    	    			
    				}
    				
    			} catch ( Exception ex ) {
    				
    				ex . printStackTrace ( ) ;
        			
    				costField . fieldBorderColor = fieldBorderRedColor ;
        			
        			return false ;
        			
    			}
    			
    		}
    		
    		g . setLoading ( true ) ;
    		
    //        ModNetwork . sendToServerAddNewItemPacket ( shopId, catId, Integer.parseInt(this.limitField.getText()), Integer.parseInt(this.costField.getText()), this . nameField . getText ( ) ) ;
            
            this . hideFrame ( g ) ;

    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    		
            this . hideFrame ( g ) ;
    		
    	}
    	
		return false ; 
		
    }	
		
}
