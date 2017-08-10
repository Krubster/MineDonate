package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameRenameEntity extends DataOfUIFrameRename {

	public DataOfUIFrameRenameEntity ( ) {
		
		super ( ) ;
		
		title = "Rename entity" ;
		renameField = new DataOfUITextHolderElement ( "", "Entity name", 160, 20 ) ;

	}
	
}
