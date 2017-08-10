package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameRenameItem extends DataOfUIFrameRename {

	public DataOfUIFrameRenameItem ( ) {
		
		super ( ) ;
		
		title = "Rename item" ;
		renameField = new DataOfUITextHolderElement ( "", "Item name", 160, 20 ) ;

	}
	
}
