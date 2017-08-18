package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameCreateShop {

	public DataOfUIElement createButton ;
	public DataOfUIElement cancelButton ;
	public String title;
	public DataOfUITextHolderElement nameField ;
	
	public DataOfUIFrameCreateShop ( ) {
		
		title = "Create new shop" ;
		createButton = new DataOfUIElement ( "Create", 50, 20 ) ;
		cancelButton = new DataOfUIElement ( "Cancel", 50, 20 ) ;
		nameField = new DataOfUITextHolderElement ( "", "Shop name", 160, 20 ) ;
		
	}
	
}
