package ru.log_inil.mc.minedonate.gui;

import ru.alastar.minedonate.gui.ShopGUI;

public class GuiFrame extends GuiEntry {

	public GuiFrame ( String _name ) {
		
		super ( _name ) ;
		
	}

	public void hideFrame ( ShopGUI g ) {
		
	    g . showEntry ( name, false ) ; 
        unShow ( g ) ;
        
	}
	
}
