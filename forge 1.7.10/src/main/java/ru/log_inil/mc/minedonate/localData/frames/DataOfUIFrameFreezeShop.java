package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameFreezeShop {
	
	public DataOfUIElement freezeButton ;
	public DataOfUIElement cancelButton ;
	public String title;
	public DataOfUITextHolderElement reasonField ;
	
	public DataOfUIFrameFreezeShop ( ) {
		
		title = "Freeze shop" ;
		freezeButton = new DataOfUIElement ( "Freeze", 50, 20 ) ;
		cancelButton = new DataOfUIElement ( "Cancel", 50, 20 ) ;
		reasonField = new DataOfUITextHolderElement ( "", "Reason", 160, 20 ) ;
		
	}
	
}
