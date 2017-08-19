package ru.log_inil.mc.minedonate.localData.frames;

import ru.log_inil.mc.minedonate.localData.ui.DataOfUIElement;
import ru.log_inil.mc.minedonate.localData.ui.DataOfUITextHolderElement;

public class DataOfUIFrameConfirm {

	public DataOfUIElement deleteButton ;
	public DataOfUIElement cancelButton ;
	public String title;
	public DataOfUITextHolderElement codeField ;
	public String text;

	public DataOfUIFrameConfirm ( ) {
		
		title = "Action" ;
		text = "Entry confirm code[%code%] to field" ;
		
		deleteButton = new DataOfUIElement ( "Ok", 50, 20 ) ;
		cancelButton = new DataOfUIElement ( "Cancel", 50, 20 ) ;
		codeField = new DataOfUITextHolderElement ( "", "Entry code", 160, 20 ) ;
		
	}
	
	public DataOfUIFrameConfirm ( String _title, String _text, String _confirmButtonText ) {
	
		this ( ) ;
		
		title = _title ;
		text = _text ;
		deleteButton . text = _confirmButtonText ;
		
	}
	
}
