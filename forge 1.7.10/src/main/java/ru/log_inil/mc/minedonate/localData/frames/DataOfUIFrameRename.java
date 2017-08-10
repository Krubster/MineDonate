package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameRename {

	public DataOfUIElement saveButton ;
	public DataOfUIElement cancelButton ;
	public String title;
	public DataOfUITextHolderElement renameField ;

	public DataOfUIFrameRename ( ) {
		
		title = "Rename" ;
		saveButton = new DataOfUIElement ( "Rename", 40, 20 ) ;
		cancelButton = new DataOfUIElement ( "Cancel", 40, 20 ) ;
		renameField = new DataOfUITextHolderElement ( "", "", 160, 20 ) ;
		
	}
	
}
