package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameDelete {

	public DataOfUIElement deleteButton ;
	public DataOfUIElement cancelButton ;
	public String title;
	public DataOfUITextHolderElement codeField ;

	public DataOfUIFrameDelete ( ) {
		
		title = "Delete" ;
		deleteButton = new DataOfUIElement ( "Delete", 40, 20 ) ;
		cancelButton = new DataOfUIElement ( "Cancel", 40, 20 ) ;
		codeField = new DataOfUITextHolderElement ( "", "Entry code", 160, 20 ) ;
		
	}
	
}
