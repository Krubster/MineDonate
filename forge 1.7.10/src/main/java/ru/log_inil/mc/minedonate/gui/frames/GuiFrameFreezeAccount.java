package ru.log_inil.mc.minedonate.gui.frames;

import net.minecraft.client.gui.GuiButton;

import ru.alastar.minedonate.gui.ShopGUI;
import ru.alastar.minedonate.rtnl.ModNetwork;

import ru.log_inil.mc.minedonate.localData.frames.DataOfUIFrameField;

public class GuiFrameFreezeAccount extends GuiFrameTextField {

	String accountName ;
	
	public GuiFrameFreezeAccount ( String _name, DataOfUIFrameField _douiffs ) {
		
		super ( _name, _douiffs ) ;

		
	}

    @Override
	public boolean actionPerformed ( ShopGUI g, GuiButton b ) {
		
    	if ( b . id == saveChangesButton . id ) {
    	
    		if ( nameField . getText ( ) . trim ( ) . isEmpty ( ) ) {
    			
    			nameField . fieldBorderColor = fieldBorderRedColor ;
    			
    			return false ;
    			
    		}
    		
    		g . setLoading ( true ) ;
    		
    		ModNetwork . sendToServerFreezeAccountPacket ( accountName, nameField . getText ( ) ) ;
    		
			hideFrame ( g ) ;
			
    	}
    	
    	if ( b . id == cancelChangesButton . id ) {
    	
			hideFrame ( g ) ;
    		
    	}
    	
		return false ;
		
	}

	public void setAccountName ( String _accountName ) {

		accountName = _accountName ;
		
	}   
    
}
