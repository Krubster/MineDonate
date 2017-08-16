package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameAddEntity extends DataOfUIFrameAddItem {

	public DataOfUIFrameAddEntity ( ) {
		
		super ( ) ;
		
		title = "Add entity to shop" ;

		nameField = new DataOfUITextHolderElement ( "", "Entity name", 120, 20 ) ;
		limitField = null ;
		
	}
	
}
