package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameEditEntity extends DataOfUIFramEditItem {

	public DataOfUIFrameEditEntity ( ) {
		
		super ( ) ;
		
		title = "Edit entity" ;
		nameField = new DataOfUITextHolderElement ( "", "Entity name", 120, 20 ) ;
		
	}
	
}
