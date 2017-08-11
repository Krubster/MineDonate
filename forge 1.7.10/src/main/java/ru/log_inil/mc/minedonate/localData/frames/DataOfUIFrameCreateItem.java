package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameCreateItem {

	public DataOfUIElement createButton ;
	public DataOfUIElement cancelButton ;
	public String title;
	public DataOfUITextHolderElement nameField ;
	
	public DataOfUIFrameCreateItem ( ) {
		
		title = "Add item to shop" ;
		createButton = new DataOfUIElement ( "Add", 35, 20 ) ;
		cancelButton = new DataOfUIElement ( "Cancel", 40, 20 ) ;
		nameField = new DataOfUITextHolderElement ( "", "Item name", 160, 20 ) ;
		
	}
	
}
