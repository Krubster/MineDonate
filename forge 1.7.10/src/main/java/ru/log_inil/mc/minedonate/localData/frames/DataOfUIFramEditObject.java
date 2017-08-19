package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFramEditObject {

	public DataOfUIElement editButton ;
	public DataOfUIElement cancelButton ;
	public String title;
	public DataOfUITextHolderElement nameField, limitField, costField ;
	
	public DataOfUIFramEditObject ( ) {
		
		title = "Edit obj" ;
		editButton = new DataOfUIElement ( "Edit", 40, 20 ) ;
		cancelButton = new DataOfUIElement ( "Cancel", 50, 20 ) ;
		nameField = new DataOfUITextHolderElement ( "", "Obj name", 120, 20 ) ;
		limitField = new DataOfUITextHolderElement ( "", "Limit", 40, 20 ) ;
		costField = new DataOfUITextHolderElement ( "", "Cost", 40, 20 ) ;
		
	}

	public DataOfUIFramEditObject ( String _title, String _nameFieldHolder ) { 
	
		this ( ) ;

		title = _title ;
		nameField . text = _nameFieldHolder ;
		
	}
	
}
