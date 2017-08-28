package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;
import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.ModNetworkRegistry;
import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameField;

public class GuiFrameRenameShop extends GuiFrameTextField {

	int shopId = -1 ;
	
	public GuiFrameRenameShop ( String _name, DataOfUIFrameField _douifrs ) {
		
		super ( _name, _douifrs ) ;

		
	}
    
    @Override
	public boolean actionPerformed ( ShopGUI g, GuiButton b ) {
		
    	if ( b . id == saveChangesButton . id ) {
    	
    		if ( nameField . getText ( ) . trim ( ) . isEmpty ( ) ) {
    			
    			nameField . fieldBorderColor = fieldBorderRedColor ;
    			
    			return false ;
    			
    		}
    		
    		g . setLoading ( true ) ;
    		
    		ModNetworkRegistry . sendToServerRenameShopPacket ( shopId, this . nameField . getText ( ) ) ;
    		
			hideFrame ( g ) ;

    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    	
			hideFrame ( g ) ;
    		
    	}
    	
		return false ;
		
	}

	public void setShopId ( int _shopId ) {

		shopId = _shopId ;
		
	}
    
}
