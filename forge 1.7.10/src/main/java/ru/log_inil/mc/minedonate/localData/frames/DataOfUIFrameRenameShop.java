package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameRenameShop extends DataOfUIFrameRename {

	public DataOfUIFrameRenameShop ( ) {
		
		super ( ) ;
		
		title = "Rename shop" ;
		renameField = new DataOfUITextHolderElement ( "", "Shop name", 160, 20 ) ;

	}
	
}
